package edu.eci.arsw.uniwheels.services;

import edu.eci.arsw.uniwheels.model.Conductor;
import edu.eci.arsw.uniwheels.model.Pasajero;
import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniWheelsServices {

    @Autowired
    UniWheelsPersistence uwp = null;

    public void addNewUser(Usuario usr) throws UniWheelsPersistenceException{
        uwp.saveUser(usr);
    }


    public List<Usuario> getAllUsers() throws UniWheelsPersistenceException{
        return uwp.getAllUsers();
    }

    public void addPasajero(Conductor conductor, Pasajero pasajero) throws UniWheelsPersistenceException{
        uwp.savePasajeros(conductor,pasajero);
    }

    public List<Conductor> getConductoresDisponibles() throws UniWheelsPersistenceException {
        return uwp.getConductoresDisponibles();
    }

    public void saveConductorDisponible(Conductor conductor) throws UniWheelsPersistenceException{
        uwp.saveConductorDisponible(conductor);
    }

}