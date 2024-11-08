package ar.edu.frc.utn.backend.interesadosPruebas.DTO;

import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PruebaDetalladaDTO {
    private Prueba prueba;
    private VehiculoDTO vehiculo;
}
