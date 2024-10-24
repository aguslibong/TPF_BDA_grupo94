package ar.edu.frc.utn.backend.vehiculoPosiciones.service;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.VehiculoDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.entities.Vehiculo;
import ar.edu.frc.utn.backend.vehiculoPosiciones.repository.VehiculoRepository;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.Servicio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoImp implements Servicio<VehiculoDTO, Integer> {

    private final VehiculoRepository vehiculoRepository;
    public VehiculoImp(VehiculoRepository vehiculoRepository) {
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

            // Convertir la entidad Vehiculo a VehiculoDTO

            // Retornar el DTO
            return new VehiculoDTO(vehiculo.getId());
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
