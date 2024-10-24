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
    public ResponseEntity<Prueba> crearPrueba(@RequestBody PruebaDTO pruebaDTO) {
        try {
            pruebaService.crearPrueba(pruebaDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/momento")
    public ResponseEntity<Iterable<PruebaDTO>> getPruebasEnCurso() {
        try {
            Iterable<PruebaDTO> lista = pruebaService.obtenerListaPruebasMomento();
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity putPruebaFinalizar(@PathVariable int id, @RequestBody String comentario){
        try{
            return pruebaService.finalizarPrueba(id,comentario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Prueba>> mostrarPruebas (){
        try{
            Iterable<Prueba> listaPruebas = pruebaService.findALL();
            return ResponseEntity.ok(listaPruebas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}