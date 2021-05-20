package com.udecar.Datos;

public class Filtro {
    private float potencia;
    private String tipofiltro;
    private String descripcionFiltro;

    public Filtro () {
    }

    public Filtro (float potencia, String tipofiltro, String descripcionFiltro) {
        this.potencia = potencia;
        this.tipofiltro = tipofiltro;
        this.descripcionFiltro = descripcionFiltro;
    }

    public float getPotencia () {
        return potencia;
    }

    public void setPotencia (float potencia) {
        this.potencia = potencia;
    }

    public String getTipofiltro () {
        return tipofiltro;
    }

    public void setTipofiltro (String tipofiltro) {
        this.tipofiltro = tipofiltro;
    }

    public String getdescripcionFiltro () {
        return descripcionFiltro;
    }

    public void setdescripcionFiltro (String descripcionFiltro) {
        this.descripcionFiltro = descripcionFiltro;
    }
}
