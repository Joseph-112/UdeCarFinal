package com.udecar.Datos;

public class Valvulas {
    private float frenado;
    private String tipoValvula;
    private String descripcionValvula;

    public Valvulas () {
    }

    public Valvulas (float frenado, String tipoValvula, String descripcionValvula) {
        this.frenado = frenado;
        this.tipoValvula = tipoValvula;
        this.descripcionValvula = descripcionValvula;
    }

    public float getFrenado () {
        return frenado;
    }

    public void setFrenado (float frenado) {
        this.frenado = frenado;
    }

    public String getTipoValvula () {
        return tipoValvula;
    }

    public void setTipoValvula (String tipoValvula) {
        this.tipoValvula = tipoValvula;
    }

    public String getdescripcionValvula () {
        return descripcionValvula;
    }

    public void setdescripcionValvula (String descripcionValvula) {
        this.descripcionValvula = descripcionValvula;
    }
}
