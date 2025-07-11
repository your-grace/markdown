document.addEventListener('DOMContentLoaded', function () {
    const startBtn = document.getElementById('startBtn');
    const stopBtn = document.getElementById('stopBtn');
    const statusLog = document.getElementById('statusLog');

    // 按钮状态初始化
    chrome.storage.local.get(['isMonitoring', 'logs'], function(result) {
        if (result.isMonitoring) {
            startBtn.disabled = true;
            stopBtn.disabled = false;
        } else {
            startBtn.disabled = false;
            stopBtn.disabled = true;
        }
        if (result.logs) {
            renderLogs(result.logs);
        }
    });

    startBtn.onclick = function () {
        chrome.runtime.sendMessage({ action: 'startMonitoring' }, function (res) {
            if (res && res.success) {
                startBtn.disabled = true;
                stopBtn.disabled = false;
            }
        });
    };

    stopBtn.onclick = function () {
        chrome.runtime.sendMessage({ action: 'stopMonitoring' }, function (res) {
            if (res && res.success) {
                startBtn.disabled = false;
                stopBtn.disabled = true;
            }
        });
    };

    // 实时日志监听
    chrome.runtime.onMessage.addListener(function (msg) {
        if (msg.action === 'updateStatus' && msg.message) {
            addLog(msg.message);
        }
    });

    function addLog(message) {
        chrome.storage.local.get(['logs'], function(result) {
            let logs = result.logs || [];
            logs.push(message);
            if (logs.length > 100) logs = logs.slice(-100);
            chrome.storage.local.set({ logs: logs }, function() {
                renderLogs(logs);
            });
        });
    }

    function renderLogs(logs) {
        statusLog.innerHTML = logs.map(item => `<div class="log-item">${item}</div>`).join('');
        statusLog.scrollTop = statusLog.scrollHeight;
    }
});
