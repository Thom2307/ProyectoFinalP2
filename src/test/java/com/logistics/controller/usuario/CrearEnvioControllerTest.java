package com.logistics.controller.usuario;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.PagoDTO;
import com.logistics.model.dto.TarifaDTO;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Usuario;
import com.logistics.patterns.creacional.singleton.InMemoryDatabase;
import com.logistics.patterns.estructural.facade.EnvioFacade;
import com.logistics.repository.UsuarioRepository;
import com.logistics.service.PagoService;
import com.logistics.service.TarifaService;
import com.logistics.util.NavigationManager;
import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearEnvioControllerTest {

    @Test
    void testCargarDirecciones() {
        // Arrange
        ComboBox<String> comboBox = mock(ComboBox.class);
        javafx.collections.ObservableList<String> items = mock(javafx.collections.ObservableList.class);
        when(comboBox.getItems()).thenReturn(items);
        
        List<Direccion> direcciones = new ArrayList<>();
        Direccion dir1 = new Direccion("d1", "Casa", "Calle 1", "Ciudad A", 19.4326, -99.1332);
        Direccion dir2 = new Direccion("d2", "Oficina", "Calle 2", "Ciudad B", 20.6597, -103.3496);
        direcciones.add(dir1);
        direcciones.add(dir2);

        try (MockedStatic<InMemoryDatabase> mockedDB = mockStatic(InMemoryDatabase.class)) {
            InMemoryDatabase db = mock(InMemoryDatabase.class);
            Map<String, Usuario> usuarios = new HashMap<>();
            Usuario usuario = new Usuario();
            usuario.getDirecciones().clear();
            usuario.getDirecciones().addAll(direcciones);
            usuarios.put("u1", usuario);
            when(db.getUsuarios()).thenReturn(usuarios);
            mockedDB.when(InMemoryDatabase::getInstance).thenReturn(db);
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act
            controller.cargarDirecciones(comboBox);
            
            // Verify
            verify(comboBox, times(1)).getItems();
            verify(items, times(1)).clear();
            verify(items, atLeast(1)).add(anyString());
        }
    }

    @Test
    void testCrearEnvio_Exitoso() {
        // Arrange
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String usuarioId = "u12345678";
        String origenTexto = "Casa - Calle 1, Ciudad A";
        String destinoTexto = "Oficina - Calle 2, Ciudad B";
        double peso = 5.0;
        List<String> servicios = new ArrayList<>();
        servicios.add("SEGURO");
        String metodoPago = "TARJETA";

        Direccion origen = new Direccion("d1", "Casa", "Calle 1", "Ciudad A", 19.4326, -99.1332);
        Direccion destino = new Direccion("d2", "Oficina", "Calle 2", "Ciudad B", 20.6597, -103.3496);
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioId);
        usuario.getDirecciones().clear();
        usuario.getDirecciones().add(origen);
        usuario.getDirecciones().add(destino);

        EnvioDTO envioEsperado = new EnvioDTO();
        envioEsperado.setIdEnvio("e12345678");

        try (MockedStatic<InMemoryDatabase> mockedDB = mockStatic(InMemoryDatabase.class);
             MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioFacade> mockedFacade = mockConstruction(EnvioFacade.class,
                (mock, context) -> {
                    when(mock.crearEnvioCompleto(any(), any(), anyDouble(), any(), anyList(), anyString()))
                        .thenReturn(envioEsperado);
                });
             MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById(usuarioId)).thenReturn(usuario);
                })) {
            
            InMemoryDatabase db = mock(InMemoryDatabase.class);
            Map<String, Usuario> usuarios = new HashMap<>();
            usuarios.put(usuarioId, usuario);
            when(db.getUsuarios()).thenReturn(usuarios);
            mockedDB.when(InMemoryDatabase::getInstance).thenReturn(db);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act
            EnvioDTO resultado = controller.crearEnvio(origenTexto, destinoTexto, peso, servicios, metodoPago);
            
            // Assert
            assertNotNull(resultado);
            assertEquals("e12345678", resultado.getIdEnvio());
        }
    }

    @Test
    void testCrearEnvio_UsuarioNoEncontrado() {
        // Arrange
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String usuarioId = "u99999999";
        String origenTexto = "Casa - Calle 1, Ciudad A";
        String destinoTexto = "Oficina - Calle 2, Ciudad B";
        double peso = 5.0;
        List<String> servicios = new ArrayList<>();
        String metodoPago = "TARJETA";

        try (MockedStatic<InMemoryDatabase> mockedDB = mockStatic(InMemoryDatabase.class);
             MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById(usuarioId)).thenReturn(null);
                })) {
            
            InMemoryDatabase db = mock(InMemoryDatabase.class);
            when(db.getUsuarios()).thenReturn(new HashMap<>());
            mockedDB.when(InMemoryDatabase::getInstance).thenReturn(db);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act & Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> controller.crearEnvio(origenTexto, destinoTexto, peso, servicios, metodoPago));
            
            assertEquals("Usuario no encontrado", exception.getMessage());
        }
    }

    @Test
    void testCrearEnvio_DireccionesInvalidas() {
        // Arrange
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String usuarioId = "u12345678";
        String origenTexto = "Direccion Invalida";
        String destinoTexto = "Otra Direccion Invalida";
        double peso = 5.0;
        List<String> servicios = new ArrayList<>();
        String metodoPago = "TARJETA";

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioId);
        usuario.getDirecciones().clear();

        try (MockedStatic<InMemoryDatabase> mockedDB = mockStatic(InMemoryDatabase.class);
             MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById(usuarioId)).thenReturn(usuario);
                })) {
            
            InMemoryDatabase db = mock(InMemoryDatabase.class);
            Map<String, Usuario> usuarios = new HashMap<>();
            usuarios.put(usuarioId, usuario);
            when(db.getUsuarios()).thenReturn(usuarios);
            mockedDB.when(InMemoryDatabase::getInstance).thenReturn(db);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act & Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> controller.crearEnvio(origenTexto, destinoTexto, peso, servicios, metodoPago));
            
            assertEquals("Direcciones no válidas. Por favor seleccione direcciones válidas.", exception.getMessage());
        }
    }

    @Test
    void testCrearEnvio_OrigenIgualDestino() {
        // Arrange
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String usuarioId = "u12345678";
        String direccionTexto = "Casa - Calle 1, Ciudad A";
        double peso = 5.0;
        List<String> servicios = new ArrayList<>();
        String metodoPago = "TARJETA";

        Direccion direccion = new Direccion("d1", "Casa", "Calle 1", "Ciudad A", 19.4326, -99.1332);
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioId);
        usuario.getDirecciones().clear();
        usuario.getDirecciones().add(direccion);

        try (MockedStatic<InMemoryDatabase> mockedDB = mockStatic(InMemoryDatabase.class);
             MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById(usuarioId)).thenReturn(usuario);
                })) {
            
            InMemoryDatabase db = mock(InMemoryDatabase.class);
            Map<String, Usuario> usuarios = new HashMap<>();
            usuarios.put(usuarioId, usuario);
            when(db.getUsuarios()).thenReturn(usuarios);
            mockedDB.when(InMemoryDatabase::getInstance).thenReturn(db);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act & Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> controller.crearEnvio(direccionTexto, direccionTexto, peso, servicios, metodoPago));
            
            assertEquals("El origen y destino no pueden ser la misma dirección.", exception.getMessage());
        }
    }

    @Test
    void testObtenerPagoPorEnvio_Exitoso() {
        // Arrange
        String idEnvio = "e12345678";
        PagoDTO pagoEsperado = new PagoDTO();
        pagoEsperado.setIdPago("p12345678");
        pagoEsperado.setIdEnvio(idEnvio);
        pagoEsperado.setMonto(150.0);

        List<PagoDTO> pagos = new ArrayList<>();
        pagos.add(pagoEsperado);

        try (MockedConstruction<PagoService> mockedService = mockConstruction(PagoService.class,
                (mock, context) -> {
                    when(mock.obtenerPagosPorEnvio(idEnvio)).thenReturn(pagos);
                })) {
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act
            PagoDTO resultado = controller.obtenerPagoPorEnvio(idEnvio);
            
            // Assert
            assertNotNull(resultado);
            assertEquals("p12345678", resultado.getIdPago());
            assertEquals(idEnvio, resultado.getIdEnvio());
            assertEquals(150.0, resultado.getMonto());
        }
    }

    @Test
    void testObtenerPagoPorEnvio_NoEncontrado() {
        // Arrange
        String idEnvio = "e99999999";
        List<PagoDTO> pagosVacios = new ArrayList<>();

        try (MockedConstruction<PagoService> mockedService = mockConstruction(PagoService.class,
                (mock, context) -> {
                    when(mock.obtenerPagosPorEnvio(idEnvio)).thenReturn(pagosVacios);
                })) {
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act
            PagoDTO resultado = controller.obtenerPagoPorEnvio(idEnvio);
            
            // Assert
            assertNull(resultado);
        }
    }

    @Test
    void testObtenerDireccionPorTexto_Exitoso() {
        // Arrange
        String texto = "Casa - Calle 1, Ciudad A";
        Direccion direccionEsperada = new Direccion("d1", "Casa", "Calle 1", "Ciudad A", 19.4326, -99.1332);

        try (MockedStatic<InMemoryDatabase> mockedDB = mockStatic(InMemoryDatabase.class)) {
            InMemoryDatabase db = mock(InMemoryDatabase.class);
            Map<String, Usuario> usuarios = new HashMap<>();
            Usuario usuario = new Usuario();
            usuario.getDirecciones().clear();
            usuario.getDirecciones().add(direccionEsperada);
            usuarios.put("u1", usuario);
            when(db.getUsuarios()).thenReturn(usuarios);
            mockedDB.when(InMemoryDatabase::getInstance).thenReturn(db);
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act
            Direccion resultado = controller.obtenerDireccionPorTexto(texto);
            
            // Assert
            assertNotNull(resultado);
            assertEquals("Casa", resultado.getAlias());
            assertEquals("Calle 1", resultado.getCalle());
        }
    }

    @Test
    void testObtenerDireccionPorTexto_NoEncontrado() {
        // Arrange
        String texto = "Direccion Inexistente";

        try (MockedStatic<InMemoryDatabase> mockedDB = mockStatic(InMemoryDatabase.class)) {
            InMemoryDatabase db = mock(InMemoryDatabase.class);
            when(db.getUsuarios()).thenReturn(new HashMap<>());
            mockedDB.when(InMemoryDatabase::getInstance).thenReturn(db);
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act
            Direccion resultado = controller.obtenerDireccionPorTexto(texto);
            
            // Assert
            assertNull(resultado);
        }
    }

    @Test
    void testCalcularTarifaEstimada() {
        // Arrange
        double latOrigen = 19.4326;
        double lonOrigen = -99.1332;
        double latDestino = 20.6597;
        double lonDestino = -103.3496;
        double peso = 5.0;
        List<String> serviciosAdicionales = new ArrayList<>();
        serviciosAdicionales.add("SEGURO");

        TarifaDTO tarifaEsperada = new TarifaDTO();
        tarifaEsperada.setCostoBase(100.0);
        tarifaEsperada.setCostoTotal(130.0);

        try (MockedStatic<InMemoryDatabase> mockedDB = mockStatic(InMemoryDatabase.class);
             MockedConstruction<TarifaService> mockedService = mockConstruction(TarifaService.class,
                (mock, context) -> {
                    when(mock.calcularTarifa(any(), anyList())).thenReturn(tarifaEsperada);
                })) {
            
            InMemoryDatabase db = mock(InMemoryDatabase.class);
            when(db.getUsuarios()).thenReturn(new HashMap<>());
            mockedDB.when(InMemoryDatabase::getInstance).thenReturn(db);
            
            CrearEnvioController controller = new CrearEnvioController();
            
            // Act
            TarifaDTO resultado = controller.calcularTarifaEstimada(
                latOrigen, lonOrigen, latDestino, lonDestino, peso, serviciosAdicionales);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(100.0, resultado.getCostoBase());
            assertEquals(130.0, resultado.getCostoTotal());
        }
    }
}

