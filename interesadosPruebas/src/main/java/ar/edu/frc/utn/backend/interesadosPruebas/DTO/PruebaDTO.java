package ar.edu.frc.utn.backend.interesadosPruebas.DTO;

import lombok.Data;

@Data
public class PruebaDTO {
    private int idPrueba;
    private int idInteresado;
    private int idEmpleado;
    private int idVehiculo;
    private boolean incidente;
}
