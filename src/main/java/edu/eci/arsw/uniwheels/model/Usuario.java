package edu.eci.arsw.uniwheels.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
@DynamicUpdate
@DynamicInsert
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public int userId;
    @Column(name="username")
    public String username;
    @Column(name="password")
    public String password;
    @Column(name="email")
    public String email;
    @Column(name="rol")
    public String rol;
    @Column(name="universidad")
    public String universidad;
    @Column(name ="direccionResidencia")
    public String direccionResidencia;
    @OneToMany
    public List<Carro> carros;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "usuario")
    public List<Conductor> viajesRealizados;
    @OneToMany
    public List<Pasajero> viajesRecibidos;
    @OneToMany
    public List<Ruta> rutas;




    public Usuario(){

    }
    public Usuario(String username,String password,String email, String rol, String universidad, String direccionResidencia){
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.universidad = universidad;
        this.direccionResidencia = direccionResidencia;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public List<Conductor> getViajesRealizados() {
        return viajesRealizados;
    }

    public void setViajesRealizados(List<Conductor> viajesRealizados) {
        this.viajesRealizados = viajesRealizados;
    }

    public List<Pasajero> getViajesRecibidos() {
        return viajesRecibidos;
    }

    public void setViajesRecibidos(List<Pasajero> viajesRecibidos) {
        this.viajesRecibidos = viajesRecibidos;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    @Override
    public String toString() {
        return "Usuario{" + this.username +","+ this.password +","+ this.email+"}";
    }
}