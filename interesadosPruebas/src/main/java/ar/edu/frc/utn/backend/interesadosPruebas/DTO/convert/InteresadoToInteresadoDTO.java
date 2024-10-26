package ar.edu.frc.utn.backend.interesadosPruebas.DTO.convert;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.InteresadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;

import java.util.function.Function;

public class InteresadoToInteresadoDTO implements Function<Interesado, InteresadoDTO> {

    @Override
    public InteresadoDTO apply(Interesado interesado) {
        InteresadoDTO interesadoDTO = new InteresadoDTO();
        interesadoDTO.setId_interesado(interesado.getID());

        return interesadoDTO;
    }

}
