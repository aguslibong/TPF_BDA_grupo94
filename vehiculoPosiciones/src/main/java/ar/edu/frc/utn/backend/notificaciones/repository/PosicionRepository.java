package ar.edu.frc.utn.backend.notificaciones.repository;

import ar.edu.frc.utn.backend.notificaciones.entities.Posicion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosicionRepository extends CrudRepository<Posicion, Integer> {
}
