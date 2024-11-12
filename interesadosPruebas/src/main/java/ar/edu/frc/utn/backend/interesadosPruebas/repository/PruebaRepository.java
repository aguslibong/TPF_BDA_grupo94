package ar.edu.frc.utn.backend.interesadosPruebas.repository;

import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import java.util.List; // Importar la clase List

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Integer> {

    //@Query("SELECT p FROM Prueba p WHERE p.idVehiculo = :idVehiculo")
    //List<Prueba> findAllByIdVehiculo(@Param("idVehiculo") Integer idVehiculo);

    List<Prueba> findAllByIdVehiculoEquals(Integer idVehiculo);


    @Query("SELECT p FROM Prueba p WHERE p.fechaHoraFin = p.fechaHoraInicio")
    List<Prueba> findAllByFechaHoraFinNull();

    List<Prueba>  findAllByIncidente(boolean incidente);


}
