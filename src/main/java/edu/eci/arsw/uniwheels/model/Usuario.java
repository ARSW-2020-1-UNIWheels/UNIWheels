package edu.eci.arsw.uniwheels.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "user_id")
    public int userId;
    @Column(name="username")
    public String username;
    @Column(name="password")
    public String password;
    @Column(name="email")
    public String email;

    public Usuario(){

    }
    public Usuario(String username,String password,String email){

        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "Usuario{" + this.username +","+ this.password +","+ this.email+"}";
    }
}