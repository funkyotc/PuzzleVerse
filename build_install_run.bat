@echo off
setlocal enabledelayedexpansion

echo =========================================
echo   Building, Installing ^& Opening App
echo =========================================

:: Locate Android SDK
set "SDK_DIR="
if defined ANDROID_HOME (
    set "SDK_DIR=%ANDROID_HOME%"
) else if defined ANDROID_SDK_ROOT (
    set "SDK_DIR=%ANDROID_SDK_ROOT%"
) else if exist "local.properties" (
    for /f "tokens=1,* delims==" %%i in ('type local.properties ^| findstr /b /c:"sdk.dir"') do (
        set "RAW_SDK=%%j"
        set "RAW_SDK=!RAW_SDK:\:=:!"
        set "RAW_SDK=!RAW_SDK:\\=\!"
        set "SDK_DIR=!RAW_SDK!"
    )
) else if exist "%LOCALAPPDATA%\Android\Sdk" (
    set "SDK_DIR=%LOCALAPPDATA%\Android\Sdk"
)

if defined SDK_DIR (
    set "ANDROID_SDK_ROOT=%SDK_DIR%"
    set "ANDROID_HOME=%SDK_DIR%"
    set "PATH=%SDK_DIR%\platform-tools;%SDK_DIR%\emulator;%PATH%"
)

:: Locate ADB
set "ADB_CMD=adb"
where adb >nul 2>nul
if %ERRORLEVEL% neq 0 (
    if defined SDK_DIR (
        set "ADB_CMD=%SDK_DIR%\platform-tools\adb.exe"
    )
)

echo [1/3] Building and installing debug APK...
call gradlew.bat installDebug
if %ERRORLEVEL% neq 0 (
    echo Error: Gradle installDebug failed!
    exit /b %ERRORLEVEL%
)

echo.
echo [2/3] Verifying connected device...
"%ADB_CMD%" devices

echo.
echo [3/3] Launching PuzzleVerse...
"%ADB_CMD%" shell am start -n com.funkyotc.puzzleverse/.MainActivity
if %ERRORLEVEL% equ 0 (
    echo Success! App launched.
) else (
    echo Error launching app via ADB.
)

endlocal
