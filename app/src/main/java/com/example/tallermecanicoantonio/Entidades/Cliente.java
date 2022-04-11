package com.example.tallermecanicoantonio.Entidades;

public class Cliente {
    private String dni;
    private String nombre, email, telefono;

    public Cliente(String dni, String telefono, String nombre, String email) {
        this.dni = dni;
        this.telefono = telefono;
        this.nombre = nombre;
        this.email = email;
    }
    public Cliente() {

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
