package edu.eci.arsw.uniwheels.repository;

import edu.eci.arsw.uniwheels.model.Calificacion;
import edu.eci.arsw.uniwheels.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion,Integer> {
    @Query("SELECT c FROM Calificacion as c WHERE c.conductor.conductorName=:nameConductor")
    List<Calificacion> traerCalificacionesDeConductor(@Param("nameConductor")String nameConductor);
}
