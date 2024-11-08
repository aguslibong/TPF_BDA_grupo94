package ar.edu.frc.utn.backend.notificaciones.service;

import ar.edu.frc.utn.backend.notificaciones.DTO.VehiculoDTO;
import ar.edu.frc.utn.backend.notificaciones.DTO.convert.VehiculoToVehiculoDTO;
import ar.edu.frc.utn.backend.notificaciones.entities.Vehiculo;
import ar.edu.frc.utn.backend.notificaciones.repository.VehiculoRepository;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.VehiculoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoImp extends ServicioImp<VehiculoDTO, Integer> implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private VehiculoToVehiculoDTO convert;

    public VehiculoImp(VehiculoRepository vehiculoRepository) {
        this.convert = new VehiculoToVehiculoDTO();
        this.vehiculoRepository = vehiculoRepository;
    }


    @Override
    public void create(VehiculoDTO vehiculoDTO) {

    }

    @Override
    public void update(VehiculoDTO vehiculoDTO) {

    }

    @Override
    public VehiculoDTO delete(Integer id) {
        return null;
    }

    @Override
    public boolean existById(Integer id) {
        return this.vehiculoRepository.existsById(id);
    }
    @Override
    public VehiculoDTO findById(Integer id) {
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findById(id);

        // Verificar si el vehículo existe
        if (!optionalVehiculo.isEmpty()) {
            // Obtener el objeto Vehiculo del Optional
            Vehiculo vehiculo = optionalVehiculo.get();

            VehiculoDTO vehiculoDTO = convert.apply(vehiculo);

            return vehiculoDTO;
        } else {
            // Lanzar excepción si el vehículo no existe
            throw new IllegalArgumentException("El vehículo con ID " + id + " no existe");
        }
    }


    @Override
    public List<VehiculoDTO> findAll() {
        return List.of();
    }
}
