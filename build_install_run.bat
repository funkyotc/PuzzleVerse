@echo off
setlocal enabledelayedexpansion

echo =========================================
echo   Building, Installing ^& Opening App
echo =========================================

:: Locate ADB
set "ADB_CMD=adb"
where adb >nul 2>nul
if %ERRORLEVEL% neq 0 (
    if exist "%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" (
        set "ADB_CMD=%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe"
    ) else if defined ANDROID_HOME (
        set "ADB_CMD=%ANDROID_HOME%\platform-tools\adb.exe"
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
