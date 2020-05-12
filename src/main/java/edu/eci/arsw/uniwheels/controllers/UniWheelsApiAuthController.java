package edu.eci.arsw.uniwheels.controllers;

import edu.eci.arsw.uniwheels.model.Universidad;
import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/auth")
public class UniWheelsApiAuthController extends BaseController{

    @Autowired
    private AuthServices authServices = null;


    @RequestMapping (value = "/getUser", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(){
        if (getLoggedUser()==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuario user = getLoggedUser().usuario;
        return new ResponseEntity<>(user,HttpStatus.OK);


    }
    @RequestMapping(value="/getAllUniversitys",method = RequestMethod.GET)
    public ResponseEntity<?> getAllUniversitys(){
        try{
            List<Universidad> lista = authServices.getAllUniversity();
            return new ResponseEntity<>(lista,HttpStatus.OK);
        } catch (Exception ex){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
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
