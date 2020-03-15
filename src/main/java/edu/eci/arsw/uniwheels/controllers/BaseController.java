package edu.eci.arsw.uniwheels.controllers;

import edu.eci.arsw.uniwheels.model.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {
    protected Usuario getLoggedUser() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
