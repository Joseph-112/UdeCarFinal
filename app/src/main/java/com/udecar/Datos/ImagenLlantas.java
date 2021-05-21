package com.udecar.Datos;

public class ImagenLlantas {
    private String nombreLlantas , imagenLlantas;
    public ImagenLlantas(String nombreLlantas, String imagenLlantas) {
        this.nombreLlantas = nombreLlantas;
        this.imagenLlantas = imagenLlantas;

    }
    public ImagenLlantas() {
    }
    public String getNombreLlantas() {
        return nombreLlantas;
    }
    public void setNombreLlantas(String nombreLlantas) {
        this.nombreLlantas = nombreLlantas;
    }
    public String getImagenLlantas() {
        return imagenLlantas;
    }
    public void setImagenLlantas(String imagenLlantas) {
        this.imagenLlantas = imagenLlantas;
    }
}