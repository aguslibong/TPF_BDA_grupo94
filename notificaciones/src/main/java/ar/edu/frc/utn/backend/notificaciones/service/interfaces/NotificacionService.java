package ar.edu.frc.utn.backend.notificaciones.service.interfaces;


import ar.edu.frc.utn.backend.notificaciones.entities.Notificacion;

public interface NotificacionService extends Servicio<Notificacion, Integer> {
    void createAll(Iterable<Notificacion> notificacion);
    String sendWhatsAppMessage(String to, String message, int legajoEmpleado);
}
