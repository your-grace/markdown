### 1.安装Chrome Debug插件

![image-20220307091004242](C:\Users\liqiubo\AppData\Roaming\Typora\typora-user-images\image-20220307091004242.png)

### 2.创建Debug配置文件

![image-20220307091131542](C:\Users\liqiubo\AppData\Roaming\Typora\typora-user-images\image-20220307091131542.png)

> 修改配置文件，内容如下
>
> ```js
> {
>     // 使用 IntelliSense 了解相关属性。 
>     // 悬停以查看现有属性的描述。
>     // 欲了解更多信息，请访问: https://go.microsoft.com/fwlink/?linkid=830387
>     "version": "0.2.0",
>     "configurations": [
>         {
>             "type": "chrome",
>             "request": "launch",
>             "name": "vuejs: chrome",
>             "url": "http://localhost:3000",
>             "webRoot": "${workspaceFolder}/src",
>             "breakOnLoad": true,
>             "sourceMapPathOverrides": {
>                 "webpack:///src/*": "${webRoot}/*",
>             }
>         }, "webRoot": "${workspaceFolder}"
>     ]
> }
> ```
>
> url中的端口是前端实际的访问端口

### 3.修改vue.config.js配置

> 如果你使用的是 Vue CLI 2，请设置并更新 `config/index.js` 内的 `devtool` property：
>
> ```js
> devtool: 'source-map',
> ```
>
> 如果你使用的是 Vue CLI 3，请设置并更新 `vue.config.js` 内的 `devtool` property：
>
> ```js
> module.exports = {
>       configureWebpack: {
>         	devtool: 'source-map',
>       },
> }
> ```
>
> 到这步应该会有问题了，配置不对，触发断点无变化，断点一直呈现灰色
>
> 原因就是源码配置问题，大部分项目中已经有了`configureWebpack`的配置，所以不知道如何添加
>
> 正确的姿势如下
>
> ```js
> configureWebpack: config => {
>     //生产环境取消 console.log
>     if (process.env.NODE_ENV === 'production') {
>    	config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true
>     }
> },
> //新添加配置
> configureWebpack: {
>    	devtool: 'source-map',
> },
> ```

### 4.开启调试

> 以调试方式启动项目

![image-20220307092438776](C:\Users\liqiubo\AppData\Roaming\Typora\typora-user-images\image-20220307092438776.png)

> 启动调试插件，开始调试

![image-20220307092601548](C:\Users\liqiubo\AppData\Roaming\Typora\typora-user-images\image-20220307092601548.png)

> 设置断点，启动插件后触发断点就可以在Vs Code中看到已经命中断点了

![image-20220307093133507](C:\Users\liqiubo\AppData\Roaming\Typora\typora-user-images\image-20220307093133507.png)

### 参考链接

- [在VS Code中调试](https://v3.cn.vuejs.org/cookbook/debugging-in-vscode.html#%E5%85%88%E5%86%B3%E6%9D%A1%E4%BB%B6)