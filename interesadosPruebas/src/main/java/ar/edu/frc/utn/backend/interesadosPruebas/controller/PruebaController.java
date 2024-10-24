package ar.edu.frc.utn.backend.interesadosPruebas.controller;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.PruebaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/prueba")
public class PruebaController {
    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @PostMapping
    public ResponseEntity<Prueba> crearPrueba(@RequestBody PruebaDTO pruebaDTO){
        try {
            Prueba prueba = pruebaService.crearPrueba(pruebaDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch DateTimeParseException
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }


}
