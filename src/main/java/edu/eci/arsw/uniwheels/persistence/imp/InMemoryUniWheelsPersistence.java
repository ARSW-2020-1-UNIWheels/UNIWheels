package edu.eci.arsw.uniwheels.persistence.imp;

import edu.eci.arsw.uniwheels.model.*;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.repository.*;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class InMemoryUniWheelsPersistence implements UniWheelsPersistence {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private UniversidadRepository universidadRepository;

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Override
    public void saveUser(Usuario usuario) throws UniWheelsPersistenceException {
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        userRepository.save(usuario);
    }

    @Override
    public void addCarToUser(Carro carro){
        carroRepository.save(carro);
        updateDatabase();
    }

    @Override
    public void deleteCarToUser(Integer carro){
        carroRepository.deleteById(carro);
        updateDatabase();
    }

    @Override
    public void createUniversity(Universidad universidad){
        universidadRepository.save(universidad);
        updateDatabase();
    }
    @Override
    public void deleteUniversity(Universidad universidad){
        universidadRepository.delete(universidad);
        updateDatabase();
    }
    @Override
    public List<Universidad> getAllUniversity(){
        List<Universidad> universidades = universidadRepository.findAll();
        updateDatabase();
        return universidades;
    }

    @Override
    public List<Carro> getCarrosPorUsuario(Usuario usuario){
        List<Carro> listaCompletaCarros = carroRepository.findAll();
        List<Carro> carrosDelUsuario = new ArrayList<>();
        for (Carro c:listaCompletaCarros){
            if(c.getUsuario().getUserId() == usuario.getUserId()){
                carrosDelUsuario.add(c);
            }
        }
        return carrosDelUsuario;
    }

    @Override
    public void updateDatabase(){
        userRepository.flush();
        pasajeroRepository.flush();
        conductorRepository.flush();
        rutaRepository.flush();
        carroRepository.flush();
        universidadRepository.flush();
    }

    @Override
    public void agregarPasajeroALaRuta(Pasajero pasajero, Conductor conductor) {
        pasajero.conductor=conductor;
        pasajeroRepository.save(pasajero);
        conductor.posiblesPasajeros.add(pasajero);
    }

    @Override
    public Usuario getUser(String name) throws UsernameNotFoundException {
        List<Usuario> usuarios = userRepository.findAll();
        Usuario usuario = null;
        for (Usuario user:usuarios){
            if(user.username.equals(name)){
                usuario = user;
            }
        }
        if (usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }

    @Override
    public List<Usuario> getAllUsers() throws UniWheelsPersistenceException {
        throw new UniWheelsPersistenceException("En proceso de construcción");
    }

    @Override
    public void savePasajeros(Pasajero pasajero) throws UniWheelsPersistenceException{
        pasajeroRepository.save(pasajero);
        updateDatabase();

    }

    @Override
    public List<Conductor> getConductoresDisponibles() throws UniWheelsPersistenceException{
        List<Conductor> conductors = conductorRepository.findAll();
        List<Conductor> conductoresDisponibles = new ArrayList<>();
        for(Conductor conduc:conductors){
            if(conduc.getNombreEstado().equals("Disponible")){
                conductoresDisponibles.add(conduc);
            }
        }
        return conductoresDisponibles;
    }

    @Override
    public void saveConductorDisponible(Conductor conductor) throws UniWheelsPersistenceException{
        conductorRepository.save(conductor);
        updateDatabase();
    }

    @Override
    public void saveRuta(Ruta ruta) throws UniWheelsPersistenceException {
        rutaRepository.save(ruta);
        updateDatabase();
    }

    @Override
    public Pasajero getPasajero(int id){
        List<Pasajero> pasajeros = pasajeroRepository.findAll();
        for(Pasajero pasajero:pasajeros){
            if(pasajero.getId() == id){
                return pasajero;
            }
        }
        return null;
    }



    @Override
    public Conductor getConductor(String name) throws UniWheelsPersistenceException {
        List<Conductor> conductors = conductorRepository.findAll();
        for(Conductor conduc:conductors){
            if(conduc.getConductorName().equals(name) && (conduc.getNombreEstado().equals("Disponible") || conduc.getNombreEstado().equals("Sin cupo"))){
                return conduc;
            }
        }
        return null;
    }

    @Override
    public void updateEstado(String estado, int idConductor, int idPasajero){
        if(idConductor == 0 && idPasajero !=0){
            pasajeroRepository.updatePasajeroDisponible(estado,idPasajero);
        } else if (idConductor !=0 && idPasajero == 0){
            conductorRepository.updateConductorDisponible(estado,idConductor);
        }
    }

    @Override
    public void updateConductorinPassenger(Conductor conductor,int idPasajero){
        pasajeroRepository.updatePasajeroConductor(conductor,idPasajero);
    }

    @Override
    public void deletePosiblePasajero(int idPasajero, int idConductor){
        pasajeroRepository.deletingPosiblesConductores(idPasajero, idConductor);
    }

    @Override
    public void añadirValoracion(int idConductor, int idPasajero,int valoracion){
        Calificacion calificacion = new Calificacion();

        if(idConductor!=0) {
            calificacion.conductor = conductorRepository.findById(idConductor).get();
        } else if (idPasajero !=0) {
            calificacion.pasajero = pasajeroRepository.findById(idPasajero).get();
        }
        calificacion.valor = valoracion;


        calificacionRepository.save(calificacion);
    }

    @Override
    public List<Pasajero> obtenerTodosLosPasajerosPorUsuario(String pasajeroName){
        List<Pasajero> todosLosPasajeros =pasajeroRepository.findAll();
        List<Pasajero> valoresAEnviar = new ArrayList<>();
        for(Pasajero p: todosLosPasajeros){
            if(p.pasajeroName.equals(pasajeroName) && p.nombreEstado.equals("Disponible")){
                valoresAEnviar.add(p);
            }
        }
        return valoresAEnviar;
    }

    @Override
    public Conductor obtenerTodosLosPasajerosPorUsuarioAceptado(String pasajeroName){

        Conductor valoresAEnviar = pasajeroRepository.traerIDConductor(pasajeroName);
        return valoresAEnviar;
    }

    @Override
    public List<Calificacion> obtenerCalificacionesConductor(String name){
        return calificacionRepository.traerCalificacionesDeConductor(name);
    }



}