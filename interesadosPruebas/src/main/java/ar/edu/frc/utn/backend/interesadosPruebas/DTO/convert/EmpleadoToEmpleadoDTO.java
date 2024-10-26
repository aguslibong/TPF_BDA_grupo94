package ar.edu.frc.utn.backend.interesadosPruebas.DTO.convert;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.EmpleadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.InteresadoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Empleado;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;

import java.util.function.Function;

public class EmpleadoToEmpleadoDTO implements Function<Empleado, EmpleadoDTO> {
    @Override
    public EmpleadoDTO apply(Empleado empleado) {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setLegajo(empleado.getLEGAJO());
        empleadoDTO.setTelefonoContacto(empleado.getTelefonoContacto());

        return empleadoDTO;
    }
}
