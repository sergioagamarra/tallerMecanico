package com.example.tallermecanicoantonio.Entidades;

public class Servicio {
    private String dni_cliente;

    private String importe;
    private String modelo, fecha, descripcion, dominio, nombre;

    public Servicio() {
    }

    public Servicio(String dni_cliente, String importe, String modelo, String fecha, String descripcion, String dominio, String nombre) {
        this.dni_cliente = dni_cliente;
        this.importe = importe;
        this.modelo = modelo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.dominio = dominio;
        this.nombre = nombre;
    }



    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String  dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
