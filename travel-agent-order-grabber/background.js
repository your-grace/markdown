console.log('[抢单助手] background.js 已加载');
let isMonitoring = false;
let ws;
let grabListTabId = null;
let logs = [];
let lastCookieValue = null;

// 从本地存储获取状态
chrome.storage.local.get(['isMonitoring', 'logs'], function(result) {
    if (result.isMonitoring) {
        isMonitoring = true;
        startMonitoring();
    }
    if (result.logs) {
        logs = result.logs;
    }
});

// 监听来自popup的消息
chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
    if (request.action === 'startMonitoring') {
        if (isMonitoring) {
            sendResponse({ success: false, error: '已经在监控中' });
            return;
        }
        startMonitoring();
        sendResponse({ success: true });
    } else if (request.action === 'stopMonitoring') {
        if (!isMonitoring) {
            sendResponse({ success: false, error: '没有在监控中' });
            return;
        }
        stopMonitoring();
        sendResponse({ success: true });
    } else if (request.action === 'newOrder') {
        addLog(`[收到新订单] 单号：${request.orderId}，内容：${request.message}`);
        if (grabListTabId) {
            chrome.tabs.sendMessage(grabListTabId, { action: 'checkAndGrabOrders' }, function(response) {
                if (response && response.success) {
                    addLog(`成功执行抢单操作，已抢${response.count}个订单`);
                    if (response.orderIds && response.orderIds.length > 0) {
                        response.orderIds.forEach(id => addLog(`抢单成功，订单号：${id}`));
                    }
                } else {
                    addLog('抢单操作失败');
                }
            });
        } else {
            addLog('没有找到抢单页面，无法执行抢单操作');
            // 新增：尝试重新查找抢单页面并多次重试
            findGrabListTab();
            let retryCount = 0;
            const maxRetries = 3;
            function retryGrab() {
                if (grabListTabId) {
                    chrome.tabs.sendMessage(grabListTabId, { action: 'checkAndGrabOrders' }, function(response) {
                        if (response && response.success) {
                            addLog(`重试成功执行抢单操作，已抢${response.count}个订单`);
                            if (response.orderIds && response.orderIds.length > 0) {
                                response.orderIds.forEach(id => addLog(`抢单成功，订单号：${id}`));
                            }
                        } else {
                            retryCount++;
                            if (retryCount < maxRetries) {
                                setTimeout(retryGrab, 1000); // 再等1秒重试
                            } else {
                                addLog('重试抢单操作失败');
                            }
                        }
                    });
                } else {
                    retryCount++;
                    if (retryCount < maxRetries) {
                        setTimeout(retryGrab, 1000);
                    } else {
                        addLog('重试抢单操作失败');
                    }
                }
            }
            setTimeout(retryGrab, 1500); // 第一次重试延迟1.5秒
        }
    }
});

// 启动监控
function startMonitoring() {
    isMonitoring = true;
    chrome.storage.local.set({ isMonitoring: true });
    addLog(`监控已启动`);
    findGrabListTab();
    // initWebSocket();
}

// 停止监控
function stopMonitoring() {
    isMonitoring = false;
    chrome.storage.local.set({ isMonitoring: false });
    addLog('监控已停止');
    if (ws) {
        ws.close();
        ws = null;
    }
}

// 查找抢单页面
function findGrabListTab() {
    chrome.tabs.query({ url: 'https://ebk.17u.cn/tailoreb/grabList*' }, function(tabs) {
        if (tabs.length > 0) {
            grabListTabId = tabs[0].id;
            addLog(`已找到抢单页面，ID: ${grabListTabId}`);
        } else {
            grabListTabId = null;
            addLog('未找到抢单页面，请确保已打开抢单页面');
        }
    });
}

