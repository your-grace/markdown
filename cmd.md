### cmd常用

```tex
control：控制面板
msconfig：服务 	
regedit：注册表
notepad：记事本	
mstsc：远程计算机
生成公钥ssh-keygen -t rsa
端口占用处理
这里的1099指的端口号，根据自己报错被占用的端口号替换
netstat -ano | findstr “1099”	
这里的10136是指具体进程号，要根据自己的替换掉。
tasklist | findstr “10136”
同样，这里的10136还是要根据具体情况来替换掉
taskkill /pid 10136 -f 
删除目录
rd /s /q directoryName
删除文件
del fileName
将编码方式设置为 GBK：
chcp 936
使用 Notepad++ 可以在 “Encoding” 菜单中选择“GBK”编码方式，然后将脚本保存为“ANSI”格式，即可将脚本文件转换为 GBK 编码方式。
```

### 脚本

```bash
#删除文件夹
@echo off
set /p folderpath="请输入要删除的文件夹路径："
rd /s /q %folderpath%
echo 删除完成！
pause
#删除文件
@echo off
set /p filepath="请输入要删除的文件路径："
del %filepath%
echo 删除完成！
pause
```

