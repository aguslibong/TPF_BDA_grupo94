package ar.edu.frc.utn.backend.notificaciones.controller;

import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionEmpleadoDTO;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.NotificacionEmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion/empleado")
public class NotificacionEmpleadoController {

    private final NotificacionEmpleadoService notificacionEmpleadoService;

    public NotificacionEmpleadoController(NotificacionEmpleadoService notificacionEmpleadoService) {
        this.notificacionEmpleadoService = notificacionEmpleadoService;
    }

    @PostMapping
    public ResponseEntity<String> sendSMSMessage(@RequestBody NotificacionEmpleadoDTO notificacionEmpleadoDTO) {
        try {
            notificacionEmpleadoService.sendSmsMessage(notificacionEmpleadoDTO);
            return ResponseEntity.ok(notificacionEmpleadoDTO.toString());
        } catch (RuntimeException e) {
            // Devolver un ResponseEntity con código 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar el mensaje SMS");
        }
    }
}
