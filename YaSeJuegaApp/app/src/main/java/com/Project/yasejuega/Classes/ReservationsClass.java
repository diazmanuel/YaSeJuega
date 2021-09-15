package com.Project.yasejuega.Classes;

public class ReservationsClass {

    private Integer reserva_pk;
    private String predio;
    private String precio;
    private String zona;
    private String sena;
    private String estado;
    private String timestamp;
    private String hora;
    private String fecha;
    private String cancha;
    private String direccion;
    private String telefono;
    private String superficie;
    private String techado;
    private String tipo;
    private String nReserva;


    public ReservationsClass() {
    }

    public ReservationsClass(Integer reserva_pk, String predio, String precio, String zona, String sena, String estado, String timestamp, String hora, String fecha, String cancha, String direccion, String telefono, String superficie, String techado, String tipo, String nReserva) {
        this.reserva_pk = reserva_pk;
        this.predio = predio;
        this.precio = precio;
        this.zona = zona;
        this.sena = sena;
        this.estado = estado;
        this.timestamp = timestamp;
        this.hora = hora;
        this.fecha = fecha;
        this.cancha = cancha;
        this.direccion = direccion;
        this.telefono = telefono;
        this.superficie = superficie;
        this.techado = techado;
        this.tipo = tipo;
        this.nReserva = nReserva;
    }

    public String getnReserva() {
        return nReserva;
    }

    public void setnReserva(String nReserva) {
        this.nReserva = nReserva;
    }

    public Integer getReserva_pk() {
        return reserva_pk;
    }

    public void setReserva_pk(Integer reserva_pk) {
        this.reserva_pk = reserva_pk;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSena() {
        return sena;
    }

    public void setSena(String sena) {
        this.sena = sena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCancha() {
        return cancha;
    }

    public void setCancha(String cancha) {
        this.cancha = cancha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getTechado() {
        return techado;
    }

    public void setTechado(String techado) {
        this.techado = techado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
