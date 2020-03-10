package edu.eci.arsw.uniwheels.controllers;

import edu.eci.arsw.uniwheels.model.Usuario;
import edu.eci.arsw.uniwheels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class UniWheelsApiAuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder = null;

    @GetMapping("/getMsg")
    public String greeting(){
        return "Spring Security Example";
    }

    @RequestMapping (path = "/adduser",method = RequestMethod.POST)
    public String addUser(@RequestBody Usuario usuario){
        System.out.println("Etrne");
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        System.out.println("Antes de usuario");
        userRepository.save(usuario);
        System.out.println("Eyyyy");
        return "Usuario creado";
    }
}
