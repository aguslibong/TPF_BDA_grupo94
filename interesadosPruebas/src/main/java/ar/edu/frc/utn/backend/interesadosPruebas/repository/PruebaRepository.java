package ar.edu.frc.utn.backend.interesadosPruebas.repository;

import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import java.util.List; // Importar la clase List
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepository extends CrudRepository<Prueba, Integer> {

    @Query("SELECT p FROM Prueba p WHERE p.idVehiculo = :idVehiculo")
    List<Prueba> findAllByIdVehiculo(@Param("idVehiculo") Integer idVehiculo);

    @Query("SELECT p FROM Prueba p WHERE p.fechaHoraFin = null")
    List<Prueba> findAllByFechaHoraFinNull();

}
