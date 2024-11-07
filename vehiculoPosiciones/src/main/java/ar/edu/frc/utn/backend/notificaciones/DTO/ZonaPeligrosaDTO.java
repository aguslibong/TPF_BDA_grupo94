package ar.edu.frc.utn.backend.notificaciones.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonaPeligrosaDTO {
    private CoordenadaDTO noroeste;
    private CoordenadaDTO sureste;
}
