package ar.edu.frc.utn.backend.notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgenciaInfoDTO {

//Latitud/Longitud de la agencia
//b. Radio máximo
//c. Listado de zonas peligrosas

    private CoordenadaDTO coordenadaAgencia;
    private float radio;

    private List<ZonaPeligrosaDTO> listaZonaPeligrosas = new ArrayList<>();
}