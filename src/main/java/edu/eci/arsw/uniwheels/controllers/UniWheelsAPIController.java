package edu.eci.arsw.uniwheels.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.uniwheels.model.Conductor;
import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.services.AuthServices;
import edu.eci.arsw.uniwheels.services.UniWheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/uniwheels")
public class UniWheelsAPIController extends BaseController {
    @Autowired
    UniWheelsServices uws = null;

    @Autowired
    AuthServices authServices;


    @RequestMapping(value="/getConducDispo", method =  RequestMethod.GET)
    public ResponseEntity<?> getConductoresDisponibles(){
        try {




            List<Conductor> lista = uws.getConductoresDisponibles();
            return new ResponseEntity<>(lista,HttpStatus.ACCEPTED);
        } catch (UniWheelsPersistenceException ex) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/addConducDispo", method =  RequestMethod.POST)
    public ResponseEntity<?> addConductorDisponible(){
        try {
            Conductor conductor = new Conductor();
            conductor.setUsuario(getLoggedUser().getUsuario());
            conductor.tiempoRecorrido = 50000;
            conductor.nombreEstado = "Disponible";
            conductor.conductorName = getLoggedUser().getUsuario().username;
            getLoggedUser().getUsuario().viajesRealizados.add(conductor);
            System.out.println(getLoggedUser().getUsuario().viajesRealizados.size());
            uws.saveConductorDisponible(conductor);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UniWheelsPersistenceException ex) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/addPassanger/{conductor}", method=RequestMethod.POST)
    public ResponseEntity<?> añadirPasajeroALaRuta(@PathVariable String conductor){
        try {
            Usuario pasajeroUser = getLoggedUser().getUsuario();
            System.out.println(pasajeroUser.username+" SuperPasajero");
            System.out.println(conductor+" SuperConductor");
            Usuario conductorUser = authServices.loadUserByUsername(conductor).getUsuario();
            uws.agregarPosiblePasajero(pasajeroUser, conductorUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UniWheelsPersistenceException ex){
            System.out.println(ex.getMessage());
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/prueba",method=RequestMethod.GET)
    public String probando(){
        return "Deberias ver este mensaje solo si estas logeado";
    }

}