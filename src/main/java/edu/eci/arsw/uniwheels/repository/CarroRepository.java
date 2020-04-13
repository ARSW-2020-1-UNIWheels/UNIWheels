package edu.eci.arsw.uniwheels.repository;

import edu.eci.arsw.uniwheels.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Service
@Transactional
public interface CarroRepository extends JpaRepository<Carro,Integer> {
}
