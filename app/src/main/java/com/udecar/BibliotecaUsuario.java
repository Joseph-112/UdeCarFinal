package com.udecar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Automovil;
import com.udecar.Datos.AutomovilesModificados;
import com.udecar.Datos.imagenAutos;
import com.udecar.interfaz.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BibliotecaUsuario extends Fragment {
    private FirebaseAuth firebaseAuth;
    private ListView l_Autos;
    private AdaptadorModificados adaptadorLista;
    private ArrayList<AutomovilesModificados> listaAutos = new ArrayList<>();
    private ArrayList<imagenAutos> urlImagenes = new ArrayList<>();
    private DatabaseReference dataBase;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biblioteca_usuario, container, false);

        dataBase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        l_Autos = view.findViewById(R.id.lv_autosUser);
        getImage();
        listarDatos();
        return view;
    }

    //Conexión con Realtime Database
    private void listarDatos () {
        listaAutos.clear();
        dataBase.child("AutosModificados").child("Modificados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot auto : snapshot.getChildren()) {
                        AutomovilesModificados autoModificado = new AutomovilesModificados();
                        autoModificado.setIdUser(auto.child("idUser").getValue().toString());
                        autoModificado.setCategoriaM(auto.child("categoriaM").getValue().toString());
                        autoModificado.setDescripcionM(auto.child("descripcionM").getValue().toString());
                        autoModificado.setNombreAutomovilM(auto.child("nombreAutomovilM").getValue().toString());
                        autoModificado.setNombreLlantasM(auto.child("nombreLlantasM").getValue().toString());
                        autoModificado.setNombreFrenosM(auto.child("nombreFrenosM").getValue().toString());
                        autoModificado.setImagenAutomovilM(auto.child("imagenAutomovilM").getValue().toString());
                        autoModificado.setAgarreM(Float.parseFloat(auto.child("agarreM").getValue().toString()));

                        autoModificado.setNombreMotorM(auto.child("nombreMotorM").getValue().toString());
                        autoModificado.setPotenciaM(Float.parseFloat(auto.child("potenciaM").getValue().toString()));
                        autoModificado.setTipoBujiaM(auto.child("tipoBujiaM").getValue().toString());
                        autoModificado.setTipoFiltroM(auto.child("tipoFiltroM").getValue().toString());
                        autoModificado.setDescripcionMotorM(auto.child("descripcionMotorM").getValue().toString());

                        autoModificado.setDescripcionFrenosM(auto.child("descripcionFrenosM").getValue().toString());
                        autoModificado.setTipoValvulasM(auto.child("tipoValvulasM").getValue().toString());
                        autoModificado.setFrenadoM(Float.parseFloat(auto.child("frenadoM").getValue().toString()));

                        autoModificado.setTipoLlantaM(auto.child("tipoLlantaM").getValue().toString());
                        autoModificado.setDescripcionLlantasM(auto.child("descripcionLlantasM").getValue().toString());
                        autoModificado.setIdAuto(Integer.parseInt(auto.child("idAuto").getValue().toString()));

                        if(autoModificado.getIdUser().equals(firebaseAuth.getUid())){
                            for (imagenAutos iterador : urlImagenes) {
                                if (iterador.getNombreAutomovil().equals(autoModificado.getNombreAutomovilM())) {
                                    autoModificado.setImagenAutomovilM(iterador.getImagenAutomovil());
                                }
                            }
                            listaAutos.add(autoModificado);
                        }
                    }
                    adaptadorLista = new AdaptadorModificados(getContext(), listaAutos);
                    l_Autos.setAdapter(adaptadorLista);
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {
            }
        });

    }

    private void getImage () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/Joseph-112/imagenesUdeCar/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<imagenAutos>> call = jsonPlaceHolderApi.getImages();
        call.enqueue(new Callback<List<imagenAutos>>() {
            @Override
            public void onResponse (Call<List<imagenAutos>> call, Response<List<imagenAutos>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<imagenAutos> postList = response.body();
                for (imagenAutos datosImagen : postList) {
                    urlImagenes.add(datosImagen);
                }
            }

            @Override
            public void onFailure (Call<List<imagenAutos>> call, Throwable t) {
                //mJasonTextView.setText(t.getMessage());
            }
        });
    }
}