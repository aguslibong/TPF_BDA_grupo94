package ar.edu.frc.utn.backend.interesadosPruebas.DTO;

import lombok.Data;

@Data
public class PruebaDTO {
    private int idInteresado;
    private int idEmpleado;
    private int idVehiculo;
    private String comentario;
}
