package com.Project.yasejuega.Classes;

public class TournamentsClass {
    private Integer torneo_pk;
    private String predio;
    private String direccion;
    private String zona;
    private String telefono;
    private String precio;
    private String precioXpartido;
    private String equipo;
    private String fecha_inicio;

    public TournamentsClass() {
    }

    public TournamentsClass(Integer torneo_pk, String predio, String direccion, String zona, String telefono, String precio, String precioXpartido, String equipo, String fecha_inicio) {
        this.torneo_pk = torneo_pk;
        this.predio = predio;
        this.direccion = direccion;
        this.zona = zona;
        this.telefono = telefono;
        this.precio = precio;
        this.precioXpartido = precioXpartido;
        this.equipo = equipo;
        this.fecha_inicio = fecha_inicio;
    }

    public Integer getTorneo_pk() {
        return torneo_pk;
    }

    public void setTorneo_pk(Integer torneo_pk) {
        this.torneo_pk = torneo_pk;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPrecioXpartido() {
        return precioXpartido;
    }

    public void setPrecioXpartido(String precioXpartido) {
        this.precioXpartido = precioXpartido;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
}
