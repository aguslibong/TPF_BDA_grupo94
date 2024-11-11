package ar.edu.frc.utn.backend.vehiculosPosiciones.service;

import ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces.Servicio;
import org.springframework.stereotype.Service;

@Service
public abstract class ServicioImp<T, K> implements Servicio<T, K> {
}
