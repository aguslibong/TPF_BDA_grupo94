package ar.edu.frc.utn.backend.interesadosPruebas.service;

import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.InteresadoRepository;

import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.InteresadoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InteresadoServiceImp implements InteresadoService {
    private final InteresadoRepository repository;

    public InteresadoServiceImp(InteresadoRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Optional<Interesado> findById(int id){
        return repository.findById(id);
    }
}
