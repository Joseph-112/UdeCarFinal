package com.udecar.Datos;

public class Filtro {
    private float potencia;
    private String tipoFiltro;
    private String descripcion;

    public Filtro () {
    }

    public Filtro (float potencia, String tipofiltro, String descripcionFiltro) {
        this.potencia = potencia;
        this.tipoFiltro = tipofiltro;
        this.descripcion = descripcionFiltro;
    }

    public float getPotencia () {
        return potencia;
    }

    public void setPotencia (float potencia) {
        this.potencia = potencia;
    }

    public String getTipofiltro () {
        return tipoFiltro;
    }

    public void setTipofiltro (String tipofiltro) {
        this.tipoFiltro = tipofiltro;
    }

    public String getdescripcionFiltro () {
        return descripcion;
    }

    public void setdescripcionFiltro (String descripcionFiltro) {
        this.descripcion = descripcionFiltro;
    }
}
