package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.EmpleadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Empleado;


import java.util.Optional;

public interface EmpleadoService extends Servicio<EmpleadoDTO, Integer>{

    Optional<Empleado> findById(int id);



}
