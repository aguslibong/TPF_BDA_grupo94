package ar.edu.frc.utn.backend.interesadosPruebas.service;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.EmpleadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.convert.EmpleadoToEmpleadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.EmpleadoRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.EmpleadoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    private EmpleadoToEmpleadoDTO converter;
    private EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository repository ) {
        this.empleadoRepository = repository;
        this.converter = new EmpleadoToEmpleadoDTO();
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
    public EmpleadoDTO findById(int id) {
        return empleadoRepository.findById(id)
                .map(converter::apply)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ID: " + id));
    }

    @Override
    public List<EmpleadoDTO> findAll() {
        return empleadoRepository.findAll().stream().map(converter)
                .collect(Collectors.toList());
    }



}
