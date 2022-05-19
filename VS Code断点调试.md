### 1.安装Chrome Debug插件

![在这里插入图片描述](https://img-blog.csdnimg.cn/5aeb66e4cef9419b922fdff4137ad3d1.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5L2g5ru06aOO,size_20,color_FFFFFF,t_70,g_se,x_16)

### 2.创建Debug配置文件

![在这里插入图片描述](https://img-blog.csdnimg.cn/42d6a3ac89e548a19ef56d450b88810b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5L2g5ru06aOO,size_20,color_FFFFFF,t_70,g_se,x_16)

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

![在这里插入图片描述](https://img-blog.csdnimg.cn/4f4303a6d65a4a0dbd9e537394dfacf5.png)

> 启动调试插件，开始调试

![在这里插入图片描述](https://img-blog.csdnimg.cn/a7e98c5b814b4332b95f4b50fb33294a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5L2g5ru06aOO,size_20,color_FFFFFF,t_70,g_se,x_16)

> 设置断点，启动插件后触发断点就可以在Vs Code中看到已经命中断点了

![在这里插入图片描述](https://img-blog.csdnimg.cn/4886b64279ff4316b76619a186de0025.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5L2g5ru06aOO,size_20,color_FFFFFF,t_70,g_se,x_16)

### 参考链接

- [在VS Code中调试](https://v3.cn.vuejs.org/cookbook/debugging-in-vscode.html#%E5%85%88%E5%86%B3%E6%9D%A1%E4%BB%B6)