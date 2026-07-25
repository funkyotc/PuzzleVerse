@echo off
setlocal enabledelayedexpansion

echo ====================================================
echo   Start Emulator, Build, Install ^& Launch App
echo ====================================================

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

if not defined SDK_DIR (
    echo Error: Could not locate Android SDK directory.
    exit /b 1
)

:: Export SDK environment variables for current process tree
set "ANDROID_SDK_ROOT=%SDK_DIR%"
set "ANDROID_HOME=%SDK_DIR%"
set "PATH=%SDK_DIR%\platform-tools;%SDK_DIR%\emulator;%PATH%"

set "EMULATOR_EXE=%SDK_DIR%\emulator\emulator.exe"
set "ADB_EXE=%SDK_DIR%\platform-tools\adb.exe"

where adb >nul 2>nul
if %ERRORLEVEL% equ 0 (
    set "ADB_EXE=adb"
)

:: Step 1: Check if a device/emulator is already running
echo [1/4] Checking for active Android devices...
"%ADB_EXE%" get-state >nul 2>nul
if %ERRORLEVEL% equ 0 (
    echo Active device/emulator found. Skipping emulator launch.
    goto :BUILD_AND_RUN
)

:: Step 2: Find AVD name and launch emulator
echo.
echo [2/4] Finding available Android Virtual Devices (AVDs)...
set "AVD_NAME="
for /f "tokens=*" %%a in ('"%EMULATOR_EXE%" -list-avds -sdk_root "%SDK_DIR%" 2^>nul') do (
    if not defined AVD_NAME set "AVD_NAME=%%a"
)

if not defined AVD_NAME (
    echo Error: No AVDs found! Please create an AVD in Android Studio.
    exit /b 1
)

echo Starting emulator with AVD: %AVD_NAME%
start "" "%EMULATOR_EXE%" -avd %AVD_NAME% -sdk_root "%SDK_DIR%"

echo Waiting for emulator to connect...
"%ADB_EXE%" wait-for-device

:WAIT_BOOT
ping 127.0.0.1 -n 3 >nul
for /f "tokens=*" %%b in ('"%ADB_EXE%" shell getprop sys.boot_completed 2^>nul') do set "BOOT_STATUS=%%b"
set "BOOT_STATUS=%BOOT_STATUS:~0,1%"
if not "%BOOT_STATUS%"=="1" (
    echo Waiting for Android system boot...
    goto :WAIT_BOOT
)
echo Emulator is ready!

:BUILD_AND_RUN
echo.
echo [3/4] Building and installing debug APK...
call gradlew.bat installDebug
if %ERRORLEVEL% neq 0 (
    echo Error: Gradle installDebug failed!
    exit /b %ERRORLEVEL%
)

echo.
echo [4/4] Opening PuzzleVerse on emulator...
"%ADB_EXE%" shell am start -n com.funkyotc.puzzleverse/.MainActivity
if %ERRORLEVEL% equ 0 (
    echo Success! App launched.
) else (
    echo Error launching app via ADB.
)

endlocal
