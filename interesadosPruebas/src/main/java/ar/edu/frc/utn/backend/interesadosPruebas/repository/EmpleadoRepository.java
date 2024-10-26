package ar.edu.frc.utn.backend.interesadosPruebas.repository;


import ar.edu.frc.utn.backend.interesadosPruebas.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
