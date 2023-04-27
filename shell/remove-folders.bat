@echo off
set /p folderpath="请输入要删除的文件夹路径："
rd /s /q %folderpath%
echo 删除完成！
pause