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

    public Conductor getConductor(String name) throws UniWheelsPersistenceException {
        return uwp.getConductor(name);
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
    public void saveRuta(Ruta ruta)throws UniWheelsPersistenceException{
        uwp.saveRuta(ruta);
        uwp.updateDatabase();
    }

    public void actualizarDB(){
        uwp.updateDatabase();
    }

    public void deleteCarro(Integer carro){
        uwp.deleteCarToUser(carro);
    }

    public void agregarPosiblePasajero(Pasajero pasajero) throws UniWheelsPersistenceException{
        uwp.savePasajeros(pasajero);
        uwp.updateDatabase();
    }

    public void createUniversity(Universidad universidad){
        uwp.createUniversity(universidad);
    }

    public void deleteUniveristy(Universidad universidad){
        uwp.deleteUniversity(universidad);
    }



}