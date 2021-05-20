package com.udecar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Automovil;
import com.udecar.Datos.Motor;

import java.util.ArrayList;

public class AdaptadorLista extends BaseAdapter {

    private ArrayList<Automovil> listaAutos;
    private Context context;
    private LayoutInflater inflater;
    //Fragment fragmento = new ModificarAutos();

    public AdaptadorLista(Context context, ArrayList<Automovil> listaAutos) {
        this.context = context;
        this.listaAutos = listaAutos;
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
        ImageView img_Auto= (ImageView) convertView.findViewById(R.id.img_Auto);
        TextView tv_NombreAuto = (TextView) convertView.findViewById(R.id.tv_NombreAuto);
        TextView tv_InfoAuto = (TextView) convertView.findViewById(R.id.tv_InfoAuto);
        // LLENAMOS LOS ELEMENTOS CON LOS VALORES DE CADA ITEM
        String informacion = "Nombre: "+automovil.getNombreAutomovil()+"\n"+
                             "Categoria: "+automovil.getCategoria()+"\n";

        //img_Auto.setImageResource(automovil.getImagenAutomovil());
        tv_NombreAuto.setText(automovil.getNombreAutomovil());
        tv_InfoAuto.setText(informacion);


        return convertView;
    }

}