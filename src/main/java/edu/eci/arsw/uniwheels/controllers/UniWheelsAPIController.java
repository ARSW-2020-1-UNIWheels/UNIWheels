package edu.eci.arsw.uniwheels.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.eci.arsw.uniwheels.model.*;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.services.AuthServices;
import edu.eci.arsw.uniwheels.services.UniWheelsServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    @RequestMapping(value="/putLocalization/{username}/{localization}",method = RequestMethod.PATCH)
    public ResponseEntity<?> putLocalizacion(@PathVariable String username,@PathVariable String localization){
        try{
            uws.agregarUbicacionPersona(username,localization);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/getLocalization/{username}")
    public ResponseEntity<?> getLocalizacion(@PathVariable String username){
        try {
            JSONObject posicion = new JSONObject();
            String ubicacion = uws.getUbicacionConductor(username);
            String[] tmp = ubicacion.split(" ");
            posicion.put("latitud",Float.valueOf(tmp[0]));
            posicion.put("longitud",Float.valueOf(tmp[1]));


            return new ResponseEntity<>(posicion,HttpStatus.OK);
        } catch (UniWheelsPersistenceException e) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
    @RequestMapping(value="/existConductor", method =  RequestMethod.GET)
    public ResponseEntity<?> existConductor(){
        try {
            Conductor conductor = uws.getConductor(getLoggedUser().usuario.username);
            boolean respuesta = false;
            if(conductor!=null){
                respuesta = true;
            }
            return new ResponseEntity<>(respuesta,HttpStatus.ACCEPTED);
        } catch (UniWheelsPersistenceException ex) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/createUniversity",method = RequestMethod.POST)
    public ResponseEntity<?> CreateUniversity(@RequestBody Universidad universidad){
        try{
            uws.createUniversity(universidad);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/deleteUniversity",method = RequestMethod.DELETE)
    public ResponseEntity<?> DeleteUniversity(@RequestBody Universidad universidad){
        try{
            uws.createUniversity(universidad);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }


    @RequestMapping(value="/deleteCarro/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCarroAlUsuario(@PathVariable String id){
        try{

            uws.deleteCarro(Integer.parseInt(id));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @RequestMapping(value="/getCarros", method=RequestMethod.GET)
    public ResponseEntity<?> getCarrosAlUsuario(){
        try{
            Usuario usuario = getLoggedUser().getUsuario();
            List<Carro> carros = uws.getCarrosDelUsuario(usuario);
            return new ResponseEntity<>(carros,HttpStatus.OK);
        } catch (Exception e){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/addValoracion/{idConductor}/{idPasajero}/{valoracion}",method=RequestMethod.POST)
    public ResponseEntity<?> añadirValoracion(@PathVariable int idConductor,@PathVariable int idPasajero,@PathVariable int valoracion){
        try{
            uws.añadirValoracion(idConductor,idPasajero,valoracion);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/getValoracion/{username}/{tipo}")
    public ResponseEntity<?> getValoracion(@PathVariable String username,@PathVariable String tipo){
        try {
            float valoracion = uws.obtenerValoracionPorUsuario(username,tipo);
            return new ResponseEntity<>(valoracion,HttpStatus.OK);
        } catch (UniWheelsPersistenceException e) {
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





    @RequestMapping(value="/prueba",method=RequestMethod.GET)
    public String probando(){
        return "Deberias ver este mensaje solo si estas logeado";
    }

}