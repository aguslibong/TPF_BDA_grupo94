package ar.edu.frc.utn.backend.interesadosPruebas.controller;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.InteresadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.InteresadoService;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.PruebaService;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.Servicio;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interesado")
public class InteresadoController {


    private final InteresadoService interesadoService;

    public InteresadoController (InteresadoService interesadoService) {
        this.interesadoService = interesadoService;
    }


    @GetMapping
    public ResponseEntity<Iterable<InteresadoDTO>> getInteresados() {
        try {
            Iterable<InteresadoDTO> lista = interesadoService.findAll();
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping ("/restringir/{id}")
    public ResponseEntity<String> restringir(@PathVariable int id){
        try {
            String mensaje = interesadoService.restringir(id);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar restringir el Interesado");
        }
    }

}
