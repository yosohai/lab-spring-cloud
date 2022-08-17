@echo off
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
if '%errorlevel%' NEQ '0' (
goto UACPrompt
) else ( goto gotAdmin )
:UACPrompt
echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
"%temp%\getadmin.vbs"
exit /B
:gotAdmin
if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
pushd "%CD%"
cd /D "%~dp0"

start /d  "D:\ProgramFiles\kafka_2.12-2.8.1\" bin\windows\zookeeper-server-start.bat config\zookeeper.properties

ping -n 3 127.0.0.1>nul   ----等待3秒在启动下一个

start /d  "D:\ProgramFiles\kafka_2.12-2.8.1\" bin\windows\kafka-server-start.bat config\server.properties

ping -n 3 127.0.0.1>nul   ----等待3秒在启动下一个

@echo on
