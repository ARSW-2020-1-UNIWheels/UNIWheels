package edu.eci.arsw.uniwheels.sockets;

import edu.eci.arsw.uniwheels.model.DetallesUsuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class BaseHandler {
    protected DetallesUsuario getLoggedUser(Principal principal) {
        DetallesUsuario usuario = (DetallesUsuario) ((Authentication) principal).getPrincipal();
        return usuario;
    }
}
