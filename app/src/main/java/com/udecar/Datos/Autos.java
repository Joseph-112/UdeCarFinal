package com.udecar.Datos;

public class Autos {
    public String idAuto;
    public String nombreAuto;
    public String tipoFreno;
    public String tipoMotor;

    public Autos() {
    }

    public String getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(String idAuto) {
        this.idAuto = idAuto;
    }

    public String getNombreAuto() {
        return nombreAuto;
    }

    public void setNombreAuto(String nombreAuto) {
        this.nombreAuto = nombreAuto;
    }

    public String getTipoFreno() {
        return tipoFreno;
    }

    public void setTipoFreno(String tipoFreno) {
        this.tipoFreno = tipoFreno;
    }

    public String getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    @Override
    public String toString() {
        return nombreAuto;
    }
}
