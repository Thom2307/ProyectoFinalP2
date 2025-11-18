package com.logistics.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GeneradorReportes {
    
    public static void generarCSV(List<Map<String, Object>> datos, String[] headers, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            
            for (Map<String, Object> fila : datos) {
                Object[] valores = new Object[headers.length];
                for (int i = 0; i < headers.length; i++) {
                    valores[i] = fila.get(headers[i]);
                }
                csvPrinter.printRecord(valores);
            }
            
            csvPrinter.flush();
        } catch (IOException e) {
            System.err.println("Error al generar CSV: " + e.getMessage());
        }
    }
    
    public static void generarPDF(String contenido, String rutaArchivo) {
        // Implementación básica - en producción usaría iText o similar
        try (FileWriter writer = new FileWriter(rutaArchivo.replace(".pdf", ".txt"))) {
            writer.write(contenido);
        } catch (IOException e) {
            System.err.println("Error al generar PDF: " + e.getMessage());
        }
    }
}

