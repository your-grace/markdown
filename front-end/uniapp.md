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

##### 升级
```javascript
export default function appUpdate() {
	plus.runtime.getProperty(plus.runtime.appid, function(wgtinfo) {
		let param = {
			"versionCode":wgtinfo.versionCode
		}
		if(wgtinfo.versionCode == 1){
			tip.toast("我是未升级版本")
		}
		api.updateCheck(param).then((res)=>{
			let result = res.data
			if(result.message == "needUpdate"){
				uni.showModal({
					title: '更新提示',
					content: "有新版本，确认升级吗？",
					success: (showResult) => {
						if (showResult.confirm) {
							plus.nativeUI.toast("正在准备环境，请稍后!");
							var dtask = plus.downloader.createDownload(apiUrl.split('/').slice(0,-1).join('/')+"/HACMESPDA.apk", {
								method: 'GET',
								filename: '_doc/update/'
							}, function(d, status) {
								if (status == 200) {
									var path = d.filename; //下载apk
									// plus.runtime.install(path); // 自动安装apk文件
								} else {
									plus.nativeUI.alert('版本更新失败:' + status);
								}
							});
							dtask.start();
							var showLoading = plus.nativeUI.showWaiting("检查更新...");
							var prg = 0;
							dtask.addEventListener("statechanged",(task,status)=>{
								switch (task.state) {
									case 1:
                                        showLoading.setTitle("正在下载");
                                        break;
									case 2:
                                        showLoading.setTitle("已连接到服务器");
                                        break;
									case 3:
                                        prg = parseInt(parseFloat(task.downloadedSize) / parseFloat(task.totalSize) * 100);
                                        if (prg % 1 == 0) {
                                        // 让百分比 10% 增长，如果这里不这么处理  出现 堆栈内存溢出的问题，有知道原因的大神指导一下哈
                                            showLoading.setTitle("已下载" + prg + "%");
                                        }
                                        if(prg == 100){
                                            plus.nativeUI.closeWaiting();
                                        }
								}
							});
						}
					}
				})
			}
		}).catch((err)=>{
			let msg = err.data.message || "请求出现错误，请稍后再试"
			tip.toast(msg)
		})
	})
}
```
