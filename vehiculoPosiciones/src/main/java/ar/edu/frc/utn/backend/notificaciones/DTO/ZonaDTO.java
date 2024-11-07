package ar.edu.frc.utn.backend.notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZonaDTO {

//Latitud/Longitud de la agencia
//b. Radio máximo
//c. Listado de zonas peligrosas

    private float latitud;
    private float longitud;
    private float radio;

    private List<ZonaDTO> listaZonaPeligrosas = new ArrayList<>();
}