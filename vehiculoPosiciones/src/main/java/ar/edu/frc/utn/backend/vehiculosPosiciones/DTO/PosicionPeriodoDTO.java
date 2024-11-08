package ar.edu.frc.utn.backend.vehiculosPosiciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosicionPeriodoDTO {
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Integer idPrueba;
    private Integer idVehiculo;
}

