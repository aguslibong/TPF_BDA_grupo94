package ar.edu.frc.utn.backend.notificaciones.controller;

import ar.edu.frc.utn.backend.notificaciones.DTO.VehiculoDTO;
import ar.edu.frc.utn.backend.notificaciones.service.VehiculoImp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoController {

    private final VehiculoImp vehiculoImp;

    public VehiculoController(VehiculoImp vehiculoImp) {
        this.vehiculoImp = vehiculoImp;
    }

    @GetMapping("/{id}")
    public VehiculoDTO getByIdVehiculo(@PathVariable int id) {
        try {
            return vehiculoImp.findById(id);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehículo no encontrado");
        }
    }


}
