package ar.edu.frc.utn.backend.interesadosPruebas.DTO;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InteresadoDTO {
    private int Id_interesado;
    private boolean restringido;
    private String documento;
    private String nombre;
    private LocalDate fechaVencimientoLicencia;

}
