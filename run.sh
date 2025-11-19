#!/bin/bash
echo "Compilando proyecto..."
mvn clean compile
echo ""
echo "Ejecutando aplicacion JavaFX..."
mvn javafx:run

