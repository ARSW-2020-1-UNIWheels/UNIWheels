package edu.eci.arsw.uniwheels.persistence.imp;

import edu.eci.arsw.uniwheels.model.*;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistence;
import edu.eci.arsw.uniwheels.persistence.UniWheelsPersistenceException;
import edu.eci.arsw.uniwheels.repository.*;
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

    }

    @Override
    public List<Conductor> getConductoresDisponibles() throws UniWheelsPersistenceException{
        List<Conductor> conductors = conductorRepository.findAll();
        System.out.println(conductors.size());
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
    public Conductor getConductor(String name) throws UniWheelsPersistenceException {
        List<Conductor> conductors = conductorRepository.findAll();
        for(Conductor conduc:conductors){
            if(conduc.getConductorName().equals(name)){
                return conduc;
            }
        }
        return null;
    }

}