package ar.edu.frc.utn.backend.notificaciones.controller;

import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionDTO;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService, NotificacionService notificacionService1) {
        this.notificacionService = notificacionService1;
    }

    @PostMapping
    public ResponseEntity<String> sendSMSMessage(@RequestBody NotificacionDTO notificacionDTO) {
        try {
            System.out.println(notificacionDTO);
            notificacionService.sendSmsMessage(notificacionDTO);
            return ResponseEntity.ok("Se almaceno la Notificacion: " + notificacionDTO.getMensaje());
        } catch (RuntimeException e) {
            // Devolver un ResponseEntity con código 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar el mensaje SMS");
        }
    }
}
