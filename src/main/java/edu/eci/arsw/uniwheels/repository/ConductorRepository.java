package edu.eci.arsw.uniwheels.repository;


import edu.eci.arsw.uniwheels.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Service
@Transactional
public interface ConductorRepository extends JpaRepository<Conductor,Integer> {
    @Modifying
    @Query("update Conductor u set u.nombreEstado = :nombreEstado where u.id = :id")
    void updateConductorDisponible(@Param("nombreEstado") String nombreEstado, @Param("id") int id);

    @Modifying
    @Query("update Conductor p set p.calificacion=:superCalificacion where p.id=:idConductor")
    void updateValoracion(@Param("superCalificacion") int superCalificacion,@Param("idConductor") int idConductor);
}
