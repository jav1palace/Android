package com.example.javierpalaciocuenca.app.persistence.model;

import android.os.Parcelable;

/**
 * Created by javierpalaciocuenca on 08/12/2016.
 */

public class BusStop {
    private int id, orden, idLinea, idTrayecto;
    private double utmx, utmy;

    public BusStop() {
    }

    public BusStop(int id, int orden, int idlinea, int idtrayecto, double utmx, double utmy) {
        this.id = id;
        this.orden = orden;
        this.idLinea = idlinea;
        this.idTrayecto = idtrayecto;
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

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdTrayecto() {
        return idTrayecto;
    }

    public void setIdTrayecto(int idTrayecto) {
        this.idTrayecto = idTrayecto;
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



