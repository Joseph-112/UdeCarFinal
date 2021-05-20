package com.udecar.Datos;

import java.io.Serializable;

public class Frenos implements Serializable{
    private String nombreFrenos;
    private String descripcionFrenos;
    private String tipoValvulas;
    private float frenado;

    public Frenos(){

    }

    public Frenos (String nombreFrenos, String descripcionFrenos, String tipoValvulas, float frenado) {
        this.nombreFrenos = nombreFrenos;
        this.descripcionFrenos = descripcionFrenos;
        this.tipoValvulas = tipoValvulas;
        this.frenado = frenado;
    }

    public String getNombreFrenos () {
        return nombreFrenos;
    }

    public void setNombreFrenos (String nombreFrenos) {
        this.nombreFrenos = nombreFrenos;
    }

    public String getDescripcionFrenos () {
        return descripcionFrenos;
    }

    public void setDescripcionFrenos (String descripcionFrenos) {
        this.descripcionFrenos = descripcionFrenos;
    }

    public String getTipoValvulas () {
        return tipoValvulas;
    }

    public void setTipoValvulas (String tipoValvulas) {
        this.tipoValvulas = tipoValvulas;
    }

    public float getFrenado () {
        return frenado;
    }

    public void setFrenado (float frenado) {
        this.frenado = frenado;
    }
}
