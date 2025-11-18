@echo off
REM Script to generate Javadoc documentation for Windows
REM Update JAVAFX_PATH to point to your JavaFX SDK installation

REM Example: set JAVAFX_PATH=C:\path\to\javafx-sdk-21\lib

if "%JAVAFX_PATH%"=="" (
    echo JAVAFX_PATH not set. Please set it to your JavaFX SDK lib directory.
    echo Example: set JAVAFX_PATH=C:\path\to\javafx-sdk-21\lib
    exit /b 1
)

javadoc -d docs -sourcepath src -subpackages photos -author -classpath "%JAVAFX_PATH%\*"

echo Javadoc generated in docs\ directory

