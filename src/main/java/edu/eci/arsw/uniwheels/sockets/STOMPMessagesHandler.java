package edu.eci.arsw.uniwheels.sockets;


import edu.eci.arsw.uniwheels.model.*;
import edu.eci.arsw.uniwheels.services.UniWheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class STOMPMessagesHandler extends BaseHandler{

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    UniWheelsServices uniWheelsServices;

    @MessageMapping("/nuevoConductor")
    public void agregarConductor(Ruta ruta,  String camionetagit,Principal principal) throws Exception {
        Conductor conductor = new Conductor();
        List<Carro> carrosPorUsuario = uniWheelsServices.getCarrosDelUsuario(getLoggedUser(principal).getUsuario());
        for (Carro c:carrosPorUsuario){
            if (c.getPlaca().equals(camioneta)){
                conductor.carro = c;
                break;
            }
        }
        System.out.println(ruta.direccionOrigen+ " " + ruta.direccionDestino);
        DetallesUsuario usuario = getLoggedUser(principal);
        conductor.setUsuario(usuario.getUsuario());
        conductor.nombreEstado = "Disponible";
        conductor.conductorName = usuario.getUsuario().username;
        uniWheelsServices.saveRuta(ruta);
        conductor.setRuta(ruta);
        usuario.getUsuario().viajesRealizados.add(conductor);
        System.out.println(usuario.getUsuario().viajesRealizados.size());
        uniWheelsServices.saveConductorDisponible(conductor);

        List<Conductor> todosLosConductores = uniWheelsServices.getConductoresDisponibles();
        uniWheelsServices.actualizarDB();
        msgt.convertAndSend("/uniwheels/conductoresDisponibles", todosLosConductores);

    }

    @MessageMapping("/agregarPosiblePasajero")
    public void posiblePasajero(String nameConductor, Principal principal) throws Exception {
        Pasajero pasajero = new Pasajero();
        Conductor conductor = uniWheelsServices.getConductor(nameConductor);
        DetallesUsuario usuario = getLoggedUser(principal);
        pasajero.setUsuario(usuario.getUsuario());
        conductor.posiblesPasajeros.add(pasajero);
        uniWheelsServices.actualizarDB();
        msgt.convertAndSend("/uniwheels/posiblesConductores."+conductor.id, conductor.posiblesPasajeros);

    }

    @MessageMapping("/agregarPasajero")
    public void accionSobrePasajero(Conductor conductor, Pasajero pasajero,boolean aceptado, Principal principal) throws Exception {
        DetallesUsuario usuario = getLoggedUser(principal);
        if(aceptado){
            conductor.pasajeros.add(pasajero);
            conductor.posiblesPasajeros.remove(pasajero);
            msgt.convertAndSend("/uniwheels/pasajeroAceptado."+pasajero.id,conductor);
        } else {
            conductor.posiblesPasajeros.remove(pasajero);
        }
        uniWheelsServices.actualizarDB();
        msgt.convertAndSend("/uniwheels/posiblesConductores."+conductor.id, conductor.posiblesPasajeros);
    }

    @MessageMapping("/conductoresDisponibles")
    public void conductoresDisponibles(Principal principal) throws Exception {
        List<Conductor> todosLosConductores = uniWheelsServices.getConductoresDisponibles();
        msgt.convertAndSend("/uniwheels/conductoresDisponibles", todosLosConductores);
    }

    @MessageMapping("/terminarCarrera")
    public void terminarCarrera(Conductor conductor) throws Exception{
        conductor.nombreEstado = "Finalizado";
        List<Pasajero> pasajeros = conductor.getPasajeros();
        for (Pasajero pas: pasajeros){
            pas.nombreEstado = "Finalizado";
        }
        uniWheelsServices.actualizarDB();
        msgt.convertAndSend("/uniwheels/conductorFinalizado");
    }

}
