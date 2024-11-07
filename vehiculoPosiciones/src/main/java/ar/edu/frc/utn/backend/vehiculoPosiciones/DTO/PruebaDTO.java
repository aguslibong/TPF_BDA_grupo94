package ar.edu.frc.utn.backend.vehiculoPosiciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PruebaDTO {
    private int idPrueba;
    private int idInteresado;
    private int idEmpleado;
    private int idVehiculo;
}
