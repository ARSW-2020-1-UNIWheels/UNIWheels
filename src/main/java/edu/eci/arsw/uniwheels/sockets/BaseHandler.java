package edu.eci.arsw.uniwheels.sockets;

import edu.eci.arsw.uniwheels.model.DetallesUsuario;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseHandler {
    protected DetallesUsuario getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DetallesUsuario userDetails = null;
        if (principal instanceof DetallesUsuario) {
            userDetails = (DetallesUsuario) principal;
        }
        return userDetails;
    }
}
