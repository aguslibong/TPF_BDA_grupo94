package ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.PosicionDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.entities.Posicion;
import org.springframework.stereotype.Service;

public interface PosicionService extends Servicio<PosicionDTO, Integer>{
    Posicion corroborar(PosicionDTO posicion);
}
