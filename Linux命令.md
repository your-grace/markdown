
####	Linux常用命令
####	1.cd命令
```bash
cd ..	#退出当前目录
cd /usr	#进入到usr目录
cd ~	#切换到root目录
cd /	#切换到根目录
```
####	2.yum命令和rpm
```bash
yum list	#可安装软件清单
yum install <pakage_name>	#安装指定软件
yum list installed	#已安装软件清单
rpm -qa	#所有安装的软件包
rpm -qa | grep nginx	#在安装的软件包中查询nginx
```
####	3.查看外网IP
```bash
#常用如下，其它写法根据个人需要自行查找
curl cip.cc
curl ifconfig.me
```
####	4.端口命令
```bash
netstat -anp | grep 8888	#查看被打开的8888端口
lsof -i:8888	#查看被占用的8888端口
```
####	5.进程
```bash
ps -ef | grep nginx	#查看nginx进程
```
####	6.压缩和解压
```bash
#压缩
tar -zcvf test.tar.gz test/  #将test文件夹压缩成.gz格式压缩包
tar -jcvf test.tar.bz2 test/ #将test文件夹压缩.bz2格式的压缩包
tar -Jcvf test.tar.xz test/  #将test文件夹压缩.xz格式的压缩包
#解压
tar -zxvf test.tar.gz /test  #解压.gz格式的到test文件夹
tar -jxvf test.tar.bz2 /test #解压.bz2格式的到test文件夹
tar -Jxvf test.tar.xz /test  #解压.xz格式的到test文件夹
```
####	7.文件和目录管理

![image-20220315103311855](C:\Users\liqiubo\AppData\Roaming\Typora\typora-user-images\image-20220315103311855.png)


```bash
#文件第一个字符：l链接 d目录 -文件
ll
ls -l	#ls参数：-a全部文件 -d目录本身 -l长数据串列出
pwd	#当前目录
mkdir	#创建目录
rmdir	#删除空目录
cp	#复制文件或目录
rm	#删除文件或目录
rm -rf	#删除目录及目录下的文件 
rm -rf *	#删除当前目录下的所有文件
mv	#移动文件与目录，或修改文件与目录明名称
```
[命令详情参考](https://www.runoob.com/linux/linux-file-content-manage.html)

####	8.上传rz和下载sz
```bash
#查看可安装lrzsz
yum list | grep lrzsz
#安装对应rpm包
yum install lrzsz-0.12.20-36.el7.lrzsz.x86_64 -y
#输入命令选择本地文件即可
rz
#下载多个文件
sz test.html test2.html
```
####    9.查看日志
```bash
tail -f test.log    #查看实时日志
tail -n 10 test.log #查看最后10行
```
后续涉及新的再补充

#### 本人[博客园](https://www.cnblogs.com/decent/)地址，和CSDN同步更新