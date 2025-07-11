(function() {
    const NativeWebSocket = window.WebSocket;
    function ProxyWebSocket(...args) {
        const ws = new NativeWebSocket(...args);
        ws.addEventListener('message', function(event) {
            window.postMessage({ type: '抢单助手WS', data: event.data }, '*');
        });
        return ws;
    }
    ProxyWebSocket.prototype = NativeWebSocket.prototype;
    Object.setPrototypeOf(ProxyWebSocket, NativeWebSocket);
    // 保留所有静态属性
    for (let key in NativeWebSocket) {
        if (NativeWebSocket.hasOwnProperty(key)) {
            ProxyWebSocket[key] = NativeWebSocket[key];
        }
    }
    window.WebSocket = ProxyWebSocket;
    console.log('[抢单助手-hook] WebSocket hook 已注入');
})();