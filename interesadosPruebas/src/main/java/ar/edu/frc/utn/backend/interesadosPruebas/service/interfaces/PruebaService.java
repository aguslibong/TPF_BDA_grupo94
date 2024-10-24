package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;

public interface PruebaService {

    void crearPrueba(PruebaDTO prueba) throws Exception;

    Iterable<PruebaDTO> obtenerListaPruebasMomento() throws Exception;

    PruebaDTO finalizarPrueba(int id, String comentario) throws Exception;
}
