package edu.eci.arsw.uniwheels.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import edu.eci.arsw.uniwheels.model.Conductor;
import edu.eci.arsw.uniwheels.model.Pasajero;
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
public class UniWheelsAPIController extends BaseController {
    @Autowired
    UniWheelsServices uws = null;

    @RequestMapping(value="/addPasajero/{conductor}/{pasajero}" ,method =  RequestMethod.POST)
    public ResponseEntity<?> addPasajero(@PathVariable ("conductor") Conductor conductor, @PathVariable ("pasajero") Pasajero pasajero){
        try {
            uws.addPasajero(conductor,pasajero);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UniWheelsPersistenceException e) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/getConducDispo", method =  RequestMethod.GET)
    public ResponseEntity<?> getConductoresDisponibles(){
        try {
            return new ResponseEntity<>(uws.getConductoresDisponibles(),HttpStatus.ACCEPTED);
        } catch (UniWheelsPersistenceException ex) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/addConducDispo", method =  RequestMethod.POST)
    public ResponseEntity<?> addConductorDisponible(@RequestBody Conductor conductor){
        try {
            uws.saveConductorDisponible(conductor);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UniWheelsPersistenceException ex) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping()
    public ResponseEntity<?> prueba(){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}