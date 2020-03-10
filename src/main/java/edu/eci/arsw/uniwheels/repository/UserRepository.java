package edu.eci.arsw.uniwheels.repository;

import edu.eci.arsw.uniwheels.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public interface UserRepository extends JpaRepository<Usuario,Integer> {
}
