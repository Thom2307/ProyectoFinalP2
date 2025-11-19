@echo off
echo Compilando proyecto...
call mvn clean compile
echo.
echo Ejecutando aplicacion JavaFX...
call mvn javafx:run
pause

