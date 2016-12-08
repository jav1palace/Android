package com.example.javierpalaciocuenca.app.persistence.model;

/**
 * Created by javierpalaciocuenca on 08/12/2016.
 */

public class BusStop {

    private int id, orden, idlinea, idtrayecto;
    private double utmx, utmy;

    public BusStop() {
    }

    public BusStop(int id, int orden, int idlinea, int idtrayecto, double utmx, double utmy) {
        this.id = id;
        this.orden = orden;
        this.idlinea = idlinea;
        this.idtrayecto = idtrayecto;
        this.utmx = utmx;
        this.utmy = utmy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getIdlinea() {
        return idlinea;
    }

    public void setIdlinea(int idlinea) {
        this.idlinea = idlinea;
    }

    public int getIdtrayecto() {
        return idtrayecto;
    }

    public void setIdtrayecto(int idtrayecto) {
        this.idtrayecto = idtrayecto;
    }

    public double getUtmy() {
        return utmy;
    }

    public void setUtmy(double utmy) {
        this.utmy = utmy;
    }

    public double getUtmx() {
        return utmx;
    }

    public void setUtmx(double utmx) {
        this.utmx = utmx;
    }
}


