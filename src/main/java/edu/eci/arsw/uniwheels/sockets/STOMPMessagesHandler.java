package edu.eci.arsw.uniwheels.sockets;


import edu.eci.arsw.uniwheels.model.Conductor;
import edu.eci.arsw.uniwheels.model.DetallesUsuario;
import edu.eci.arsw.uniwheels.services.UniWheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;


@Controller
public class STOMPMessagesHandler extends BaseHandler{

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    UniWheelsServices uniWheelsServices;

    @MessageMapping("/nuevoConductor")
    public void handlePointEvent(Conductor conductor, Principal principal) throws Exception {
        Conductor conductorPrueba = new Conductor();
        DetallesUsuario usuario = (DetallesUsuario) ((Authentication) principal).getPrincipal();
        conductorPrueba.setUsuario(usuario.getUsuario());
        conductorPrueba.tiempoRecorrido = 50000;
        conductorPrueba.nombreEstado = "Disponible";
        conductorPrueba.conductorName = usuario.getUsuario().username;
        usuario.getUsuario().viajesRealizados.add(conductorPrueba);
        System.out.println(usuario.getUsuario().viajesRealizados.size());
        uniWheelsServices.saveConductorDisponible(conductorPrueba);

        List<Conductor> todosLosConductores = uniWheelsServices.getConductoresDisponibles();

        msgt.convertAndSend("/uniwheels/conductoresDisponibles", todosLosConductores);

    }

    @MessageMapping("/conductoresDisponibles")
    public void conductoresDisponibles() throws Exception {
        List<Conductor> todosLosConductores = uniWheelsServices.getConductoresDisponibles();

        msgt.convertAndSend("/uniwheels/conductoresDisponibles", todosLosConductores);

    }


}
