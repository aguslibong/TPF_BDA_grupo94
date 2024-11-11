package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.InteresadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;

import java.util.List;
import java.util.Optional;

public interface InteresadoService extends Servicio<InteresadoDTO, Integer>{

    Optional<Interesado> findById(int id);

    List<InteresadoDTO> findAll();

    String restringir(int id);
}
