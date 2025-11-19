# Instrucciones de Ejecución

## Solución al Error: "JavaFX runtime components are missing"

Este error ocurre cuando JavaFX no puede encontrar sus módulos. Sigue estos pasos:

### Opción 1: Usar Maven (Recomendado)

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar con el plugin de JavaFX
mvn javafx:run
```

### Opción 2: Ejecutar desde el IDE

Si estás usando IntelliJ IDEA o Eclipse:

1. **IntelliJ IDEA:**
   - Click derecho en `Main.java`
   - Selecciona "Run 'Main.main()'"
   - Si aparece el error, ve a: Run → Edit Configurations
   - En "VM options", agrega:
     ```
     --module-path /ruta/a/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
     ```
   - O descarga JavaFX SDK desde: https://openjfx.io/
   - Agrega la librería JavaFX al proyecto

2. **Eclipse:**
   - Click derecho en el proyecto → Run As → Java Application
   - Si aparece el error, ve a: Run → Run Configurations
   - En "Arguments" → "VM arguments", agrega:
     ```
     --module-path /ruta/a/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
     ```

### Opción 3: Descargar JavaFX SDK Manualmente

1. Descarga JavaFX SDK 17 desde: https://openjfx.io/
2. Extrae el archivo
3. Ejecuta con:

```bash
java --module-path /ruta/a/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.logistics.Main
```

### Opción 4: Usar Scripts Incluidos

**Windows:**
```cmd
run.bat
```

**Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

### Verificar Instalación

Asegúrate de tener:
- Java 17 o superior
- Maven 3.6 o superior
- JavaFX 17 en el classpath

### Solución Rápida (Si nada funciona)

Si sigues teniendo problemas, puedes ejecutar directamente con:

```bash
mvn clean package
java -cp "target/classes:target/dependency/*" --add-modules javafx.controls,javafx.fxml com.logistics.Main
```

Pero primero necesitas copiar las dependencias:
```bash
mvn dependency:copy-dependencies
```

