package ar.edu.frc.utn.backend.interesadosPruebas.controller;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.InteresadoService;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.PruebaService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interesado")
public class InteresadoController {
    private final InteresadoService interesadoService;

    public InteresadoController(InteresadoService service) {
        this.interesadoService = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<Interesado>> getInteresados() {
        try {
            Iterable<Interesado> lista = interesadoService.findAll();
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
