package com.udecar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udecar.Datos.Bujia;
import com.udecar.Datos.Filtro;

import java.util.Random;

public class CrearFiltro extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();

    private TextView tipoFiltro,  potenciaFiltro, descripcionFiltro;
    private Button guardarFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_filtro);

        dataBase = FirebaseDatabase.getInstance().getReference();
        tipoFiltro = findViewById(R.id.textTipoFiltro);
        potenciaFiltro = findViewById(R.id.textPotenciaFiltro);
        descripcionFiltro = findViewById(R.id.text_DescripcionFiltro);

        guardarFiltro = findViewById(R.id.btnGuardarFiltro);

        guardarFiltro.setOnClickListener(this);

    }


    private void registrarBujia() {
        String filtro2 = tipoFiltro.getText().toString();
        Float potenFiltro2=  Float.valueOf(potenciaFiltro.getText().toString());
        String descrip = descripcionFiltro.getText().toString();
        Random random = new Random();


        Filtro a = new Filtro();


        a.setTipofiltro(filtro2);
        a.setPotencia(potenFiltro2);
        a.setdescripcionFiltro(descrip);

        dataBase.child("Filtro").child(String.valueOf((random.nextInt()))).setValue(a);
        Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        registrarBujia();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}