package ar.edu.frc.utn.backend.interesadosPruebas.repository;

import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteresadoRepository extends JpaRepository<Interesado, Integer> {

}

