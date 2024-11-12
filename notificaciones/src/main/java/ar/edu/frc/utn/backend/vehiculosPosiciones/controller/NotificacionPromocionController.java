package ar.edu.frc.utn.backend.vehiculosPosiciones.controller;

import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.NotificacionPromocionDTO;
import ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces.NotificacionPromocionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion/promocion")
public class NotificacionPromocionController {

    private final NotificacionPromocionService notificacionPromocionService;

    public NotificacionPromocionController(NotificacionPromocionService notificacionPromocionService) {
        this.notificacionPromocionService = notificacionPromocionService;
    }

    @PostMapping
    public ResponseEntity<String> promocionNotificacion(@RequestBody List<NotificacionPromocionDTO> notificacionPromocionDTO) {
        try {
            notificacionPromocionService.createAll(notificacionPromocionDTO);
            return ResponseEntity.ok("Se han enviado los mensajes con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar las notificaciones" + notificacionPromocionDTO.toString());
        }
    }
}
