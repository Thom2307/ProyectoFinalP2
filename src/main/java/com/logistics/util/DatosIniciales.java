package com.logistics.util;

import com.logistics.model.entities.Usuario;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Repartidor;
import com.logistics.model.enums.DisponibilidadRepartidor;
import com.logistics.patterns.creational.singleton.InMemoryDatabase;

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
        // Agregar direcciones del Quindío
        Direccion dir1 = new Direccion("usr-dir1", "Casa", "Calle 15 #4-25", "Armenia", 4.5339, -75.6811);
        Direccion dir2 = new Direccion("usr-dir2", "Oficina", "Carrera 20 #5-30", "Calarcá", 4.5333, -75.6333);
        usuarioPrueba.getDirecciones().add(dir1);
        usuarioPrueba.getDirecciones().add(dir2);
        usuarioPrueba.getMetodosPago().add("TARJETA");
        usuarioPrueba.getMetodosPago().add("PSE");
        db.getUsuarios().put(usuarioPrueba.getIdUsuario(), usuarioPrueba);
        // Guardar usuario de prueba en archivo .txt (sobrescribe)
        UsuarioFileManager.guardarUsuario(usuarioPrueba);
        
        // Repartidores del Quindío
        Repartidor r1 = new Repartidor("r1", "Carlos Rodríguez", "3100000001", "Moto");
        r1.setDisponibilidad(DisponibilidadRepartidor.DISPONIBLE);
        db.getRepartidores().put(r1.getIdRepartidor(), r1);
        
        Repartidor r2 = new Repartidor("r2", "Ana López", "3100000002", "Bicicleta");
        r2.setDisponibilidad(DisponibilidadRepartidor.DISPONIBLE);
        db.getRepartidores().put(r2.getIdRepartidor(), r2);
        
        Repartidor r3 = new Repartidor("r3", "Pedro Martínez", "3100000003", "Moto");
        r3.setDisponibilidad(DisponibilidadRepartidor.EN_RUTA);
        db.getRepartidores().put(r3.getIdRepartidor(), r3);
        
        Repartidor r4 = new Repartidor("r4", "Diego Quintero", "3100000004", "Moto");
        r4.setDisponibilidad(DisponibilidadRepartidor.DISPONIBLE);
        db.getRepartidores().put(r4.getIdRepartidor(), r4);
        
        Repartidor r5 = new Repartidor("r5", "Sandra Cardona", "3100000005", "Bicicleta");
        r5.setDisponibilidad(DisponibilidadRepartidor.DISPONIBLE);
        db.getRepartidores().put(r5.getIdRepartidor(), r5);
        
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("DATOS INICIALES CARGADOS");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("- Administrador: Inicializado y guardado en administrador.txt");
        System.out.println("- Usuario de Prueba: Inicializado y guardado en usuarios_registrados.txt");
        System.out.println("- Repartidores: " + db.getRepartidores().size());
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

