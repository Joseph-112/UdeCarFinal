package com.udecar.interfaz;

import com.udecar.Datos.ImagenLlantas;
import com.udecar.Datos.imagenAutos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("Imagenes")
    Call<List<imagenAutos>> getImages();

    @GET("Llantas")
    Call<List<ImagenLlantas>> getImagesLlantas();

}
