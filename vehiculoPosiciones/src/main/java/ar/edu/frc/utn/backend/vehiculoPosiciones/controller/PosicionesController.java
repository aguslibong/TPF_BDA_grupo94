package ar.edu.frc.utn.backend.vehiculoPosiciones.controller;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.PosicionDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.entities.Posicion;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.PosicionImp;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.ServicioImp;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.PosicionService;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Posicion> corroborarPosicion (@RequestBody PosicionDTO posicion) {
        try {
            Posicion posicionReturn = posicionService.corroborar(posicion);
            return ResponseEntity.ok(posicionReturn);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
