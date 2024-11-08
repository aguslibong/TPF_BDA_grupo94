package ar.edu.frc.utn.backend.notificaciones.service.interfaces;


import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionEmpleadoDTO;

import java.util.List;

public interface NotificacionEmpleadoService extends Servicio<NotificacionEmpleadoDTO, Integer> {
    String sendSmsMessage(NotificacionEmpleadoDTO notificacion);
}
