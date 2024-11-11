package ar.edu.frc.utn.backend.vehiculosPosiciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionEmpleadoDTO {
    private int legajoEmpleado;
    private String telefonoEmpleado;
    private String mensaje;
}
