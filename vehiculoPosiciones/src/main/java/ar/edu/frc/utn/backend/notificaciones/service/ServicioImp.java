package ar.edu.frc.utn.backend.notificaciones.service;

import ar.edu.frc.utn.backend.notificaciones.service.interfaces.Servicio;
import org.springframework.stereotype.Service;

@Service
public abstract class ServicioImp<T, K> implements Servicio<T, K> {
}
