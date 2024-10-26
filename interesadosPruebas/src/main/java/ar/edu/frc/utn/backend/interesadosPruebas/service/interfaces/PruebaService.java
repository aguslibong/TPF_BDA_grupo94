package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PruebaService extends Servicio<PruebaDTO,Integer> {

    void create(PruebaDTO prueba) throws Exception ;

    Iterable<PruebaDTO> obtenerListaPruebasMomento() throws Exception;

    ResponseEntity finalizarPrueba(int id, String comentario) throws Exception;

    List<PruebaDTO> findAll() throws Exception;
}
