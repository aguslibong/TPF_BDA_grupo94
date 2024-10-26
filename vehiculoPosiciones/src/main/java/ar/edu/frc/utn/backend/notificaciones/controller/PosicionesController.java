package ar.edu.frc.utn.backend.notificaciones.controller;

import ar.edu.frc.utn.backend.notificaciones.DTO.PosicionDTO;
import ar.edu.frc.utn.backend.notificaciones.entities.Posicion;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.PosicionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posiciones")
public class PosicionesController {

        private final PosicionService posicionService;
        
        public PosicionesController(PosicionService posicionService) {
            this.posicionService = posicionService;
        }


    @PostMapping("/corroborar")
    public ResponseEntity<String> corroborarPosicion (@RequestBody PosicionDTO posicion) {
        try {
            String posicionReturn = posicionService.corroborar(posicion);
            return ResponseEntity.ok(posicionReturn);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
