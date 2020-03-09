package edu.eci.arsw.uniwheels.persistence.imp;

import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InMemoryUniWheelsPersistence implements UniWheelsPersistence {

    @Override
    public void saveUser(Usuario usr) throws UniWheelsPersistenceException {
        throw new UniWheelsPersistenceException("En proceso de construcción");
    }

    @Override
    public Usuario getUser(String name) throws UniWheelsPersistenceException{
        return new Usuario("Carlos","1","carlos.murillo-i");
    }

    @Override
    public List<Usuario> getAllUsers() throws UniWheelsPersistenceException {
        throw new UniWheelsPersistenceException("En proceso de construcción");
    }
}
