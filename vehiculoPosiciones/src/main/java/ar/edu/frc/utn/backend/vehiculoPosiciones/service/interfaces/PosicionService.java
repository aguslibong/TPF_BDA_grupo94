package ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.PosicionDTO;
import org.springframework.http.ResponseEntity;

public interface PosicionService extends Servicio<PosicionDTO, Integer>{
    ResponseEntity<String> corroborar(PosicionDTO posicion);
}
