package edu.eci.arsw.uniwheels.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.uniwheels.model.Carro;
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

    @RequestMapping(value="/getCarros", method=RequestMethod.GET)
    public ResponseEntity<?> getCarrosAlUsuario(){
        try{
            Usuario usuario = getLoggedUser().getUsuario();
            List<Carro> carros = uws.getCarrosDelUsuario(usuario);
            return new ResponseEntity<>(carros,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/addCarro", method=RequestMethod.POST)
    public ResponseEntity<?> addCarrosAlUsuario(@RequestBody Carro carro){
        try{
            Usuario usuario = getLoggedUser().getUsuario();
            uws.addCarroAlUsuario(usuario,carro);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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


    // Añadiendo usuario
    @RequestMapping(value="/addUser", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody Usuario usuario){
		try{
			System.out.println("Vamos a agregar el usuario .............");
			System.out.println(usuario);
			authServices.addUser(usuario);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (UniWheelsPersistenceException ex){
			System.out.println("ERROR NO PODEMOS CREAR USUARIO");
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
			
		}
        
    }


}