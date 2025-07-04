@echo off
echo ========================================
echo   Bookstore Application Launcher
echo ========================================
echo.
echo Starting with H2 Database...
echo Database will be created at ./data/bookstore
echo.

echo Setting up Java environment...
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.15.6-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

echo Checking Java version...
java -version
echo.

echo Compiling application...
call gradlew clean bootJar
echo.

echo Starting application...
java -jar build\libs\bookstore-0.0.1-SNAPSHOT.jar

pause 