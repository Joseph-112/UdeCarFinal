package com.udecar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udecar.Datos.Valvulas;

import java.util.Random;

public class CrearValvulas extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();

    private TextView nombreValvula, frenado, descripcion;
    private Button guardarValvulas;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_valvulas);

        dataBase = FirebaseDatabase.getInstance().getReference();

        nombreValvula = findViewById(R.id.textValvula);
        frenado = findViewById(R.id.textFrenado);
        descripcion = findViewById(R.id.text_DescripcionFrenos);
        guardarValvulas = findViewById(R.id.btnGuardarValvula);

        guardarValvulas.setOnClickListener(this);







    }

    private void registrarValvula() {

        String nombre = nombreValvula.getText().toString();
        Float frenadoo=  Float.valueOf(frenado.getText().toString());
        String descrip = descripcion.getText().toString();
        Random random = new Random();


        Valvulas a = new Valvulas();


        a.setFrenado(frenadoo);
        a.setTipoValvula(nombre);
        a.setdescripcionValvula(descrip);

        dataBase.child("Valvulas").child(String.valueOf((random.nextInt()))).setValue(a);
        Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onClick(View v) {
        registrarValvula();
    }
}


