package ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces;


import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.NotificacionEmpleadoDTO;

public interface NotificacionEmpleadoService extends Servicio<NotificacionEmpleadoDTO, Integer> {
    String sendSmsMessage(NotificacionEmpleadoDTO notificacion);
}
