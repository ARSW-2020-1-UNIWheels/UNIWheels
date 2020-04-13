package edu.eci.arsw.uniwheels.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "pasajero")
@Transactional
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public int tiempoRecorrido;
    @OneToOne
    public Calificacion calificacion;
    @OneToOne
    public Ruta ruta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="conductor")
    public Conductor conductor;
    public String nombreEstado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Usuario usuario;

    public String pasajeroName;
    public Pasajero(){

    }
    public Pasajero(int tiempoRecorrido, Conductor conductor, Ruta ruta, Calificacion calificacion, String nombreEstado){
        this.tiempoRecorrido = tiempoRecorrido;
        this.conductor = conductor;
        this.ruta = ruta;
        this.calificacion = calificacion;
        this.nombreEstado = nombreEstado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    public void setTiempoRecorrido(int tiempoRecorrido) {
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPasajeroName() {
        return pasajeroName;
    }

    public void setPasajeroName(String pasajeroName) {
        this.pasajeroName = pasajeroName;
    }
}
