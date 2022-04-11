package com.example.tallermecanicoantonio.Entidades;

public class Administrador {
    private int dni_adm;
    private String usuario, contrasenia;

    public Administrador() {
    }

    public Administrador(int dni_adm, String usuario, String contrasenia) {
        this.dni_adm = dni_adm;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public int getDni_adm() {
        return dni_adm;
    }

    public void setDni_adm(int dni_adm) {
        this.dni_adm = dni_adm;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
