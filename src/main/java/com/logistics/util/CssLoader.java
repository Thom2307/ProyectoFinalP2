package com.logistics.util;

import java.net.URL;

public class CssLoader {
    private static final String CSS_PATH = "/styles.css";
    
    public static String getCssPath() {
        URL resource = CssLoader.class.getResource(CSS_PATH);
        if (resource == null) {
            System.err.println("Warning: No se pudo encontrar styles.css. La aplicación funcionará sin estilos personalizados.");
            return null;
        }
        return resource.toExternalForm();
    }
}

