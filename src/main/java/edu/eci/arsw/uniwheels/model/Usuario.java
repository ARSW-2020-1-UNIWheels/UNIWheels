package edu.eci.arsw.uniwheels.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
@DynamicUpdate
@DynamicInsert
@Transactional
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "usuario")
    @JsonIgnore
    public Set<Carro> carros;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "usuario")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Conductor> viajesRealizados;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "usuario")
    @JsonIgnore
    public Set<Pasajero> viajesRecibidos;
    @OneToMany
    @JsonIgnore
    public Set<Ruta> rutas;

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

    public Set<Carro> getCarros() {
        return carros;
    }

    public void setCarros(Set<Carro> carros) {
        this.carros = carros;
    }

    public Set<Conductor> getViajesRealizados() {
        return viajesRealizados;
    }

    public void setViajesRealizados(Set<Conductor> viajesRealizados) {
        this.viajesRealizados = viajesRealizados;
    }

    public Set<Pasajero> getViajesRecibidos() {
        return viajesRecibidos;
    }

    public void setViajesRecibidos(Set<Pasajero> viajesRecibidos) {
        this.viajesRecibidos = viajesRecibidos;
    }

    public Set<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(Set<Ruta> rutas) {
        this.rutas = rutas;
    }

}