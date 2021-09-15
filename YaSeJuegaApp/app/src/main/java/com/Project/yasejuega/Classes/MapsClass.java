package com.Project.yasejuega.Classes;

import com.google.android.gms.maps.model.LatLng;

public class MapsClass {
   private String predio;
   private Integer predio_pk;
   private LatLng latLng;

    public MapsClass(String predio, Integer predio_pk, LatLng latLng) {
        this.predio = predio;
        this.predio_pk = predio_pk;
        this.latLng = latLng;
    }

    public MapsClass() {
    }

    public MapsClass(String predio , String latLng,Integer predio_pk) {
        this.predio_pk = predio_pk;
        this.predio = predio;
        this.latLng = new LatLng((Double.parseDouble(latLng.split(",")[0])),(Double.parseDouble(latLng.split(",")[1])));
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


    public com.google.android.gms.maps.model.LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = new LatLng((Double.parseDouble(latLng.split(",")[0])),(Double.parseDouble(latLng.split(",")[1])));
    }
}
