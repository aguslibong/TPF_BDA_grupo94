package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import org.springframework.http.ResponseEntity;

public interface PruebaService {

    void crearPrueba(PruebaDTO prueba) throws Exception;

    Iterable<PruebaDTO> obtenerListaPruebasMomento() throws Exception;

    ResponseEntity finalizarPrueba(int id, String comentario) throws Exception;

    Iterable<Prueba> findALL() throws Exception;
}
