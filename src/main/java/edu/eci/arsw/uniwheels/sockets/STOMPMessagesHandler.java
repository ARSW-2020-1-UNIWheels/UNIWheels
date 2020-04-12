package edu.eci.arsw.uniwheels.sockets;


import edu.eci.arsw.uniwheels.model.Conductor;
import edu.eci.arsw.uniwheels.services.UniWheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class STOMPMessagesHandler extends BaseHandler{

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    UniWheelsServices uniWheelsServices;

    @MessageMapping("/nuevoConductor")
    public void handlePointEvent(Conductor conductor) throws Exception {
        Conductor conductorPrueba = new Conductor();
        conductor.setUsuario(getLoggedUser().getUsuario());
        conductorPrueba.tiempoRecorrido = 50000;
        conductorPrueba.nombreEstado = "Disponible";
        conductorPrueba.conductorName = getLoggedUser().getUsuario().username;
        getLoggedUser().getUsuario().viajesRealizados.add(conductorPrueba);
        System.out.println(getLoggedUser().getUsuario().viajesRealizados.size());
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
