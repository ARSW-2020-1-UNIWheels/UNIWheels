package edu.eci.arsw.uniwheels.persistence.imp;

import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InMemoryUniWheelsPersistence implements UniWheelsPersistence {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(Usuario usuario) throws UniWheelsPersistenceException {
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        userRepository.save(usuario);
    }

    @Override
    public Usuario getUser(String name) throws UsernameNotFoundException {
        List<Usuario> usuarios = userRepository.findAll();
        Usuario usuario = null;
        for (Usuario user:usuarios){
            if(user.username.equals(name)){
                usuario = user;
            }
        }
        if (usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }

    @Override
    public List<Usuario> getAllUsers() throws UniWheelsPersistenceException {
        throw new UniWheelsPersistenceException("En proceso de construcción");
    }
}