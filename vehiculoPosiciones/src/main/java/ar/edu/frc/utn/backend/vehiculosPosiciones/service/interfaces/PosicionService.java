package ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces;

import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.PosicionDTO;
import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.PosicionPeriodoDTO;
import org.springframework.http.ResponseEntity;

public interface PosicionService extends Servicio<PosicionDTO, Integer>{
    ResponseEntity<String> corroborar(PosicionDTO posicion);
    double getPosicionesEnPeriodo(PosicionPeriodoDTO pruebaPossicion);
}
