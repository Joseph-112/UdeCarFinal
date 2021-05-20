package com.udecar.Datos;

import java.io.Serializable;

public class Llantas implements Serializable {
    private String nombreLlantas;
    private String descripcionllantas;
    private String tipoLlanta;
    private float agarreLlanta;

    public Llantas () {
    }

    public Llantas (String nombreLlantas, String descripcionllantas, float agarreLlanta, String tipoLlanta) {
        this.nombreLlantas = nombreLlantas;
        this.descripcionllantas = descripcionllantas;
        this.agarreLlanta = agarreLlanta;
        this.tipoLlanta = tipoLlanta;
    }

    public String getNombreLlantas () {
        return nombreLlantas;
    }

    public void setNombreLlantas (String nombreLlantas) {
        this.nombreLlantas = nombreLlantas;
    }

    public String getDescripcionllantas () {
        return descripcionllantas;
    }

    public void setDescripcionllantas (String descripcionllantas) {
        this.descripcionllantas = descripcionllantas;
    }

    public float getAgarreLlanta () {
        return agarreLlanta;
    }

    public void setAgarreLlanta (float agarreLlanta) {
        this.agarreLlanta = agarreLlanta;
    }

    public String getTipoLlanta () {
        return tipoLlanta;
    }

    public void setTipoLlanta (String tipoLlanta) {
        this.tipoLlanta = tipoLlanta;
    }

}
