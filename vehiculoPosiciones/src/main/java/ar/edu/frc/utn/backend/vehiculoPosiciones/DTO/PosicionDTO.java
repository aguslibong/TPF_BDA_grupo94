package ar.edu.frc.utn.backend.vehiculoPosiciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosicionDTO {

    private int id;
    private int id_vehiculo;
    //es la hipotenusa a la que el le dice radio y es el calculo que vamos a hacer con la latitud y longitud que tiene la posicion
    private int latitud;
    private int longitud;

}