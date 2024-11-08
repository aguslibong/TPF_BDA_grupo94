package ar.edu.frc.utn.backend.notificaciones.service.interfaces;

import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionEmpleadoDTO;
import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionPromocionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NotificacionPromocionService extends Servicio<NotificacionPromocionDTO, Integer>{
    void createAll(List<NotificacionPromocionDTO> notificacion);
}
