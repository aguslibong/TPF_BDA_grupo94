package ar.edu.frc.utn.backend.interesadosPruebas.controller;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.PruebaService;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

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
            pruebaService.create(pruebaDTO);
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
    public ResponseEntity<List<PruebaDTO>> mostrarPruebas (){
        try{
            List<PruebaDTO> listaPruebas = pruebaService.findAll();
            return ResponseEntity.ok(listaPruebas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping ("/incidente/{id}")
    public ResponseEntity<String> incidente(@PathVariable int id){
        try {
            String mensaje = pruebaService.cambiarIncidente(id);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al Modificar Prueba");
        }
    }

    @GetMapping ("/incidente/reporte")
    public ResponseEntity<Iterable<PruebaDTO>> incidenteReporte(){
        try {
            Iterable<PruebaDTO> mensaje = pruebaService.incidenteReporte();
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}