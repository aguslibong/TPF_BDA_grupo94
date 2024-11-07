package ar.edu.frc.utn.backend.vehiculoPosiciones.service;

import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.Servicio;
import org.springframework.stereotype.Service;

@Service
public abstract class ServicioImp<T, K> implements Servicio<T, K> {
}
