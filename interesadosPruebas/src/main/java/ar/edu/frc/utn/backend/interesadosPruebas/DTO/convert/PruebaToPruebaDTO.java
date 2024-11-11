package ar.edu.frc.utn.backend.interesadosPruebas.DTO.convert;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import java.util.function.Function;

public class PruebaToPruebaDTO implements Function<Prueba, PruebaDTO> {
    @Override
    public PruebaDTO apply(Prueba prueba) {
        PruebaDTO pruebaDTO = new PruebaDTO();
        pruebaDTO.setIdPrueba(prueba.getId());
        pruebaDTO.setIdInteresado(prueba.getInteresado().getID());
        pruebaDTO.setIdEmpleado(prueba.getEmpleado().getLEGAJO());
        pruebaDTO.setIdVehiculo(prueba.getIdVehiculo());
        pruebaDTO.setIncidente(prueba.isIncidente());
        return pruebaDTO;
    }
}
