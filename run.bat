@echo off
chcp 65001 > nul
cd /d "%~dp0"
echo Compiling project...
"D:\java\jdk-21.0.6\bin\javac.exe" -encoding UTF-8 -sourcepath src -d bin src\plantgame\*.java
if %errorlevel%==0 (
    echo Compilation successful!
    echo Starting game...
    "D:\java\jdk-21.0.6\bin\java.exe" -cp bin plantgame.Enter
) else (
    echo Compilation failed, check code errors
    pause
)