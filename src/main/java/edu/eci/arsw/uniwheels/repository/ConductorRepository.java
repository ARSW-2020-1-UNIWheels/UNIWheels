package edu.eci.arsw.uniwheels.repository;


import edu.eci.arsw.uniwheels.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Service
@Transactional
public interface ConductorRepository extends JpaRepository<Conductor,Integer> {
}
