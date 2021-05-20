package com.udecar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Automovil;
import com.udecar.Datos.Motor;
import com.udecar.Datos.imagenAutos;

import java.util.ArrayList;

public class FragmentCrearMotor extends Fragment {

    private DatabaseReference dataBase;
    private TextView nombreMotor, cilindraje, potencia, descripcion;
    private Spinner bujias, filtros;
    private Button guardar;
    private ListView lv_Motores;
    private ArrayList<Motor> listaMotores = new ArrayList<>();
    private Adaptador adaptador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_crear_motor, container, false);
        dataBase = FirebaseDatabase.getInstance().getReference();

        lv_Motores = view.findViewById(R.id.lista_motores);
        nombreMotor = view.findViewById(R.id.txtNombreMotor);
        cilindraje = view.findViewById(R.id.txtCilindraje);
        potencia = view.findViewById(R.id.txtPotenciaMotor);
        descripcion = view.findViewById(R.id.txtDescripcion);
        guardar = view.findViewById(R.id.bnCrear);
        bujias = view.findViewById(R.id.sp_Bujia);
        filtros = view.findViewById(R.id.sp_Filtro);
        //getImage();
        listarDatos();

        return view;
    }

    private void listarDatos() {
        listaMotores.clear();
        dataBase.child("Motor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot motorDB : snapshot.getChildren()){
                        Motor motor = new Motor();
                        motor.setNombreMotor(motorDB.getKey());
                        motor.setDescripcionMotor(motorDB.child("descripcion").getValue().toString());
                        motor.setPotencia(Float.parseFloat(motorDB.child("potencia").getValue().toString()));
                        motor.setCilindraje(Float.parseFloat(motorDB.child("cilindraje").getValue().toString()));
                        motor.setTipoBujia(motorDB.child("tipoBujia").getValue().toString());
                        motor.setTipoFiltro(motorDB.child("tipoFiltro").getValue().toString());

                        /*for(imagenAutos iterador:urlImagenes){
                            if (iterador.getNombreAutomovil().equals(nuevoAuto.getNombreAutomovil())){
                                nuevoAuto.setImagenAutomovil(iterador.getImagenAutomovil());
                            }
                        }*/

                        listaMotores.add(motor);

                    }

                    //lv_Motores.setAdapter(listaMotores);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
