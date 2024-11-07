package ar.edu.frc.utn.backend.notificaciones.repository;

import ar.edu.frc.utn.backend.notificaciones.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}
