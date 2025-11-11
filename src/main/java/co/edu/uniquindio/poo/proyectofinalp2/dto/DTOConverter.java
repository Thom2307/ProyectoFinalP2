package dto;

import model.*;

public class DTOConverter {
    public static EnvioDTO convertirEnvio(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setIdEnvio(envio.getIdEnvio());
        dto.setUsuarioNombre(envio.getUsuarioNombre());
        dto.setOrigen(envio.getOrigen() != null ? envio.getOrigen().toString() : "N/A");
        dto.setDestino(envio.getDestino() != null ? envio.getDestino().toString() : "N/A");
        dto.setPeso(envio.getPeso());
        dto.setVolumen(envio.getVolumen());
        dto.setCosto(envio.getCosto());
        dto.setEstado(envio.getEstado());
        dto.setFechaCreacion(envio.getFechaCreacion());
        dto.setFechaEstimadaEntrega(envio.getFechaEstimadaEntrega());
        dto.setRepartidorNombre(envio.getRepartidor() != null ? envio.getRepartidor().getNombre() : "Sin asignar");
        dto.setCantidadServiciosAdicionales(envio.getServiciosAdicionales().size());
        dto.setTienePago(envio.getPago() != null);
        return dto;
    }

    public static PagoDTO convertirPago(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(pago.getIdPago());
        dto.setIdEnvio(pago.getEnvio().getIdEnvio());
        dto.setMonto(pago.getMonto());
        dto.setFecha(pago.getFecha());
        dto.setMetodoPago(pago.getMetodoPago() != null ? pago.getMetodoPago().getTipo() : "N/A");
        dto.setResultado(pago.getResultado());
        return dto;
    }

    public static UsuarioDTO convertirUsuario(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setCantidadDirecciones(usuario.getDirecciones().size());
        dto.setCantidadMetodosPago(usuario.getMetodosPago().size());
        dto.setCantidadEnvios(usuario.getEnvios().size());
        return dto;
    }
}


