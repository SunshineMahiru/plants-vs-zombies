@echo off
chcp 65001 >nul
cd /d "%~dp0"
echo 正在编译项目...
D:\java\jdk-21.0.6\bin\javac.exe -encoding UTF-8 -sourcepath src -d bin src\plantgame\*.java
if %errorlevel%==0 (
    echo 编译成功！
    echo 正在启动游戏...
    D:\java\jdk-21.0.6\bin\java.exe -cp bin plantgame.Enter
) else (
    echo 编译失败，请检查代码错误
    pause
)