@echo off
setlocal

REM 设置源文件夹路径和目标zip文件路径
set "sourceFolder=F:\uni\HACMES"

set "destinationFolder=F:\uni"

REM 获取当前日期和时间信息
for /F "tokens=2 delims==." %%G in ('wmic os get LocalDateTime /VALUE') do set "timestamp=%%G"
set "timestamp=%timestamp:~0,8%_%timestamp:~8,6%"

REM 构建目标zip文件名，包含时间戳
set "destinationZip=%destinationFolder%\HACMES_%timestamp%.7z"

REM 压缩文件夹
7z a "%destinationZip%" "%sourceFolder%\*"

endlocal