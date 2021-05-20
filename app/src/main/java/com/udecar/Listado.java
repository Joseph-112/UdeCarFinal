package com.udecar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Automovil;

import java.util.ArrayList;

public class Listado extends Fragment {
    private ListView l_Autos;
    private AdaptadorLista adaptadorLista;
    private ArrayList<Automovil> listaAutos = new ArrayList<>();
    private DatabaseReference dataBase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado,container,false);

        dataBase = FirebaseDatabase.getInstance().getReference();
        l_Autos = view.findViewById(R.id.lv_autos);

        listarDatos();

        return view;
    }

    //Conexión con Realtime Database
    private void listarDatos() {
        dataBase.child("Automoviles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot auto : snapshot.getChildren()){
                        Automovil nuevoAuto = new Automovil();
                        nuevoAuto.setNombreAutomovil(auto.getKey());
                        nuevoAuto.setNombreLlantas((auto.child("nombreLlantas").getValue().toString()));
                        nuevoAuto.setNombreMotor(auto.child("nombreMotor").getValue().toString());
                        nuevoAuto.setDescripcion(auto.child("descripcion").getValue().toString());
                        nuevoAuto.setNombreFrenos(auto.child("nombreFrenos").getValue().toString());
                        nuevoAuto.setImagenAutomovil(Integer.parseInt(auto.child("imagenAutomovil").getValue().toString()));
                        nuevoAuto.setCategoria(auto.child("categoria").getValue().toString());
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
}