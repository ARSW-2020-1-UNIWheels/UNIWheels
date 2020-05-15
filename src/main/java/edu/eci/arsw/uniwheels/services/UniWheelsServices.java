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

    public Pasajero getPasajero(int id){
        Pasajero pasajero = uwp.getPasajero(id);
        if(pasajero!=null){
            return pasajero;
        }
        return null;
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

    public void savePasajero(Pasajero pasajero) throws UniWheelsPersistenceException {
        uwp.savePasajeros(pasajero);
    }

    public void updateEstado(String estado, int idConductor, int idPasajero){
        uwp.updateEstado(estado, idConductor, idPasajero);
    }

    public void updateConductorinPassanger(Conductor conductor, int idPasajero){
        uwp.updateConductorinPassenger(conductor, idPasajero);
    }

    public void deletePosiblePasajero(int idPasajero,int idConductor){
        uwp.deletePosiblePasajero(idPasajero, idConductor);
    }

    public void añadirValoracion(int idConductor, int idPasajero,int valoracion){
        uwp.añadirValoracion(idConductor,idPasajero,valoracion);
    }

    public float obtenerValoracionPorUsuario(String username, String tipo) throws UniWheelsPersistenceException {
        Usuario usuario = uwp.getUser(username);
        float valoracionCompleta = 0;
        int sumaCalificaciones = 0;
        if(tipo.equals("conductor")){
            List<Calificacion> calificaciones = uwp.obtenerCalificacionesConductor(username);
            for(Calificacion c: calificaciones){
                valoracionCompleta+=c.valor;
                sumaCalificaciones++;

            }
            if(sumaCalificaciones!=0) {
                valoracionCompleta = valoracionCompleta / sumaCalificaciones;
            }
        } else {
            for(Pasajero p:usuario.viajesRecibidos){
                for(Calificacion cal:p.calificacion){
                    valoracionCompleta+=cal.valor;
                    sumaCalificaciones++;
                }
            }
            if(sumaCalificaciones!=0) {
                valoracionCompleta = valoracionCompleta / sumaCalificaciones;
            }
        }
        return valoracionCompleta;
    }

    public List<Pasajero> obtenerPasajerosPorNombre(String username){
        return uwp.obtenerTodosLosPasajerosPorUsuario(username);
    }

    public Conductor obtenerPasajerosPorNombreParaAceptar(String username){
        return uwp.obtenerTodosLosPasajerosPorUsuarioAceptado(username);
    }


}