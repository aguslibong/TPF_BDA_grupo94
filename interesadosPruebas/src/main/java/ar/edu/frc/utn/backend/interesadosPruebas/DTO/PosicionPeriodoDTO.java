package ar.edu.frc.utn.backend.interesadosPruebas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosicionPeriodoDTO {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int idPrueba;
    private int idVehiculo;

}