// 初始化WebSocket
/*
function initWebSocket() {
    chrome.cookies.get({ url: 'https://ebk.17u.cn', name: 'EBTCUserInfo' }, function(cookie) {
        if (!cookie) {
            addLog('未找到EBTCUserInfo cookie');
            return;
        }
        const userInfo = encodeURIComponent(cookie.value);
        const wsUrl = `wss://ebkapi.17u.cn/travelbook/hub/?userInfo=${userInfo}&access_token=EBTCUserInfo`;
        ws = new WebSocket(wsUrl);

        ws.onopen = function() {
            addLog('WebSocket连接已建立');
            // 发送SignalR握手消息，注意结尾的 \u001e
            ws.send('{"protocol":"json","version":1}\u001e');
        };

        function extractOrderIdFromMessage(msg) {
            // 匹配“需求单号xxxxxxx”
            const match = msg.match(/需求单号(\d+)/);
            return match ? match[1] : '';
        }

        ws.onmessage = function(event) {
            try {
                // SignalR 每条消息结尾带 \u001e，需先去掉
                let raw = event.data;
                console.log('[抢单助手] WebSocket收到原始消息:', event.data);
                if (raw.endsWith('\u001e')) {
                    raw = raw.slice(0, -1);
                }
                // SignalR 可能一次推送多条消息，用 \u001e 分割
                const messages = raw.split('\u001e').filter(Boolean);
                console.log('messages:', messages);
                for (const msg of messages) {
                    // 统一格式输出每条消息
                    console.log('[抢单助手] WebSocket收到消息:', msg);

                    // 下面是原有业务逻辑
                    let data;
                    try {
                        console.log("1");
                        data = JSON.parse(msg);
                    } catch (e) {
                        console.log("1");
                        addLog('消息内容解析失败: ' + msg);
                        continue;
                    }
                    console.log("data.type", data.type);
                    console.log("data.target", data.target);
                    console.log("data.arguments", data.arguments);
                    console.log(
    "data.arguments.length",
    Array.isArray(data.arguments) ? data.arguments.length : '无'
);
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
            addLog('消息内容解析失败: ' + msgObj);
            continue;
        }
    }
    // 这里加日志
    console.log('[抢单助手] 解析后的msgObj:', msgObj);

    if (msgObj.Message && (
        msgObj.Message.includes('新的待抢订单') || 
        msgObj.Message.includes('您有新的待抢订单')
    )) {
        // 提取订单号
        const orderId = extractOrderIdFromMessage(msgObj.Message);
        addLog(`检测到新的待抢订单，单号：${orderId}`);
        if (grabListTabId) {
            chrome.tabs.sendMessage(grabListTabId, { action: 'checkAndGrabOrders' }, function(response) {
                if (response && response.success) {
                    addLog(`成功执行抢单操作，已抢${response.count}个订单`);
                    if (response.orderIds && response.orderIds.length > 0) {
                        response.orderIds.forEach(id => addLog(`抢单成功，订单号：${id}`));
                    }
                } else {
                    addLog('抢单操作失败');
                }
            });
        } else {
            addLog('没有找到抢单页面，无法执行抢单操作');
        }
    } else {
        addLog('收到AddMsg但Message字段不包含新订单提醒: ' + (msgObj.Message || '无'));
    }
} else if (data.type === 1 && data.target === 'Heartbeat') {
                        // 心跳包，无需处理
                    }
                }
            } catch (error) {
                addLog('WebSocket消息解析错误: ' + error.message);
            }
        };

        ws.onclose = function(event) {
            addLog(`WebSocket连接已关闭，代码: ${event.code}`);
            setTimeout(initWebSocket, 5000);
        };

        ws.onerror = function(error) {
            addLog('WebSocket发生错误');
        };
    });
}
*/

// 添加日志并保存
function addLog(message) {
    console.log('[抢单助手]', message);
    logs.push(message);
    if (logs.length > 100) {
        logs = logs.slice(-100);
    }
    chrome.storage.local.set({ logs: logs });
    chrome.runtime.sendMessage({ action: 'updateStatus', message: message }, function(response) {
        // 忽略未连接接收端的错误
        if (chrome.runtime.lastError) {
            // 可选：console.log('无接收端，忽略错误');
        }
    });
}

// 监听标签更新事件
chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
    if(changeInfo.status === 'complete') {
        if (tab.url && tab.url.includes('https://ebk.17u.cn/tailoreb/grabList')) {
            grabListTabId = tabId;
            addLog(`检测到抢单页面更新，ID: ${tabId}`);
        }
    }
});

// 监听标签关闭事件
chrome.tabs.onRemoved.addListener(function(tabId, removeInfo) {
    if (tabId === grabListTabId) {
        grabListTabId = null;
        addLog('抢单页面已关闭');
    }
});

// 定时检测 EBTCUserInfo cookie 是否变化
/*
setInterval(() => {
    chrome.cookies.get({ url: 'https://ebk.17u.cn', name: 'EBTCUserInfo' }, function(cookie) {
        if (!cookie) {
            addLog('未检测到EBTCUserInfo cookie，可能已退出登录');
            return;
        }
        if (lastCookieValue === null) {
            lastCookieValue = cookie.value;
        } else if (lastCookieValue !== cookie.value) {
            addLog('检测到EBTCUserInfo cookie发生变化，自动重连WebSocket');
            lastCookieValue = cookie.value;
            if (ws) {
                ws.close();
                ws = null;
            }
            // initWebSocket();
        }
    });
}, 5000); // 每5秒检测一次
*/
