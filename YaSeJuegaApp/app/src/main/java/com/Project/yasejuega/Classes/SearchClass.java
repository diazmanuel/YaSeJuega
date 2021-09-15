package com.Project.yasejuega.Classes;

import com.google.android.gms.maps.model.LatLng;

public class SearchClass {

    private Integer predio_pk;
    private String predio;
    private String precio;
    private String direccion;
    private String telefono;
    private Integer valoracion;
    private Integer nVal;
    private String url;

    private Boolean[] extra;
    private LatLng latLng;

    public SearchClass() {
    }

    public SearchClass(Integer predio_pk, String predio, String precio, String zona, String direccion, String telefono, Integer valoracion,Integer nval, String latLng,int extra,String url) {
        this.predio_pk = predio_pk;
        this.predio = predio;
        this.precio = precio;
        this.direccion = direccion+" - "+zona;
        this.telefono = telefono;
        this.valoracion = valoracion;
        this.nVal=nval;
        this.extra[0] = ((extra>>0x00)&0x01)==1;
        this.extra[1] = ((extra>>0x01)&0x01)==1;
        this.extra[2] = ((extra>>0x02)&0x01)==1;
        this.extra[3] = ((extra>>0x03)&0x01)==1;
        this.extra[4] = ((extra>>0x04)&0x01)==1;
        this.latLng = new LatLng((Double.parseDouble(latLng.split(",")[0])),(Double.parseDouble(latLng.split(",")[1])));
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPredio_pk() {
        return predio_pk;
    }

    public void setPredio_pk(Integer predio_pk) {
        this.predio_pk = predio_pk;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion,String zona) {
        this.direccion = direccion+" - "+zona;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public Integer getnVal() {
        return nVal;
    }

    public void setnVal(Integer nVal) {
        this.nVal = nVal;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }


    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = new LatLng((Double.parseDouble(latLng.split(",")[0])),(Double.parseDouble(latLng.split(",")[1])));
    }

    public void setExtra(Integer extra){
        this.extra = new Boolean[5];
        this.extra[0] = ((extra>>0x00)&0x01)==1;
        this.extra[1] = ((extra>>0x01)&0x01)==1;
        this.extra[2] = ((extra>>0x02)&0x01)==1;
        this.extra[3] = ((extra>>0x03)&0x01)==1;
        this.extra[4] = ((extra>>0x04)&0x01)==1;
    }



    public Boolean[] getExtra() {
        return extra;
    }


}
