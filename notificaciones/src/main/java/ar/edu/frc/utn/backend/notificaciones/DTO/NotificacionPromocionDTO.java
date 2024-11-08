package ar.edu.frc.utn.backend.notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionPromocionDTO {

    private String telefono;
    private String mensaje;

}
