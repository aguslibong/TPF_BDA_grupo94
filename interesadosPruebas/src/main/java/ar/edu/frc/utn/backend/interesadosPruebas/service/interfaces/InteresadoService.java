package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;

import java.util.Optional;

public interface InteresadoService {

    Optional<Interesado> findById(int id);
}
