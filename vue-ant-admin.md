### vue-ant-admin路由模式、部署到服务器相应配置修改

#### 路由模式修改

> 未修改前，地址带#，现修改为`mode='history'`
>
> 修改`src/router/index.js`文件
>
> ```js
> function initRouter(isAsync) {
>   const options = isAsync ? require('./async/config.async').default : require('./config').default
>   formatRoutes(options.routes)
>   // 路由模式
>   options.mode='history'
>   return new Router(options)
> }
> ```

#### 部署到服务器配置修改

> 修改`vue.config.js`文件
>
> ```js
> //注释或删除如下代码，相关的代码也可酌情删除或注释掉
> if (isProd) {
>    	config.externals = assetsCDN.externals
> }
> ```