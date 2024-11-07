package ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces;


import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.NotificacionDTO;

public interface NotificacionService extends Servicio<NotificacionDTO, Integer> {
    void createAll(Iterable<NotificacionDTO> notificacion);
    String sendSmsMessage(NotificacionDTO notificacion);
}
