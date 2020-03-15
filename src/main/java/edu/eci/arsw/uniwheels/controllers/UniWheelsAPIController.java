package edu.eci.arsw.uniwheels.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
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


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> prueba(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}