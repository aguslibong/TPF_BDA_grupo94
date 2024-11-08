package ar.edu.frc.utn.backend.notificaciones.DTO.convert;

import ar.edu.frc.utn.backend.notificaciones.DTO.VehiculoDTO;
import ar.edu.frc.utn.backend.notificaciones.entities.Vehiculo;

import java.util.function.Function;

public class VehiculoToVehiculoDTO implements Function<Vehiculo, VehiculoDTO> {
    @Override
    public VehiculoDTO apply(Vehiculo vehiculo) {
        VehiculoDTO vehiculoDTO = new VehiculoDTO();
        vehiculoDTO.setIdVehiculo(vehiculo.getId());
        vehiculoDTO.setPatente(vehiculo.getPatente());
        vehiculoDTO.setModelo(vehiculo.getModelo().getDescripcion());
        vehiculoDTO.setMarca(vehiculo.getModelo().getIdMarca().getNombre());

        return vehiculoDTO;
    }
}
