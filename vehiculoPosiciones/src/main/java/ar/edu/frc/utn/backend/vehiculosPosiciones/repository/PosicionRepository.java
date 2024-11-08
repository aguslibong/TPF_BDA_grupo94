package ar.edu.frc.utn.backend.vehiculosPosiciones.repository;

import ar.edu.frc.utn.backend.vehiculosPosiciones.entities.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
    List<Posicion> registros = findByVehicleIdAndDateRange(int idVehiculo, LocalDateTime fechaDesde, LocalDateTime fechaHasta ;
}
