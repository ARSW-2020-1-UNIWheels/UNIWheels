package edu.eci.arsw.uniwheels.services;

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
}