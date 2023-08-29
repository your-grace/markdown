##### 获取设备uuid

```javascript
// #ifdef APP-PLUS
this.mark = plus.device.uuid
// #endif
```

##### 获取系统信息

```javascript
uni.getSystemInfo({
    //成功获取的回调函数，返回值为系统信息
    success: (sysinfo) => {
        height = sysinfo.windowHeight;//自行修改，自己需要的高度 此处如底部有其他内容，可以直接---(-50)这种
    },
    complete: () => {
    }
});
```

