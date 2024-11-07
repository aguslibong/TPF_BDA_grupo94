package ar.edu.frc.utn.backend.interesadosPruebas.service;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.InteresadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.convert.InteresadoToInteresadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.InteresadoRepository;

import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.InteresadoService;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.Servicio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InteresadoServiceImp implements InteresadoService {
    private final InteresadoRepository repository;
    private final InteresadoToInteresadoDTO converter;


    public InteresadoServiceImp(InteresadoRepository repository) {
        this.repository = repository;
        this.converter = new InteresadoToInteresadoDTO();
    }
    

    @Override
    public void create(InteresadoDTO interesadoDTO) {

    }

    @Override
    public void update(InteresadoDTO interesadoDTO) {

    }

    @Override
    public InteresadoDTO delete(Integer id) {
        return null;
    }

    @Override
    public InteresadoDTO findById(Integer id) {
        return null;
    }

    @Override
    public Optional<Interesado> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<InteresadoDTO> findAll() {
        return repository.findAll().stream().map(converter)
                .collect(Collectors.toList());
    }

    @Override
    public String restringir(int id) {
        Optional<Interesado> optionalInteresado = repository.findById(id);
        if (optionalInteresado.isPresent()) {
            Interesado interesado = optionalInteresado.get();
            interesado.setRestringido(true);
            repository.save(interesado);
            return "El interesado se restringió";
        } else {
            return "No se encontró el interesado con el id: " + id;
        }}}

