package ar.edu.frc.utn.backend.vehiculosPosiciones.repository;

import ar.edu.frc.utn.backend.vehiculosPosiciones.entities.Vehiculo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends CrudRepository<Vehiculo, Integer> {

}
