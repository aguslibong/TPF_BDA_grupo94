package ar.edu.frc.utn.backend.vehiculosPosiciones.repository;

import ar.edu.frc.utn.backend.vehiculosPosiciones.entities.NotificacionPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionPromocionRepository extends JpaRepository<NotificacionPromocion, Integer> {
}
