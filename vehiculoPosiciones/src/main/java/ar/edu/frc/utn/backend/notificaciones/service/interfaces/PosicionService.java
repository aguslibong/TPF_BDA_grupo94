package ar.edu.frc.utn.backend.notificaciones.service.interfaces;

import ar.edu.frc.utn.backend.notificaciones.DTO.PosicionDTO;
import ar.edu.frc.utn.backend.notificaciones.entities.Posicion;

public interface PosicionService extends Servicio<PosicionDTO, Integer>{
    Posicion corroborar(PosicionDTO posicion);
}
