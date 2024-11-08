package ar.edu.frc.utn.backend.notificaciones.DTO;

import ar.edu.frc.utn.backend.notificaciones.entities.Modelo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoDTO {

    private int idVehiculo;
    private String patente;
    private String modelo;
    private String marca;

}
