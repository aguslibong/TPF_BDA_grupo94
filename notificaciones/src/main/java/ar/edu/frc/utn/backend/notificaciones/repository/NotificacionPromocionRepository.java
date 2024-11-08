package ar.edu.frc.utn.backend.notificaciones.repository;

import ar.edu.frc.utn.backend.notificaciones.entities.NotificacionEmpleado;
import ar.edu.frc.utn.backend.notificaciones.entities.NotificacionPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionPromocionRepository extends JpaRepository<NotificacionPromocion, Integer> {
}
