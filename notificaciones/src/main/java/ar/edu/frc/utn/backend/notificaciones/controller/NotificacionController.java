package ar.edu.frc.utn.backend.notificaciones.controller;

import ar.edu.frc.utn.backend.notificaciones.entities.Notificacion;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<Notificacion> sendWhatsAppMessage(@RequestBody Notificacion notificacion) {
        try {
            Notificacion noti = notificacionService.sendWhatsAppMessage(notificacion);
            return ResponseEntity.ok(noti);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        };
    }
}
