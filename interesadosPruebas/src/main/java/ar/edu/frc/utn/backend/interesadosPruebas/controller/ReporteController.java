package ar.edu.frc.utn.backend.interesadosPruebas.controller;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PosicionPeriodoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDetalladaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.PruebaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/reporte")
public class ReporteController {

    private final PruebaService pruebaService;

    public ReporteController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }


    @GetMapping ("/incidente")
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

    @GetMapping("/incidente/empleado/{id}")
    public ResponseEntity<Iterable<PruebaDetalladaDTO>> incidenteReporteEmpleado(@PathVariable int id){
        try {
            Iterable<PruebaDetalladaDTO> mensaje = pruebaService.incidentesPorEmpleado(id);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/vehiculo/{idVehiculo}")
    public ResponseEntity<Iterable<PruebaDetalladaDTO>> reporteVehiculo(@PathVariable int idVehiculo){
        try {
            Iterable<PruebaDetalladaDTO> mensaje = pruebaService.reporteVehiculo(idVehiculo);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/kilometro")
    public ResponseEntity<String> reporteKilometro(@RequestBody PosicionPeriodoDTO posicionPeriodoDTO){
        try{
            Double kilometros = pruebaService.calcularKilometros(posicionPeriodoDTO);

            return ResponseEntity.ok("Los kilometros recorridos son: " + kilometros);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error-Message", e.getMessage()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}