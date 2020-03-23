package edu.eci.arsw.uniwheels.persistence;

import edu.eci.arsw.uniwheels.model.Conductor;
import edu.eci.arsw.uniwheels.model.Pasajero;
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
    public void savePasajeros( Pasajero pasajero) throws UniWheelsPersistenceException;
    public List<Conductor> getConductoresDisponibles() throws UniWheelsPersistenceException;
    public void saveConductorDisponible(Conductor conductor) throws UniWheelsPersistenceException;
    public void updateDatabase();
    public void agregarPasajeroALaRuta(Pasajero pasajero, Conductor conductor);
}