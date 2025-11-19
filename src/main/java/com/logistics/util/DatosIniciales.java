package com.logistics.util;

import com.logistics.model.entities.Usuario;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Repartidor;
import com.logistics.model.entities.Envio;
import com.logistics.model.enums.EstadoRepartidor;
import com.logistics.model.enums.Ruta;
import com.logistics.patterns.creacional.builder.EnvioBuilder;
import com.logistics.patterns.creacional.singleton.InMemoryDatabase;
import com.logistics.service.TarifaService;

public class DatosIniciales {
    
    public static void inicializarDatos() {
        InMemoryDatabase db = InMemoryDatabase.getInstance();
        
        // ============================================
        // USUARIO ADMINISTRADOR (siempre se inicializa)
        // ============================================
        Usuario admin = new Usuario("admin", "Administrador Sistema", "admin@logistics.com", "3000000000");
        admin.setContrasena("admin123");
        admin.setEsAdmin(true);
        // Agregar dirección para el admin
        Direccion adminDir = new Direccion("admin-dir", "Oficina Principal", "Carrera 14 #6-10", "Armenia", 4.5400, -75.6800);
        admin.getDirecciones().add(adminDir);
        admin.getMetodosPago().add("TARJETA");
        db.getUsuarios().put(admin.getIdUsuario(), admin);
        // Guardar admin en archivo .txt (siempre se sobrescribe)
        AdminFileManager.guardarAdmin(admin);
        
        // ============================================
        // USUARIO DE PRUEBA (único usuario de testeo)
        // ============================================
        Usuario usuarioPrueba = new Usuario("usuario", "Usuario Prueba", "usuario@test.com", "3001234567");
        usuarioPrueba.setContrasena("usuario123");
        usuarioPrueba.setEsAdmin(false);
        
        // Agregar direcciones personales del usuario
        Direccion dir1 = new Direccion("usr-dir1", "Casa", "Calle 15 #4-25", "Armenia", 4.5339, -75.6811);
        Direccion dir2 = new Direccion("usr-dir2", "Oficina Personal", "Carrera 20 #5-30", "Calarcá", 4.5333, -75.6333);
        usuarioPrueba.getDirecciones().add(dir1);
        usuarioPrueba.getDirecciones().add(dir2);
        
        // Agregar OFICINAS DE RUTAS en cada municipio
        // Ruta 1: Armenia - Calarcá
        Direccion oficinaArmenia = new Direccion("oficina-armenia", "Oficina Armenia", "Carrera 14 #6-10", "Armenia", 4.5400, -75.6800);
        Direccion oficinaCalarca = new Direccion("oficina-calarca", "Oficina Calarcá", "Carrera 20 #5-30", "Calarcá", 4.5333, -75.6333);
        
        // Ruta 2: Filandia - Quimbaya - Montenegro
        Direccion oficinaFilandia = new Direccion("oficina-filandia", "Oficina Filandia", "Calle Principal #10-20", "Filandia", 4.6750, -75.6583);
        Direccion oficinaQuimbaya = new Direccion("oficina-quimbaya", "Oficina Quimbaya", "Carrera 5 #3-15", "Quimbaya", 4.6233, -75.7617);
        Direccion oficinaMontenegro = new Direccion("oficina-montenegro", "Oficina Montenegro", "Avenida Central #8-25", "Montenegro", 4.5667, -75.7500);
        
        // Ruta 3: Tebaida - Pueblo Tapao
        Direccion oficinaTebaida = new Direccion("oficina-tebaida", "Oficina Tebaida", "Calle 10 #5-10", "Tebaida", 4.4500, -75.7833);
        Direccion oficinaPuebloTapao = new Direccion("oficina-pueblotapao", "Oficina Pueblo Tapao", "Vía Principal", "Pueblo Tapao", 4.4167, -75.8000);
        
        // Ruta 4: Génova - Pijao - Caicedonia
        Direccion oficinaGenova = new Direccion("oficina-genova", "Oficina Génova", "Carrera 3 #2-8", "Génova", 4.3167, -75.7667);
        Direccion oficinaPijao = new Direccion("oficina-pijao", "Oficina Pijao", "Calle 5 #4-12", "Pijao", 4.3333, -75.7000);
        Direccion oficinaCaicedonia = new Direccion("oficina-caicedonia", "Oficina Caicedonia", "Avenida Principal #6-20", "Caicedonia", 4.3333, -75.8333);
        
        // Ruta 5: Circasia - Salento
        Direccion oficinaCircasia = new Direccion("oficina-circasia", "Oficina Circasia", "Carrera 10 #8-15", "Circasia", 4.6167, -75.6333);
        Direccion oficinaSalento = new Direccion("oficina-salento", "Oficina Salento", "Calle Real #12-25", "Salento", 4.6333, -75.5667);
        
        // Agregar todas las oficinas al usuario de prueba
        usuarioPrueba.getDirecciones().add(oficinaArmenia);
        usuarioPrueba.getDirecciones().add(oficinaCalarca);
        usuarioPrueba.getDirecciones().add(oficinaFilandia);
        usuarioPrueba.getDirecciones().add(oficinaQuimbaya);
        usuarioPrueba.getDirecciones().add(oficinaMontenegro);
        usuarioPrueba.getDirecciones().add(oficinaTebaida);
        usuarioPrueba.getDirecciones().add(oficinaPuebloTapao);
        usuarioPrueba.getDirecciones().add(oficinaGenova);
        usuarioPrueba.getDirecciones().add(oficinaPijao);
        usuarioPrueba.getDirecciones().add(oficinaCaicedonia);
        usuarioPrueba.getDirecciones().add(oficinaCircasia);
        usuarioPrueba.getDirecciones().add(oficinaSalento);
        
        usuarioPrueba.getMetodosPago().add("TARJETA");
        usuarioPrueba.getMetodosPago().add("PSE");
        db.getUsuarios().put(usuarioPrueba.getIdUsuario(), usuarioPrueba);
        // Guardar usuario de prueba en archivo .txt (sobrescribe)
        UsuarioFileManager.guardarUsuario(usuarioPrueba);
        
        // Repartidores del Quindío con datos completos y rutas asignadas
        Repartidor r1 = new Repartidor("REP001", "Juan Pérez", "1234567890", "3100000001", "Moto", "Norte");
        r1.setDisponibilidad(EstadoRepartidor.ACTIVO);
        r1.setRuta(Ruta.RUTA_1); // Armenia - Calarcá
        db.getRepartidores().put(r1.getIdRepartidor(), r1);
        
        Repartidor r2 = new Repartidor("REP002", "María García", "0987654321", "3100000002", "Bicicleta", "Sur");
        r2.setDisponibilidad(EstadoRepartidor.EN_RUTA);
        r2.setRuta(Ruta.RUTA_2); // Filandia - Quimbaya - Montenegro
        db.getRepartidores().put(r2.getIdRepartidor(), r2);
        
        Repartidor r3 = new Repartidor("REP003", "Carlos López", "1122334455", "3100000003", "Moto", "Centro");
        r3.setDisponibilidad(EstadoRepartidor.ACTIVO);
        r3.setRuta(Ruta.RUTA_3); // Tebaida - Pueblo Tapao
        db.getRepartidores().put(r3.getIdRepartidor(), r3);
        
        Repartidor r4 = new Repartidor("REP004", "Ana Martínez", "5544332211", "3100000004", "Moto", "Este");
        r4.setDisponibilidad(EstadoRepartidor.INACTIVO);
        r4.setRuta(Ruta.RUTA_4); // Génova - Pijao - Caicedonia
        db.getRepartidores().put(r4.getIdRepartidor(), r4);
        
        Repartidor r5 = new Repartidor("REP005", "Sandra Cardona", "6677889900", "3100000005", "Bicicleta", "Oeste");
        r5.setDisponibilidad(EstadoRepartidor.ACTIVO);
        r5.setRuta(Ruta.RUTA_5); // Circasia - Salento
        db.getRepartidores().put(r5.getIdRepartidor(), r5);
        
        // ============================================
        // ENVÍOS DE PRUEBA (3 envíos en estado SOLICITADO)
        // ============================================
        TarifaService tarifaService = new TarifaService();
        
        // Envío 1: Ruta 1 - Armenia a Calarcá
        Envio envio1 = new EnvioBuilder()
                .withId("ENV001")
                .from(oficinaArmenia)
                .to(oficinaCalarca)
                .weight(2.5)
                .forUser(usuarioPrueba)
                .build();
        // Calcular tarifa
        double costo1 = tarifaService.calcular(envio1.getOrigen(), envio1.getDestino(), envio1.getPeso());
        envio1.setCosto(costo1);
        db.getEnvios().put(envio1.getIdEnvio(), envio1);
        
        // Envío 2: Ruta 2 - Filandia a Montenegro
        Envio envio2 = new EnvioBuilder()
                .withId("ENV002")
                .from(oficinaFilandia)
                .to(oficinaMontenegro)
                .weight(5.0)
                .forUser(usuarioPrueba)
                .build();
        // Calcular tarifa
        double costo2 = tarifaService.calcular(envio2.getOrigen(), envio2.getDestino(), envio2.getPeso());
        envio2.setCosto(costo2);
        db.getEnvios().put(envio2.getIdEnvio(), envio2);
        
        // Envío 3: Ruta 5 - Circasia a Salento
        Envio envio3 = new EnvioBuilder()
                .withId("ENV003")
                .from(oficinaCircasia)
                .to(oficinaSalento)
                .weight(1.8)
                .forUser(usuarioPrueba)
                .build();
        // Calcular tarifa
        double costo3 = tarifaService.calcular(envio3.getOrigen(), envio3.getDestino(), envio3.getPeso());
        envio3.setCosto(costo3);
        db.getEnvios().put(envio3.getIdEnvio(), envio3);
        
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("DATOS INICIALES CARGADOS");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("- Administrador: Inicializado y guardado en administrador.txt");
        System.out.println("- Usuario de Prueba: Inicializado y guardado en usuarios_registrados.txt");
        System.out.println("- Oficinas creadas en todos los municipios de las rutas");
        System.out.println("- Repartidores: " + db.getRepartidores().size() + " (con rutas asignadas)");
        System.out.println("- Envíos de prueba: " + db.getEnvios().size() + " (en estado SOLICITADO)");
        System.out.println();
        System.out.println("CREDENCIALES:");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("ADMINISTRADOR:");
        System.out.println("  Usuario: admin@logistics.com (o 'admin')");
        System.out.println("  Contraseña: admin123");
        System.out.println();
        System.out.println("USUARIO DE PRUEBA:");
        System.out.println("  Correo: usuario@test.com");
        System.out.println("  Contraseña: usuario123");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }
}

