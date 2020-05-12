package edu.eci.arsw.uniwheels.repository;

import edu.eci.arsw.uniwheels.model.Conductor;
import edu.eci.arsw.uniwheels.model.Pasajero;
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
public interface PasajeroRepository extends JpaRepository<Pasajero,Integer> {
    @Modifying
    @Query("update Pasajero u set u.conductor = :conductor where u.id = :id")
    void updatePasajeroConductor(@Param("conductor") Conductor conductor, @Param("id") int id);

    @Modifying
    @Query("update Pasajero u set u.nombreEstado = :nombreEstado where u.id = :id")
    void updatePasajeroDisponible(@Param("nombreEstado") String nombreEstado,@Param("id") int id);

    @Modifying
    @Query(value = "DELETE FROM pasajeroxconductor AS pxc WHERE pxc.pasajero_id= ?1 AND pxc.conductor_id=?2", nativeQuery = true)
    void deletingPosiblesConductores(int idPasajero,int idConductor);


}
