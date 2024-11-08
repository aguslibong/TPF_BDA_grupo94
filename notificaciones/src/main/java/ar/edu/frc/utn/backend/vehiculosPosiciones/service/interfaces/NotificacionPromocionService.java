package ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces;

import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.NotificacionPromocionDTO;

import java.util.List;

public interface NotificacionPromocionService extends Servicio<NotificacionPromocionDTO, Integer>{
    void createAll(List<NotificacionPromocionDTO> notificacion);
}
