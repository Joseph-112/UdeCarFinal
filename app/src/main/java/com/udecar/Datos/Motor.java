package com.udecar.Datos;

import java.io.Serializable;

public class Motor implements Serializable {
    private String nombreMotor;
    private String descripcionMotor;
    private String tipoBujia;
    private String tipoFiltro;
    private float potencia;
    private float cilindraje;

    public Motor(){
    }

    public Motor(String nombreMotor, String descripcionMotor, String tipoBujia, String tipoFiltro, float potencia, float cilindraje) {
        this.nombreMotor = nombreMotor;
        this.descripcionMotor = descripcionMotor;
        this.tipoBujia = tipoBujia;
        this.tipoFiltro = tipoFiltro;
        this.potencia = potencia;
        this.cilindraje = cilindraje;
    }

    public float getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(float cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getNombreMotor () {
        return nombreMotor;
    }

    public void setNombreMotor (String nombreMotor) {
        this.nombreMotor = nombreMotor;
    }

    public String getDescripcionMotor () {
        return descripcionMotor;
    }

    public void setDescripcionMotor (String descripcionMotor) {
        this.descripcionMotor = descripcionMotor;
    }

    public String getTipoBujia () {
        return tipoBujia;
    }

    public void setTipoBujia (String tipoBujia) {
        this.tipoBujia = tipoBujia;
    }

    public String getTipoFiltro () {
        return tipoFiltro;
    }

    public void setTipoFiltro (String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }

    public float getPotencia () {
        return potencia;
    }

    public void setPotencia (float potencia) {
        this.potencia = potencia;
    }
}
