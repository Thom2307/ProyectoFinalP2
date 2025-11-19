package com.logistics.util;

import com.logistics.model.entities.Envio;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EnvioFileManager {
    private static final String ARCHIVO_ENVIOS = "pedidos_usuario.txt";
    private static final String DIRECTORIO_DATOS = "data";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Guarda un envío en el archivo de texto.
     * Crea el directorio y archivo si no existen, y agrega el envío al final del archivo.
     * 
     * @param envio La entidad Envio a guardar en el archivo
     */
    public static void guardarEnvio(Envio envio) {
        try {
            // Crear directorio si no existe
            Path dirPath = Paths.get(DIRECTORIO_DATOS);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Ruta completa del archivo
            Path archivoPath = dirPath.resolve(ARCHIVO_ENVIOS);
            
            // Si el archivo no existe, crear con encabezado
            boolean archivoExiste = Files.exists(archivoPath);
            
            try (FileWriter fw = new FileWriter(archivoPath.toFile(), true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter pw = new PrintWriter(bw)) {
                
                // Escribir encabezado si es un archivo nuevo
                if (!archivoExiste) {
                    pw.println("=".repeat(80));
                    pw.println("REGISTRO DE PEDIDOS - SISTEMA DE LOGÍSTICA");
                    pw.println("=".repeat(80));
                    pw.println();
                }
                
                // Escribir datos del envío
                pw.println("Fecha de Creación: " + (envio.getFechaCreacion() != null ? 
                    envio.getFechaCreacion().format(formatter) : LocalDateTime.now().format(formatter)));
                pw.println("ID Envío: " + envio.getIdEnvio());
                pw.println("ID Usuario: " + (envio.getUsuario() != null ? envio.getUsuario().getIdUsuario() : "N/A"));
                pw.println("Nombre Usuario: " + (envio.getUsuario() != null ? envio.getUsuario().getNombre() : "N/A"));
                
                // Origen
                if (envio.getOrigen() != null) {
                    pw.println("Origen: " + envio.getOrigen().getCalle() + ", " + envio.getOrigen().getCiudad());
                } else {
                    pw.println("Origen: N/A");
                }
                
                // Destino
                if (envio.getDestino() != null) {
                    pw.println("Destino: " + envio.getDestino().getCalle() + ", " + envio.getDestino().getCiudad());
                } else {
                    pw.println("Destino: N/A");
                }
                
                pw.println("Peso: " + envio.getPeso() + " kg");
                pw.println("Costo: $" + String.format("%.2f", envio.getCosto()));
                pw.println("Estado: " + (envio.getEstado() != null ? envio.getEstado().name() : "N/A"));
                
                if (envio.getRepartidor() != null) {
                    pw.println("Repartidor: " + envio.getRepartidor().getNombre() + " (ID: " + envio.getRepartidor().getIdRepartidor() + ")");
                } else {
                    pw.println("Repartidor: No asignado");
                }
                
                if (envio.getAdicionales() != null && !envio.getAdicionales().isEmpty()) {
                    pw.println("Servicios Adicionales: " + String.join(", ", envio.getAdicionales()));
                } else {
                    pw.println("Servicios Adicionales: Ninguno");
                }
                
                pw.println("-".repeat(80));
                pw.println();
                
                pw.flush();
            }
            
            System.out.println("Envío guardado en archivo: " + archivoPath.toAbsolutePath());
            
        } catch (IOException e) {
            System.err.println("Error al guardar envío en archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la ruta absoluta del archivo donde se guardan los envíos.
     * 
     * @return La ruta absoluta del archivo de envíos
     */
    public static String getRutaArchivo() {
        return Paths.get(DIRECTORIO_DATOS, ARCHIVO_ENVIOS).toAbsolutePath().toString();
    }

    /**
     * Verifica si el archivo de envíos existe en el sistema de archivos.
     * 
     * @return true si el archivo existe, false en caso contrario
     */
    public static boolean archivoExiste() {
        return Files.exists(Paths.get(DIRECTORIO_DATOS, ARCHIVO_ENVIOS));
    }
}

