package com.udecar;
/*
Clase que permite llenar la lista dinamica de automoviles
 */
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
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
import com.udecar.Datos.Automovil;
import com.udecar.Datos.AutomovilesModificados;
import com.udecar.Datos.Frenos;
import com.udecar.Datos.Llantas;
import com.udecar.Datos.Motor;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
    private ArrayList<Automovil> listaAutos;
    private ArrayList<Motor> arrayMotores = new ArrayList<>();
    private ArrayList<Llantas> arrayLlantas = new ArrayList<>();
    private ArrayList<Frenos> arrayFrenos= new ArrayList<>();
    private Context context;

    private Motor motor = new Motor();
    private Llantas llantas = new Llantas();
    private Frenos frenos = new Frenos();

    private Bundle datosAEnviar = new Bundle();
    Fragment fragmento = new ModificarAutos();

    public Adaptador(Context context, ArrayList<Automovil> listaAutos) {
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
        // OBTENER EL OBJETO POR CADA ITEM A MOSTRAR
        final Automovil automovil = (Automovil) getItem(position);
        // CREAMOS E INICIALIZAMOS LOS ELEMENTOS DEL ITEM DE LA LISTA
        convertView = LayoutInflater.from(context).inflate(R.layout.lista_carros, null);
        ImageView img_Auto = (ImageView) convertView.findViewById(R.id.img_Auto);
        TextView tv_NombreAuto = (TextView) convertView.findViewById(R.id.tv_NombreAuto);
        TextView tv_InfoAuto = (TextView) convertView.findViewById(R.id.tv_InfoAuto);
        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        String informacion= "Nombre: " + automovil.getNombreAutomovil() + "\n" +
                            "Categoria: " + automovil.getDescripcion() + "\n";

        //img_Auto.setImageResource(automovil.getImagenAutomovil());
        tv_NombreAuto.setText(automovil.getNombreAutomovil());
        tv_InfoAuto.setText(informacion);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((motor != null) && (automovil != null) && (llantas != null)) {
                    for (int i=0;i<arrayMotores.size();i++) {
                        if (automovil.getNombreMotor().equals(arrayMotores.get(i).getNombreMotor())) {
                            motor.setNombreMotor(arrayMotores.get(i).getNombreMotor());
                            motor.setPotencia(arrayMotores.get(i).getPotencia());
                            motor.setTipoBujia(arrayMotores.get(i).getTipoBujia());
                            motor.setTipoFiltro(arrayMotores.get(i).getTipoFiltro());
                            motor.setDescripcionMotor(arrayMotores.get(i).getDescripcionMotor());
                        }
                    }

                    for (int i=0;i<arrayLlantas.size();i++) {
                        if (automovil.getNombreLlantas().equals(arrayLlantas.get(i).getNombreLlantas())) {
                            llantas.setNombreLlantas(arrayLlantas.get(i).getNombreLlantas());
                            llantas.setTipoLlanta(arrayLlantas.get(i).getTipoLlanta());
                            llantas.setAgarreLlanta(arrayLlantas.get(i).getAgarreLlanta());
                            llantas.setDescripcionllantas(arrayLlantas.get(i).getDescripcionllantas());
                        }
                    }

                    for (int i=0;i<arrayFrenos.size();i++) {
                        if (automovil.getNombreFrenos().equals(arrayFrenos.get(i).getNombreFrenos())) {
                            frenos.setNombreFrenos(arrayFrenos.get(i).getNombreFrenos());
                            frenos.setTipoValvulas(arrayFrenos.get(i).getTipoValvulas());
                            frenos.setFrenado(arrayFrenos.get(i).getFrenado());
                            frenos.setDescripcionFrenos(arrayFrenos.get(i).getDescripcionFrenos());
                        }
                    }

                    AutomovilesModificados autoModificado = new AutomovilesModificados();

                    autoModificado.setCategoriaM(automovil.getCategoria());
                    autoModificado.setDescripcionM(automovil.getDescripcion());
                    autoModificado.setNombreAutomovilM(automovil.getNombreAutomovil());
                    autoModificado.setNombreLlantasM(automovil.getNombreLlantas());
                    autoModificado.setNombreFrenosM(automovil.getNombreFrenos());
                    autoModificado.setImagenAutomovilM(automovil.getImagenAutomovil());
                    autoModificado.setAgarreM(automovil.getAgarre());

                    autoModificado.setNombreMotorM(motor.getNombreMotor());
                    autoModificado.setPotenciaM(motor.getPotencia());
                    autoModificado.setTipoBujiaM(motor.getTipoBujia());
                    autoModificado.setTipoFiltroM(motor.getTipoFiltro());
                    autoModificado.setDescripcionMotorM(motor.getDescripcionMotor());

                    autoModificado.setDescripcionFrenosM(frenos.getDescripcionFrenos());
                    autoModificado.setTipoValvulasM(frenos.getTipoValvulas());
                    autoModificado.setFrenadoM(frenos.getFrenado());

                    autoModificado.setTipoLlantaM(llantas.getTipoLlanta());
                    autoModificado.setDescripcionLlantasM(llantas.getDescripcionllantas());

                    datosAEnviar.putSerializable("autoMod", autoModificado);

                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager;
                    fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_fragment, fragmento);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    System.out.println("vacios");
                }
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
                        motores.setNombreMotor(templateSnapshot.getKey());
                        motores.setPotencia(Float.parseFloat(templateSnapshot.child("potencia").getValue().toString()));
                        motores.setTipoBujia(templateSnapshot.child("tipoBujia").getValue().toString());
                        motores.setTipoFiltro(templateSnapshot.child("tipoFiltro").getValue().toString());
                        motores.setDescripcionMotor(templateSnapshot.child("descripcion").getValue().toString());
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

