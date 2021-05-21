package com.udecar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Automovil;
import com.udecar.Datos.imagenAutos;
import com.udecar.interfaz.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Listado extends Fragment {
    private ListView l_Autos;
    private AdaptadorLista adaptadorLista;
    private ArrayList<Automovil> listaAutos = new ArrayList<>();
    private ArrayList<imagenAutos> urlImagenes = new ArrayList<>();
    private DatabaseReference dataBase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado,container,false);
        dataBase = FirebaseDatabase.getInstance().getReference();
        l_Autos = view.findViewById(R.id.lv_autos);
        getImage();
        listarDatos();
        return view;
    }
    //Conexión con Realtime Database
    private void listarDatos() {
        listaAutos.clear();
        dataBase.child("Automoviles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot auto : snapshot.getChildren()){
                        Automovil nuevoAuto = new Automovil();
                        nuevoAuto.setNombreAutomovil(auto.child("nombreAutomovil").getValue().toString());
                        nuevoAuto.setNombreLlantas((auto.child("nombreLlantas").getValue().toString()));
                        nuevoAuto.setNombreMotor(auto.child("nombreMotor").getValue().toString());
                        nuevoAuto.setDescripcion(auto.child("descripcion").getValue().toString());
                        nuevoAuto.setNombreFrenos(auto.child("nombreFrenos").getValue().toString());
                        nuevoAuto.setCategoria(auto.child("categoria").getValue().toString());
                        for(imagenAutos iterador:urlImagenes){
                            if (iterador.getNombreAutomovil().equals(nuevoAuto.getNombreAutomovil())){
                                nuevoAuto.setImagenAutomovil(iterador.getImagenAutomovil());
                            }
                        }
                        listaAutos.add(nuevoAuto);
                    }
                    adaptadorLista = new AdaptadorLista(getContext(), listaAutos);
                    l_Autos.setAdapter(adaptadorLista);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private void getImage(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/Joseph-112/imagenesUdeCar/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<imagenAutos>> call = jsonPlaceHolderApi.getImages();
        call.enqueue(new Callback<List<imagenAutos>>() {
            @Override
            public void onResponse(Call<List<imagenAutos>> call, Response<List<imagenAutos>> response) {
                if (!response.isSuccessful()){
                    return;
                }
                List<imagenAutos> postList = response.body();
                for (imagenAutos datosImagen : postList){
                    urlImagenes.add(datosImagen);
                }
            }
            @Override
            public void onFailure(Call<List<imagenAutos>> call, Throwable t) {
                //mJasonTextView.setText(t.getMessage());
            }
        });
    }
}