package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import org.springframework.stereotype.Service;

public interface PruebaService {

    Prueba crearPrueba(PruebaDTO prueba) throws Exception;
}
