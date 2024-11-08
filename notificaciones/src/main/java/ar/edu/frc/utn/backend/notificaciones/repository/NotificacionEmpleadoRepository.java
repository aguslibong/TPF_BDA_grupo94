package ar.edu.frc.utn.backend.notificaciones.repository;

import ar.edu.frc.utn.backend.notificaciones.entities.NotificacionEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionEmpleadoRepository extends JpaRepository<NotificacionEmpleado, Integer> {
}
