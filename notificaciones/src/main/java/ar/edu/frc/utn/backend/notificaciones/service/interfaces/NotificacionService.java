package ar.edu.frc.utn.backend.notificaciones.service.interfaces;


import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionDTO;

public interface NotificacionService extends Servicio<NotificacionDTO, Integer> {
    void createAll(Iterable<NotificacionDTO> notificacion);
    String sendSmsMessage(NotificacionDTO notificacion);
}
