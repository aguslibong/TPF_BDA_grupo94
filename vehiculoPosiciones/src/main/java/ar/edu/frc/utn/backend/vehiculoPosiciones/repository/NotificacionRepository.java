package ar.edu.frc.utn.backend.vehiculoPosiciones.repository;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.NotificacionDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.entities.Notificacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends CrudRepository<Notificacion, Integer> {
}
