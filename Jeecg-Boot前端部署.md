#### Jeecg-Boot前端服务器部署详细流程
> 针对问题：  
1.图片资源不能正常显示  
2.网站刷新网站报404错误  
3.部署的操作步骤

#### 准备工作
- 部署生产环境，修改.env.production文件中url
```plain
NODE_ENV=production
VUE_APP_API_BASE_URL=http://生产环境ip:8080/jeecg-boot
VUE_APP_CAS_BASE_URL=http://生产环境ip:8888/cas
VUE_APP_ONLINE_BASE_URL=http://fileview.jeecg.com/onlinePreview
```
- 若要是在本地测试，访问后端测试环境，修改.env.development文件中的url
```plain
NODE_ENV=development
VUE_APP_API_BASE_URL=http://测试环境ip:8080/jeecg-boot
VUE_APP_CAS_BASE_URL=http://cas.example.org:8443/cas
VUE_APP_ONLINE_BASE_URL=http://fileview.jeecg.com/onlinePreview
```
#### 1.打包项目
```js
npm run build
```
> note:  
1.vue.config.js文件中这段注释`
//打包app时放开改配置
//publicPath:'/'
`是误导，不用修改  
2.router/index.js文件的Router模式`mode:'history'`正常部署不用动，简易部署方案修改成`mode:'hash'`  
3.运行打包后的命令，会在项目内生成对应的dist文件，此文件就是要部署到服务器的文件
#### 2.上传
> 根据个人情况选择：  
1.上传dist文件到nginx文件夹下的html文件中  
2.服务器上新建一个文件来存放项目文件  
#### 3.nginx
```nginx
server{
    listen 81;#监听的端口，本地访问ip:81
    server_name localhost;#有域名配置域名
    #解决Router(mode: 'history')模式下，刷新路由地址不能找到页面的问题
    location / {
        root   /usr/gyimom/;#项目文件存放位置,可自定义或者nginx/html文件下
        index  index.html index.htm;
        if (!-e $request_filename) {
            rewrite ^(.*)$ /index.html?s=$1 last;
            break;
        }
    }
    #后台服务配置，配置了这个location便可以通过http://域名/jeecg-boot/xxxx 访问		
    location ^~ /jeecg-boot {
        proxy_pass              http://127.0.0.1:8080/jeecg-boot/;
        proxy_set_header        Host 127.0.0.1;
        proxy_set_header        X-Real-IP $remote_addr;
        proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}

```
nginx命令
```bash
#使用前提进入到nginx目录下的sbin目录
#或者nginx文件绝对路径形式
./nginx -v  #查看nginx版本
./nginx #启动nginx
./nginx -s reload   #重启（重载nginx配置）
./nginx -s stop #关闭nginx
./nginx -t  #验证nginx配置文件
```
> nginx依赖  
1.pcre  
2.zlib  
3.openssl  
4.gcc  

nginx端口号可自定义设置，但需注意端口的放行
#### 4.linux放行端口
以上述nginx监听的81端口为例  
> 1.查看防火墙状态  
若是开启状态越过第2步，关闭顺序操作
```bash
firewall-cmd --state
````
> 2.开启防火墙
```bash
systemctl start firewalld.service
````
> 3.查看指定端口放行状态（yes/or）
```bash
firewall-cmd --query-port=81/tcp
````
> 4.放行指定端口
```bash
firewall-cmd --zone=public --add-port=81/tcp --permanent
````
> 5.重启防火墙
```bash
systemctl restart firewalld.service
````
> 6.重新载入配置
```bash
firewall-cmd --reload
```
#### 参考链接
- [Jeecg-Boot官方文档](http://doc.jeecg.com/2043868)  
- [jeecg项目部署笔记](https://blog.csdn.net/sunshine641/article/details/112761710)  
- [Nginx安装和常用命令](https://www.cnblogs.com/hhddd-1024/p/14515377.html)
- [Linux如何放行指定端口](https://www.cnblogs.com/ubiquitousShare/p/13135747.html)
- [宝塔面板](https://www.bt.cn/)