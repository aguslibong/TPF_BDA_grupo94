package ar.edu.frc.utn.backend.notificaciones.controller;

import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionDTO;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService, NotificacionService notificacionService1) {
        this.notificacionService = notificacionService1;
    }

    @PostMapping
    public String sendSMSMessage(@RequestBody NotificacionDTO notificacionDTO) {
        try {
            return notificacionService.sendSmsMessage(notificacionDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
