@echo off
setlocal EnableDelayedExpansion

set "WRAPPER_DIR=%~dp0.mvn\wrapper"
set "WRAPPER_PROPS=%WRAPPER_DIR%\maven-wrapper.properties"

for /f "tokens=2 delims==" %%i in ('findstr "distributionUrl" "%WRAPPER_PROPS%"') do set "DIST_URL=%%i"

set "DIST_HOME=%USERPROFILE%\.m2\wrapper\dists"
set "MVN_CMD="

for /f "delims=" %%f in ('where /r "%DIST_HOME%\maven" mvn.cmd 2^>nul') do set "MVN_CMD=%%f"

if defined MVN_CMD goto run

:download
echo Downloading Maven...
powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$ProgressPreference='SilentlyContinue'; New-Item -ItemType Directory -Force -Path '%DIST_HOME%' | Out-Null; Invoke-WebRequest -Uri '%DIST_URL%' -OutFile '%DIST_HOME%\maven.zip'; Expand-Archive -Path '%DIST_HOME%\maven.zip' -DestinationPath '%DIST_HOME%\maven' -Force"

for /f "delims=" %%f in ('where /r "%DIST_HOME%\maven" mvn.cmd 2^>nul') do set "MVN_CMD=%%f"
if defined MVN_CMD goto run
echo Failed to install Maven wrapper.
exit /b 1

:run
call "%MVN_CMD%" %*
exit /b %errorlevel%
