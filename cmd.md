
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

