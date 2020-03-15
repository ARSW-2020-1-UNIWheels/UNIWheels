package edu.eci.arsw.uniwheels.services;

import edu.eci.arsw.uniwheels.model.DetallesUsuario;
import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices implements UserDetailsService {
    @Autowired
    UniWheelsPersistence uwp = null;


    public void addUser(Usuario usuario) throws UniWheelsPersistenceException {
        uwp.saveUser(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = null;
        try {
            usuario = uwp.getUser(username);
        } catch (UniWheelsPersistenceException e) {
            e.printStackTrace();
        }


        return new DetallesUsuario(usuario);
    }


}
