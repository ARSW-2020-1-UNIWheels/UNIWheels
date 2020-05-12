package edu.eci.arsw.uniwheels.persistence;

import edu.eci.arsw.uniwheels.model.*;

import java.util.List;

/**
 *
 * @author Carlos Murillo
 */
public interface UniWheelsPersistence {
    public void saveUser(Usuario usr) throws UniWheelsPersistenceException;
    public Usuario getUser(String name) throws UniWheelsPersistenceException;
    public List<Usuario> getAllUsers() throws UniWheelsPersistenceException;
    public void savePasajeros( Pasajero pasajero) throws UniWheelsPersistenceException;
    public List<Conductor> getConductoresDisponibles() throws UniWheelsPersistenceException;
    public void saveConductorDisponible(Conductor conductor) throws UniWheelsPersistenceException;
    public void saveRuta(Ruta ruta) throws UniWheelsPersistenceException;

    Pasajero getPasajero(int id);



    public Conductor getConductor(String name ) throws UniWheelsPersistenceException;

    void addCarToUser(Carro carro);


    void deleteCarToUser(Integer carro);

    void createUniversity(Universidad universidad);

    void deleteUniversity(Universidad universidad);

    List<Universidad> getAllUniversity();

    List<Carro> getCarrosPorUsuario(Usuario usuario);

    public void updateDatabase();
    public void agregarPasajeroALaRuta(Pasajero pasajero, Conductor conductor);

    void updateEstado(String estado, int idConductor, int idPasajero);

    void updateConductorinPassenger(Conductor conductor, int idPasajero);

    void deletePosiblePasajero(int idPasajero, int idConductor);




    void añadirValoracion(int idConductor, int idPasajero,int valoracion);

    List<Pasajero> obtenerTodosLosPasajerosPorUsuario(String pasajeroName);
}