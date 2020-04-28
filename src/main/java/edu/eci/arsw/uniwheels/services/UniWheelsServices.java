package edu.eci.arsw.uniwheels.services;

import edu.eci.arsw.uniwheels.model.*;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.repository.ConductorRepository;
import edu.eci.arsw.uniwheels.repository.PasajeroRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniWheelsServices {

    @Autowired
    UniWheelsPersistence uwp = null;

    @Autowired
    ConductorRepository conductorRepository;

    @Autowired
    PasajeroRepository pasajeroRepository;





    public List<Conductor> getConductoresDisponibles() throws UniWheelsPersistenceException {
        return uwp.getConductoresDisponibles();
    }

    public void addCarroAlUsuario(Usuario usuario, Carro carro){
        carro.setUsuario(usuario);
        uwp.addCarToUser(carro);
    }

    public List<Carro> getCarrosDelUsuario(Usuario usuario){
        return uwp.getCarrosPorUsuario(usuario);
    }

    public void saveConductorDisponible(Conductor conductor) throws UniWheelsPersistenceException{
        uwp.saveConductorDisponible(conductor);
        uwp.updateDatabase();
    }

    public void deleteCarro(Integer carro){
        uwp.deleteCarToUser(carro);
    }

    public void agregarPosiblePasajero(Usuario pasajero, Usuario conductor) throws UniWheelsPersistenceException{
        System.out.println(conductor.viajesRealizados.size());
        System.out.println("Eyyyy estoy entrando acá");
        List<Conductor> listaConductor = new ArrayList<>(conductor.viajesRealizados);
        Conductor con = listaConductor.get(conductor.viajesRealizados.size()-1);
        Pasajero pas = null;
        boolean acabadoDeCrear = true;
        if (pasajero.viajesRecibidos.size() ==0){
            pas = new Pasajero(0, con, null, null, "Disponible");
            pas.setUsuario(pasajero);
            acabadoDeCrear = false;
            uwp.savePasajeros(pas);
        } else {
            List<Pasajero> listaPasajero = new ArrayList<>(pasajero.viajesRecibidos);
            pas = listaPasajero.get(pasajero.viajesRecibidos.size()-1);
        }
        if (pas.nombreEstado.equals("Finalizado")) {
            pas = new Pasajero(0, con, null, null, "Disponible");
            pas.setUsuario(pasajero);
            uwp.savePasajeros(pas);
        } else if (pas.nombreEstado.equals("Ocupado") || (pas.nombreEstado.equals("Disponible") && acabadoDeCrear)){
            throw new UniWheelsPersistenceException("El pasajero ya esta tomando un servicio");
        }
        uwp.agregarPasajeroALaRuta(pas, con);
        uwp.updateDatabase();
    }

}