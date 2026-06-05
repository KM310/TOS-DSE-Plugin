@echo off
echo === Building Plugin (Windows CMD) ===

REM Gradle Wrapper ausführen
call gradlew build

echo.
echo === Build finished ===
pause
