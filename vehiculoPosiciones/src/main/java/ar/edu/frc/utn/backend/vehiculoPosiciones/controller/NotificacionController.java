package ar.edu.frc.utn.backend.vehiculoPosiciones.controller;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.NotificacionDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.NotificacionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    public ResponseEntity<NotificacionDTO> crearNotificacion(@RequestBody Iterable<NotificacionDTO> notificacion) {
        try {
            for (NotificacionDTO notificacionDTO : notificacion) {
                notificacionService.create(notificacionDTO);
                ResponseEntity.ok().body(notificacionDTO);
            }
        } catch (RuntimeException e) {
            ResponseEntity.badRequest().body(notificacion);
        }
        return null;
    }
}
