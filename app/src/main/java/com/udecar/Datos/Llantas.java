package com.udecar.Datos;

import java.io.Serializable;

public class Llantas implements Serializable {
    private String nombreLlanta;
    private String descripcion;
    private String tipoLlanta;
    private float agarre;

    public Llantas () {
    }

    public Llantas (String nombreLlanta, String descripcion, float agarre, String tipoLlanta) {
        this.nombreLlanta = nombreLlanta;
        this.descripcion = descripcion;
        this.agarre = agarre;
        this.tipoLlanta = tipoLlanta;
    }

    public String getNombreLlanta () {
        return nombreLlanta;
    }

    public void setNombreLlanta (String nombreLlanta) {
        this.nombreLlanta = nombreLlanta;
    }

    public String getDescripcion () {
        return descripcion;
    }

    public void setDescripcion (String descripcion) {
        this.descripcion = descripcion;
    }

    public float getAgarre () {
        return agarre;
    }

    public void setAgarre (float agarre) {
        this.agarre = agarre;
    }

    public String getTipoLlanta () {
        return tipoLlanta;
    }

    public void setTipoLlanta (String tipoLlanta) {
        this.tipoLlanta = tipoLlanta;
    }

}
