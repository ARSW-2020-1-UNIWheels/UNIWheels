package edu.eci.arsw.uniwheels.services;

import edu.eci.arsw.uniwheels.model.*;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.repository.ConductorRepository;
import edu.eci.arsw.uniwheels.repository.PasajeroRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniWheelsServices {

    @Autowired
    UniWheelsPersistence uwp = null;

    @Autowired
    ConductorRepository conductorRepository;

    @Autowired
    PasajeroRepository pasajeroRepository;

    public void addNewUser(Usuario usr) throws UniWheelsPersistenceException{
        uwp.saveUser(usr);
    }


    public List<Usuario> getAllUsers() throws UniWheelsPersistenceException{
        return uwp.getAllUsers();
    }



    public List<Conductor> getConductoresDisponibles() throws UniWheelsPersistenceException {
        return uwp.getConductoresDisponibles();
    }

    public void saveConductorDisponible(Conductor conductor) throws UniWheelsPersistenceException{
        uwp.saveConductorDisponible(conductor);
        uwp.updateDatabase();
    }

    public void agregarPosiblePasajero(Usuario pasajero, Usuario conductor) throws UniWheelsPersistenceException{
        System.out.println(conductor.viajesRealizados.size());
        Conductor con = conductor.viajesRealizados.get(conductor.viajesRealizados.size()-1);
        Pasajero pas = null;
        boolean acabadoDeCrear = true;
        if (pasajero.viajesRecibidos.size() ==0){
            pas = new Pasajero(null, con, null, null, "Disponible");
            acabadoDeCrear = false;
            uwp.savePasajeros(pas);
        } else {
            pas = pasajero.viajesRecibidos.get(pasajero.viajesRecibidos.size()-1);
        }
        if (pas.nombreEstado.equals("Finalizado")) {
            pas = new Pasajero(null, con, null, null, "Disponible");
            uwp.savePasajeros(pas);
        } else if (pas.nombreEstado.equals("Ocupado") || (pas.nombreEstado.equals("Disponible") && acabadoDeCrear)){
            throw new UniWheelsPersistenceException("El pasajero ya esta tomando un servicio");
        }
        uwp.agregarPasajeroALaRuta(pas, con);
        uwp.updateDatabase();
    }

}