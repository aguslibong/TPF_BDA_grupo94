package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.EmpleadoDTO;


import java.util.List;

public interface EmpleadoService extends Servicio<EmpleadoDTO, Integer>{

    EmpleadoDTO findById(int id);

    List<EmpleadoDTO> findAll();

}
