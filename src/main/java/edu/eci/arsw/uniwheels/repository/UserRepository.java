package edu.eci.arsw.uniwheels.repository;

import edu.eci.arsw.uniwheels.model.Usuario;
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
public interface UserRepository extends JpaRepository<Usuario,Integer> {
    @Modifying
    @Query("update Usuario u set u.ubicacion = :ubicacion where u.username = :username")
    void agregarUbicacionUsuario(@Param("username") String username, @Param("ubicacion") String ubicacion);
}
