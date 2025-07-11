console.log('[抢单助手-content] content.js 已注入');

// 监听来自background的消息
chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
    if (request.action === 'checkAndGrabOrders') {
        checkAndGrabOrders(sendResponse);
        return true;
    }
    if (request.action === 'checkOrders') {
        checkOrders(sendResponse);
        return true;
    } else if (request.action === 'grabOrders') {
        grabOrders(sendResponse);
        return true;
    }
});

// 查找包含“查询”文本的按钮
function findQueryButton() {
    const buttons = document.querySelectorAll('button');
    for (const btn of buttons) {
        if (btn.innerText.trim() === '查询') {
            return btn;
        }
    }
    return null;
}
// 查找包含“抢单”文本的按钮
function findQiangButton() {
    const buttons = document.querySelectorAll('button');
    for (const btn of buttons) {
        if (btn.innerText.trim() === '抢单') {
            return btn;
        }
    }
    return null;
}

// 查找所有“抢单”按钮
function findQiangButtons() {
    return Array.from(document.querySelectorAll('button')).filter(
        btn => btn.innerText.trim() === '抢单'
    );
}

// 等待“抢单”按钮出现
function waitForQiangButtons(maxWait = 5000, interval = 200) {
    return new Promise((resolve, reject) => {
        const start = Date.now();
        function check() {
            const grabButtons = findQiangButtons();
            if (grabButtons && grabButtons.length > 0) {
                resolve(grabButtons);
            } else if (Date.now() - start > maxWait) {
                resolve([]); // 超时返回空数组
            } else {
                setTimeout(check, interval);
            }
        }
        check();
    });
}

// 查询并抢单
function checkAndGrabOrders(callback) {
    const queryBtn = findQueryButton();
    if (queryBtn) {
        queryBtn.click();
        console.log('已点击查询按钮');
        waitForQiangButtons(5000, 200).then(grabButtons => {
            if (grabButtons.length > 0) {
                console.log(`找到${grabButtons.length}个待抢订单，开始抢单`);
                grabOrders(callback);
            } else {
                console.log('未找到待抢订单');
                callback({ success: false, error: '未找到待抢订单' });
            }
        });
    } else {
        console.error('未找到查询按钮');
        callback({ success: false, error: '未找到查询按钮' });
    }
}

// 检查订单
function checkOrders(callback) {
    console.log('检查待抢订单...');
    try {
        const queryBtn = findQueryButton();
        if (queryBtn) {
            queryBtn.click();
            console.log('已点击查询按钮');
            setTimeout(() => {
                const grabButtons = findQiangButtons();
                if (grabButtons && grabButtons.length > 0) {
                    console.log(`找到${grabButtons.length}个待抢订单`);
                    callback({ hasOrders: true, count: grabButtons.length });
                } else {
                    console.log('未找到待抢订单');
                    callback({ hasOrders: false });
                }
            }, 1000);
        } else {
            console.error('未找到查询按钮');
            callback({ hasOrders: false, error: '未找到查询按钮' });
        }
    } catch (error) {
        console.error('检查订单时出错:', error);
        callback({ hasOrders: false, error: error.message });
    }
}

// 执行抢单操作
function grabOrders(callback, clickInterval = 300) {
    console.log('开始执行抢单操作...');
    try {
        const grabButtons = findQiangButtons();
        if (!grabButtons || grabButtons.length === 0) {
            console.error('未找到抢单按钮');
            callback({ success: false, error: '未找到抢单按钮' });
            return;
        }
        let count = 0;
        let orderIds = [];
        grabButtons.forEach((button, index) => {
            setTimeout(() => {
                button.click();
                // 获取订单号（假设在 data-orderid 属性）
                let orderId = button.getAttribute('data-orderid');
                // 如无属性，可尝试从按钮附近DOM获取
                if (!orderId) {
                    const row = button.closest('tr');
                    if (row) {
                        const td = row.querySelector('td');
                        if (td) orderId = td.innerText.trim();
                    }
                }
                orderIds.push(orderId || '未知订单号');
                console.log(`已点击第${index + 1}个抢单按钮，订单号：${orderId || '未知'}`);
                count++;
                if (count === grabButtons.length) {
                    console.log(`抢单操作完成，共点击${count}个抢单按钮`);
                    callback({ success: true, count: count, orderIds: orderIds });
                }
            }, index * clickInterval);
        });
    } catch (error) {
        console.error('抢单时出错:', error);
        callback({ success: false, error: error.message });
    }
}

// content.js 顶部立即执行
(function() {
    const script = document.createElement('script');
    script.src = chrome.runtime.getURL('inject-ws-hook.js');
    document.documentElement.appendChild(script);
    script.onload = function() { script.remove(); };
})();

// 监听页面原生 WebSocket 的消息
window.addEventListener('message', function(event) {
    if (event.data && event.data.type === '抢单助手WS') {
        console.log('[抢单助手-content] 收到页面WebSocket消息:', event.data.data);
        const raw = event.data.data;
        const messages = raw.split('\u001e').filter(Boolean);
        for (const msg of messages) {
            let data;
            try {
                data = JSON.parse(msg);
            } catch (e) {
                continue;
            }
            if (
                data.type === 1 &&
                data.target === 'AddMsg' &&
                Array.isArray(data.arguments) &&
                data.arguments.length > 0
            ) {
                let msgObj = data.arguments[0];
                if (typeof msgObj === 'string') {
                    try {
                        msgObj = JSON.parse(msgObj);
                    } catch (e) {
                        continue;
                    }
                }
                if (msgObj.Message && (
                    msgObj.Message.includes('新的待抢订单') ||
                    msgObj.Message.includes('您有新的待抢订单')
                )) {
                    chrome.runtime.sendMessage({
                        action: 'newOrder',
                        orderId: extractOrderIdFromMessage(msgObj.Message),
                        message: msgObj.Message
                    });
                }
            }
        }
    }
});

function extractOrderIdFromMessage(msg) {
    const match = msg.match(/需求单号(\d+)/);
    return match ? match[1] : '';
}
