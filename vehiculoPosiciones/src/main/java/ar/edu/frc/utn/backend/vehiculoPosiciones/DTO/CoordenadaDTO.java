package ar.edu.frc.utn.backend.vehiculoPosiciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordenadaDTO {
    private double lat;
    private double lon;
}
