package com.udecar.Datos;
import java.io.Serializable;

public class Automovil implements Serializable{
    private String categoria;
    private String descripcion;
    private String nombreAutomovil;
    private String nombreFrenos;
    private String nombreMotor;
    private String nombreLlantas;
    private int imagenAutomovil;//cambiar para usar BD
    private float agarre;

    public Automovil(){
    }

    public Automovil (String categoria, String descripcion, String nombreAutomovil, String nombreFrenos, String nombreMotor, String nombreLlantas, int imagenAutomovil, float agarre) {
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.nombreAutomovil = nombreAutomovil;
        this.nombreFrenos = nombreFrenos;
        this.nombreMotor = nombreMotor;
        this.nombreLlantas = nombreLlantas;
        this.imagenAutomovil = imagenAutomovil;
        this.agarre = agarre ;
    }

    public String getNombreAutomovil() {
        return nombreAutomovil;
    }

    public void setNombreAutomovil(String nombreAutomovil) {
        this.nombreAutomovil = nombreAutomovil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreMotor() {
        return nombreMotor;
    }

    public void setNombreMotor(String nombreMotor) {
        this.nombreMotor = nombreMotor;
    }

    public String getNombreFrenos() {
        return nombreFrenos;
    }

    public void setNombreFrenos(String nombreFrenos) {
        this.nombreFrenos = nombreFrenos;
    }

    public String getNombreLlantas () {
        return nombreLlantas;
    }

    public void setNombreLlantas (String nombreLlantas) {
        this.nombreLlantas = nombreLlantas;
    }

    public float getAgarre () {
        return agarre;
    }

    public void setAgarre (float agarre) {
        this.agarre = agarre;
    }

    public int getImagenAutomovil () {
        return imagenAutomovil;
    }

    public void setImagenAutomovil (int imagenAutomovil) {
        this.imagenAutomovil = imagenAutomovil;
    }
}
