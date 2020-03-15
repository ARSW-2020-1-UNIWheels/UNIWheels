package edu.eci.arsw.uniwheels.controllers;

import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.repository.UserRepository;
import edu.eci.arsw.uniwheels.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class UniWheelsApiAuthController extends BaseController{

    @Autowired
    private AuthServices authServices = null;


    @RequestMapping (value = "/getUser", method = RequestMethod.POST)
    public ResponseEntity<?> getUser(@RequestBody Usuario usuario){

        UserDetails user = authServices.loadUserByUsername(usuario.username);
        return new ResponseEntity<>(user,HttpStatus.OK);


    }

    @RequestMapping (path = "/addUser",method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody Usuario usuario){
        try{
            authServices.addUser(usuario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UniWheelsPersistenceException uwp){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
