package ar.edu.frc.utn.backend.interesadosPruebas.service;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.EmpleadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.EmpleadoRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.InteresadoRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.EmpleadoService;

import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {
    private EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository repository ) {
        this.empleadoRepository = repository;
    }

    @Override
    public void create(EmpleadoDTO empleadoDTO) {

    }

    @Override
    public void update(EmpleadoDTO empleadoDTO) {

    }

    @Override
    public EmpleadoDTO delete(Integer id) {
        return null;
    }

    @Override
    public EmpleadoDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<EmpleadoDTO> findAll() {
        return List.of();
    }
}
