package ar.edu.frc.utn.backend.vehiculosPosiciones.controller;

import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.PosicionDTO;
import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.PruebaPosicionPeriodoDTO;
import ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces.PosicionService;
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
            return posicionService.corroborar(posicion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/periodo"){
        public ResponseEntity<Iterable<PosicionDTO>> getPosicionesEnPeriodo(@RequestBody PruebaPosicionPeriodoDTO pruebaPosicion) {
            try {
                return posicionService.getPosicionesEnPeriodo(pruebaPosicion);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
