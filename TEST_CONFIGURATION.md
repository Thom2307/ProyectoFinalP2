# Configuración de Pruebas con JavaFX

## Problema
Al ejecutar las pruebas desde el IDE, puede aparecer el error:
```
Module javafx.base not found
```

## Solución

### Opción 1: Configurar el IDE (IntelliJ IDEA)

1. Ve a **Run → Edit Configurations...**
2. Selecciona tu configuración de prueba o crea una nueva
3. En **VM options**, agrega:
   ```
   --add-modules javafx.base,javafx.graphics,javafx.controls,javafx.fxml
   --add-exports javafx.base/com.sun.javafx.runtime=ALL-UNNAMED
   --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED
   --add-opens javafx.base/java.lang=ALL-UNNAMED
   --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED
   ```
4. Guarda la configuración

### Opción 2: Ejecutar desde Maven (Recomendado)

Ejecuta las pruebas usando Maven desde la terminal:

```bash
mvn clean test
```

O para ejecutar una clase específica:

```bash
mvn test -Dtest=CrearEnvioControllerTest
```

### Opción 3: Configurar todas las pruebas en IntelliJ IDEA

1. Ve a **File → Settings → Build, Execution, Deployment → Build Tools → Maven → Runner**
2. Marca **Delegate IDE build/run actions to Maven**
3. Esto hará que el IDE use Maven para ejecutar las pruebas, lo que aplicará automáticamente la configuración del `pom.xml`

### Opción 4: Configuración Global de JUnit en IntelliJ

1. Ve a **Run → Edit Configurations...**
2. Haz clic en **Edit configuration templates...**
3. Selecciona **JUnit**
4. En **VM options**, agrega los mismos argumentos mencionados en la Opción 1
5. Esto aplicará la configuración a todas las pruebas nuevas

## Verificación

Después de configurar, ejecuta cualquier prueba. Debería ejecutarse sin el error de módulos de JavaFX.

