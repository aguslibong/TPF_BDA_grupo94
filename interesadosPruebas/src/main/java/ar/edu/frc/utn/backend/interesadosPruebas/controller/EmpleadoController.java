package ar.edu.frc.utn.backend.interesadosPruebas.controller;


import ar.edu.frc.utn.backend.interesadosPruebas.DTO.EmpleadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.EmpleadoService;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.Servicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        
        this.empleadoService = empleadoService;
    }

    //@GetMapping
    //public

}

///api/empleado/3