package edu.eci.arsw.uniwheels.sockets;


import edu.eci.arsw.uniwheels.model.*;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.services.UniWheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class STOMPMessagesHandler extends BaseHandler{

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    UniWheelsServices uniWheelsServices;

    @MessageMapping("/recibirPasajeros")
    public void recibirPasajeros(Principal principal){
        Usuario usuario = getLoggedUser(principal).usuario;
        Conductor conductor = null;
        try{
            conductor = uniWheelsServices.getConductor(usuario.username);
        } catch (UniWheelsPersistenceException ex){
            ex.printStackTrace();
        }
        if(conductor!=null){
            msgt.convertAndSend("/uniwheels/pasajero."+conductor.conductorName, conductor.pasajeros);
            msgt.convertAndSend("/uniwheels/posiblesConductores."+conductor.conductorName, conductor.posiblesPasajeros);
        }
    }

    @MessageMapping("/nuevoConductor")
    public void agregarConductor(Ruta ruta,  String camioneta ,Principal principal) throws Exception {
        Conductor conductor = new Conductor();
        String[] separacionJson = camioneta.split("}");
        camioneta = separacionJson[1];
        List<Carro> carrosPorUsuario = uniWheelsServices.getCarrosDelUsuario(getLoggedUser(principal).getUsuario());
        for (Carro c:carrosPorUsuario){
            if (c.getPlaca().equals(camioneta)){
                conductor.carro = c;
                break;
            }
        }
        DetallesUsuario usuario = getLoggedUser(principal);
        conductor.setUsuario(usuario.getUsuario());
        conductor.nombreEstado = "Disponible";
        conductor.conductorName = usuario.getUsuario().username;
        uniWheelsServices.saveRuta(ruta);
        conductor.setRuta(ruta);
        usuario.getUsuario().viajesRealizados.add(conductor);
        uniWheelsServices.saveConductorDisponible(conductor);

        List<Conductor> todosLosConductores = uniWheelsServices.getConductoresDisponibles();
        uniWheelsServices.actualizarDB();
        msgt.convertAndSend("/uniwheels/conductoresDisponibles", todosLosConductores);

    }

    @MessageMapping("/obtenerPasajeroEnViaje")
    public void obtenerPasajeroEnViaje(Principal principal){
        Usuario usuario = getLoggedUser(principal).usuario;
        List<Pasajero> viajesRecibidos = uniWheelsServices.obtenerPasajerosPorNombre(usuario.username);
        Conductor conductorActual = null;
        for(Pasajero p: viajesRecibidos){
            if(p.nombreEstado.equals("Aceptado")){
                conductorActual = p.conductor;
                break;
            }
        }
        if(conductorActual!= null){
            msgt.convertAndSend("/uniwheels/pasajeroAceptado."+usuario.username,conductorActual);
        }

    }

    @MessageMapping("/agregarPosiblePasajero")
    public void posiblePasajero(String nameConductor, Principal principal) throws Exception {
        Pasajero pasajero = new Pasajero();
        Conductor conductor = uniWheelsServices.getConductor(nameConductor);
        pasajero.nombreEstado = "Disponible";
        DetallesUsuario usuario = getLoggedUser(principal);
        pasajero.setUsuario(usuario.getUsuario());
        pasajero.pasajeroName = usuario.usuario.username;
        conductor.posiblesPasajeros.add(pasajero);
        pasajero.posiblesConductores.add(conductor);
        usuario.usuario.viajesRecibidos.add(pasajero);
        uniWheelsServices.agregarPosiblePasajero(pasajero);
        uniWheelsServices.actualizarDB();
        msgt.convertAndSend("/uniwheels/posiblesConductores."+conductor.conductorName, conductor.posiblesPasajeros);

    }

    @MessageMapping("/agregarPasajero.{conductorUsername}")
    public void accionSobrePasajero(String idPasajero,@DestinationVariable String conductorUsername, Principal principal) throws Exception {

        DetallesUsuario usuario = getLoggedUser(principal);
        Conductor conductor = uniWheelsServices.getConductor(conductorUsername);
        String[] separacionJson = idPasajero.split(",");
        idPasajero = separacionJson[0];
        String aceptado = separacionJson[1];
        Pasajero pasajero = uniWheelsServices.getPasajero(Integer.parseInt(idPasajero));
        boolean hayCupo = true;
        if(conductor.pasajeros.size()==3 && aceptado.equals("true")){
            uniWheelsServices.updateEstado("Sin cupo",conductor.id,0);

        } else if (conductor.pasajeros.size()==4){
            hayCupo = false;
        }
        if(aceptado.equals("true") && hayCupo){
            uniWheelsServices.updateEstado("Aceptado",0,pasajero.id);
            //conductor.pasajeros.add(pasajero);
            uniWheelsServices.updateConductorinPassanger(conductor,pasajero.id);
            //conductor.posiblesPasajeros.remove(pasajero);
            uniWheelsServices.deletePosiblePasajero(pasajero.id,conductor.id);
            List<Pasajero> otrosViajesPasajero = uniWheelsServices.obtenerPasajerosPorNombre(pasajero.pasajeroName);
            for(Pasajero p:otrosViajesPasajero){
                if(p.id!=pasajero.id){
                    uniWheelsServices.updateEstado("Finalizado",0,p.id);
                }
            }
            List<Conductor> otrosConductores = uniWheelsServices.getConductoresDisponibles();
            for(Conductor otroConductor:otrosConductores){
                if(otroConductor.posiblesPasajeros.contains(pasajero) && otroConductor.id == conductor.id){

                    uniWheelsServices.deletePosiblePasajero(pasajero.id,otroConductor.id);
                    Conductor otroConductorPrueba = uniWheelsServices.getConductor(otroConductor.conductorName);
                    msgt.convertAndSend("/uniwheels/posiblesConductores."+otroConductor.conductorName, otroConductorPrueba.posiblesPasajeros);
                }
            }
            conductor = uniWheelsServices.getConductor(conductorUsername);
            msgt.convertAndSend("/uniwheels/pasajeroAceptado."+pasajero.pasajeroName,conductor);

            msgt.convertAndSend("/uniwheels/pasajero."+conductor.conductorName, conductor.pasajeros);
        } else {
            uniWheelsServices.updateEstado("Rechazado",0,pasajero.id);
            uniWheelsServices.updateConductorinPassanger(conductor,pasajero.id);
            uniWheelsServices.deletePosiblePasajero(pasajero.id,conductor.id);
            msgt.convertAndSend("/uniwheels/pasajeroRechazado."+pasajero.pasajeroName,conductor);
        }
        uniWheelsServices.actualizarDB();
        conductor = uniWheelsServices.getConductor(conductorUsername);
        List<Conductor> todosLosConductores = uniWheelsServices.getConductoresDisponibles();
        msgt.convertAndSend("/uniwheels/conductoresDisponibles", todosLosConductores);
        msgt.convertAndSend("/uniwheels/posiblesConductores."+conductor.conductorName, conductor.posiblesPasajeros);
    }

    @MessageMapping("/conductoresDisponibles")
    public void conductoresDisponibles(Principal principal) throws Exception {
        List<Conductor> todosLosConductores = uniWheelsServices.getConductoresDisponibles();
        msgt.convertAndSend("/uniwheels/conductoresDisponibles", todosLosConductores);
    }

    @MessageMapping("/terminarCarrera.{conductorName}")
    public void terminarCarrera(@DestinationVariable String conductorName, Principal principal) throws Exception{
        Conductor conductor = uniWheelsServices.getConductor(conductorName);
        conductor.nombreEstado = "Finalizado";
        uniWheelsServices.updateEstado(conductor.nombreEstado,conductor.id,0);
        List<Pasajero> pasajeros = conductor.getPasajeros();
        for (Pasajero pas: pasajeros){
            pas.nombreEstado = "Finalizado";
            uniWheelsServices.updateEstado(pas.nombreEstado,0,pas.id);
        }
        msgt.convertAndSend("/uniwheels/conductorFinalizado."+conductor.conductorName,"Viaje Finalizado");

    }

}
