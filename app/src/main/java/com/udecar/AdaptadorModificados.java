package com.udecar;
/*
Clase que permite llenar la lista dinamica de automoviles
 */

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.udecar.Datos.Automovil;
import com.udecar.Datos.AutomovilesModificados;
import com.udecar.Datos.Frenos;
import com.udecar.Datos.Llantas;
import com.udecar.Datos.Motor;
import com.udecar.Datos.imagenAutos;
import com.udecar.interfaz.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdaptadorModificados extends BaseAdapter {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
    private ArrayList<AutomovilesModificados> listaAutos;
    private ArrayList<Motor> arrayMotores = new ArrayList<>();
    private ArrayList<Llantas> arrayLlantas = new ArrayList<>();
    private ArrayList<Frenos> arrayFrenos= new ArrayList<>();
    private Context context;
    private ImageView img_Auto;

    private TextView mJasonTextView;

    private Motor motor = new Motor();
    private Llantas llantas = new Llantas();
    private Frenos frenos = new Frenos();

    private Bundle datosAEnviar = new Bundle();
    Fragment fragmento = new ModificarAutos();



    public AdaptadorModificados (Context context, ArrayList<AutomovilesModificados> listaAutos) {
        this.context = context;
        this.listaAutos = listaAutos;
        listarDatos();
    }

    @Override
    public int getCount() {
        return listaAutos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaAutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Gson gson = new GsonBuilder().setLenient().create();
        //mJasonTextView = findViewById(R.id.jsonText);
        String imagen;
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        AutomovilesModificados automovil = (AutomovilesModificados) getItem(position);
        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.lista_carros, null);
        img_Auto = (ImageView) convertView.findViewById(R.id.img_Auto);
        Picasso.get().load(automovil.getImagenAutomovilM()).into(img_Auto);
        TextView tv_NombreAuto = (TextView) convertView.findViewById(R.id.tv_NombreAuto);
        TextView tv_InfoAuto = (TextView) convertView.findViewById(R.id.tv_InfoAuto);
        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        String informacion= "Categoria: " + automovil.getCategoriaM() + "\n";

        tv_NombreAuto.setText(automovil.getNombreAutomovilM());
        tv_InfoAuto.setText(informacion);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    datosAEnviar.putSerializable("autoMod", automovil);
                    datosAEnviar.putString("estado", "creado");

                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager;
                    fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_user, fragmento);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
            }
        });
        return convertView;
    }

    public void listarDatos() {
        dataBase.child("Motor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot templateSnapshot : snapshot.getChildren()){
                        Motor motores = new Motor();
                        motores.setNombreMotor(templateSnapshot.child("nombreMotor").getValue().toString());
                        motores.setPotencia(Float.parseFloat(templateSnapshot.child("potencia").getValue().toString()));
                        motores.setTipoBujia(templateSnapshot.child("tipoBujia").getValue().toString());
                        motores.setTipoFiltro(templateSnapshot.child("tipoFiltro").getValue().toString());
                        motores.setDescripcionMotor(templateSnapshot.child("descripcionMotor").getValue().toString());
                        arrayMotores.add(motores);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        dataBase.child("Llanta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot templateSnapshot : snapshot.getChildren()){
                        Llantas llanta = new Llantas();
                        llanta.setNombreLlantas(templateSnapshot.child("nombreLlanta").getValue().toString());
                        llanta.setTipoLlanta(templateSnapshot.child("tipoLlanta").getValue().toString());
                        llanta.setDescripcionllantas(templateSnapshot.child("descripcion").getValue().toString());
                        llanta.setAgarreLlanta(Float.parseFloat(templateSnapshot.child("agarre").getValue().toString()));
                        arrayLlantas.add(llanta);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        dataBase.child("Frenos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot templateSnapshot : snapshot.getChildren()){
                        Frenos freno = new Frenos();
                        freno.setNombreFrenos(templateSnapshot.getKey());
                        freno.setDescripcionFrenos(templateSnapshot.child("descripcion").getValue().toString());
                        freno.setTipoValvulas(templateSnapshot.child("tipoValvulas").getValue().toString());
                        freno.setFrenado(Float.parseFloat(templateSnapshot.child("frenado").getValue().toString()));
                        arrayFrenos.add(freno);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}

