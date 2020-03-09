package edu.eci.arsw.uniwheels.persistence;

import edu.eci.arsw.uniwheels.model.Usuario;
import java.util.List;

/**
 *
 * @author Carlos Murillo
 */
public interface UniWheelsPersistence {
    public void saveUser(Usuario usr) throws UniWheelsPersistenceException;
    public Usuario getUser(String name) throws UniWheelsPersistenceException;
    public List<Usuario> getAllUsers() throws UniWheelsPersistenceException;
}