package edu.eci.arsw.uniwheels.controllers;

import edu.eci.arsw.uniwheels.model.DetallesUsuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {
    protected DetallesUsuario getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DetallesUsuario userDetails = null;
        if (principal instanceof DetallesUsuario) {
            userDetails = (DetallesUsuario) principal;
        }
        return userDetails;
    }
}
