package ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces;

import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.PosicionDTO;
import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.PruebaPosicionPeriodoDTO;
import org.springframework.http.ResponseEntity;

public interface PosicionService extends Servicio<PosicionDTO, Integer>{
    ResponseEntity<String> corroborar(PosicionDTO posicion);
    Iterable<PosicionDTO> getPosicionesEnPeriodo(PruebaPosicionPeriodoDTO pruebaPossicion);
}
