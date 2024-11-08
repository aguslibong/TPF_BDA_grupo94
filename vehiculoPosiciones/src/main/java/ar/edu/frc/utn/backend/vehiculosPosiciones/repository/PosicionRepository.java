package ar.edu.frc.utn.backend.vehiculosPosiciones.repository;

import ar.edu.frc.utn.backend.vehiculosPosiciones.entities.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Integer> {

    @Query("SELECT p FROM Posicion p WHERE p.idVehiculo = :idVehiculo AND p.fechaHora BETWEEN :fechaDesde AND :fechaHasta ORDER BY p.fechaHora ASC")
    List<Posicion> findByVehicleIdAndDateRange(
            @Param("idVehiculo") int idVehiculo,
            @Param("fechaDesde") LocalDateTime fechaDesde,
            @Param("fechaHasta") LocalDateTime fechaHasta
    );

}
