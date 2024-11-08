package ar.edu.frc.utn.backend.interesadosPruebas.controller;


import ar.edu.frc.utn.backend.interesadosPruebas.DTO.EmpleadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.InteresadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Empleado;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.EmpleadoService;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.Servicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/{legajo_Empleado}")
    public ResponseEntity<EmpleadoDTO> obtenerUnEmpleado (@PathVariable int legajo_Empleado){
        try {
            EmpleadoDTO empleado = empleadoService.findById(legajo_Empleado);
            return ResponseEntity.ok(empleado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<EmpleadoDTO>> findAll (){
        try {
            Iterable<EmpleadoDTO> empleado = empleadoService.findAll();
            return ResponseEntity.ok(empleado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

///api/empleado/3