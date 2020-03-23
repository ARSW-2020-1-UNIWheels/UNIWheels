package edu.eci.arsw.uniwheels.repository;

import edu.eci.arsw.uniwheels.model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasajeroRepository extends JpaRepository<Pasajero,Integer> {
}
