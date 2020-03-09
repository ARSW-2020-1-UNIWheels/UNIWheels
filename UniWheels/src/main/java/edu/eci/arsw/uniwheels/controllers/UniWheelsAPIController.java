package edu.eci.arsw.uniwheels.controllers;

import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.services.UniWheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/uniwheels")
public class UniWheelsAPIController {
    @Autowired
    UniWheelsServices uws = null;

    @RequestMapping(path ="/{name}" , method = RequestMethod.GET)
    public ResponseEntity<?> GetUser(@PathVariable("name") String name){
        try{
            return new ResponseEntity<>(uws.getUser(name), HttpStatus.ACCEPTED);
        } catch (UniWheelsPersistenceException e) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> GetAllUsers(){
        try{
            return new ResponseEntity<>(uws.getAllUsers(), HttpStatus.ACCEPTED);
        } catch (UniWheelsPersistenceException e) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> AddNewUser(@RequestBody Usuario newUs){
        try{
            uws.addNewUser(newUs);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UniWheelsPersistenceException e) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }
}
