@echo off
echo "-------begin-------"
git status
set /p msg=�������ύע��:
git add .
git commit -m %msg%
git pull
git push
echo ���ͳɹ�����%msg%��
echo "--------end!--------"
pause