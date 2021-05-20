package com.udecar.Datos;

public class Bujia {
    private float potencia;
    private String tipoBujia;
    private String descripcionBujia;

    public Bujia() {
    }

    public Bujia (float potencia, String tipoBujia, String descripcionBujia) {
        this.potencia = potencia;
        this.tipoBujia = tipoBujia;
        this.descripcionBujia = descripcionBujia;
    }

    public float getPotencia() {
        return potencia;
    }

    public void setPotencia(float potencia) {
        this.potencia = potencia;
    }

    public String getTipoBujia() {
        return tipoBujia;
    }

    public void setTipoBujia(String tipoBujia) {
        this.tipoBujia = tipoBujia;
    }

    public String getdescripcionBujia () {
        return descripcionBujia;
    }

    public void setdescripcionBujia (String descripcionBujia) {
        this.descripcionBujia = descripcionBujia;
    }
}
