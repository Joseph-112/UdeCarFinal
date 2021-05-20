package com.udecar.Datos;
import java.io.Serializable;

public class AutomovilesModificados implements Serializable {
    private String categoriaM;
    private String descripcionM;
    private String nombreAutomovilM;
    private String nombreFrenosM;
    private String nombreMotorM;
    private String nombreLlantasM;
    private int imagenAutomovilM;//cambiar para usar BD
    private float agarreM;
    //motor
    private String descripcionMotorM;
    private String tipoBujiaM;
    private String tipoFiltroM;
    private float potenciaM;
    //frenos
    private String descripcionFrenosM;
    private String tipoValvulasM;
    private float frenadoM;
    //lantas
    private String tipoLlantaM;
    private String descripcionLlantasM;

    public AutomovilesModificados()  {
    }

    public AutomovilesModificados (String categoriaM, String descripcionM, String nombreAutomovilM, String nombreFrenosM, String nombreMotorM, String nombreLlantasM, int imagenAutomovilM, float agarreM, String descripcionMotorM, String tipoBujiaM, String tipoFiltroM, float potenciaM, String descripcionFrenosM, String tipoValvulasM, float frenadoM, String tipoLlantaM, String descripcionLlantasM) {
        this.categoriaM = categoriaM;
        this.descripcionM = descripcionM;
        this.nombreAutomovilM = nombreAutomovilM;
        this.nombreFrenosM = nombreFrenosM;
        this.nombreMotorM = nombreMotorM;
        this.nombreLlantasM = nombreLlantasM;
        this.imagenAutomovilM = imagenAutomovilM;
        this.agarreM = agarreM;
        this.descripcionMotorM = descripcionMotorM;
        this.tipoBujiaM = tipoBujiaM;
        this.tipoFiltroM = tipoFiltroM;
        this.potenciaM = potenciaM;
        this.descripcionFrenosM = descripcionFrenosM;
        this.tipoValvulasM = tipoValvulasM;
        this.frenadoM = frenadoM;
        this.tipoLlantaM = tipoLlantaM;
        this.descripcionLlantasM = descripcionLlantasM;
    }

    public String getCategoriaM () {
        return categoriaM;
    }

    public void setCategoriaM (String categoriaM) {
        this.categoriaM = categoriaM;
    }

    public String getDescripcionM () {
        return descripcionM;
    }

    public void setDescripcionM (String descripcionM) {
        this.descripcionM = descripcionM;
    }

    public String getNombreAutomovilM () {
        return nombreAutomovilM;
    }

    public void setNombreAutomovilM (String nombreAutomovilM) {
        this.nombreAutomovilM = nombreAutomovilM;
    }

    public String getNombreFrenosM () {
        return nombreFrenosM;
    }

    public void setNombreFrenosM (String nombreFrenosM) {
        this.nombreFrenosM = nombreFrenosM;
    }

    public String getNombreMotorM () {
        return nombreMotorM;
    }

    public void setNombreMotorM (String nombreMotorM) {
        this.nombreMotorM = nombreMotorM;
    }

    public String getNombreLlantasM () {
        return nombreLlantasM;
    }

    public void setNombreLlantasM (String nombreLlantasM) {
        this.nombreLlantasM = nombreLlantasM;
    }

    public int getImagenAutomovilM () {
        return imagenAutomovilM;
    }

    public void setImagenAutomovilM (int imagenAutomovilM) {
        this.imagenAutomovilM = imagenAutomovilM;
    }

    public float getAgarreM () {
        return agarreM;
    }

    public void setAgarreM (float agarreM) {
        this.agarreM = agarreM;
    }

    public String getDescripcionMotorM () {
        return descripcionMotorM;
    }

    public void setDescripcionMotorM (String descripcionMotorM) {
        this.descripcionMotorM = descripcionMotorM;
    }

    public String getTipoBujiaM () {
        return tipoBujiaM;
    }

    public void setTipoBujiaM (String tipoBujiaM) {
        this.tipoBujiaM = tipoBujiaM;
    }

    public String getTipoFiltroM () {
        return tipoFiltroM;
    }

    public void setTipoFiltroM (String tipoFiltroM) {
        this.tipoFiltroM = tipoFiltroM;
    }

    public float getPotenciaM () {
        return potenciaM;
    }

    public void setPotenciaM (float potenciaM) {
        this.potenciaM = potenciaM;
    }

    public String getDescripcionFrenosM () {
        return descripcionFrenosM;
    }

    public void setDescripcionFrenosM (String descripcionFrenosM) {
        this.descripcionFrenosM = descripcionFrenosM;
    }

    public String getTipoValvulasM () {
        return tipoValvulasM;
    }

    public void setTipoValvulasM (String tipoValvulasM) {
        this.tipoValvulasM = tipoValvulasM;
    }

    public float getFrenadoM () {
        return frenadoM;
    }

    public void setFrenadoM (float frenadoM) {
        this.frenadoM = frenadoM;
    }

    public String getTipoLlantaM () {
        return tipoLlantaM;
    }

    public void setTipoLlantaM (String tipoLlantaM) {
        this.tipoLlantaM = tipoLlantaM;
    }

    public String getDescripcionLlantasM () {
        return descripcionLlantasM;
    }

    public void setDescripcionLlantasM (String descripcionLlantasM) {
        this.descripcionLlantasM = descripcionLlantasM;
    }
}
