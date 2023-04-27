@echo off
set /p filepath="请输入要删除的文件路径："
del %filepath%
echo 删除完成！
pause