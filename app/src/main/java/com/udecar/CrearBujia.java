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
import com.udecar.Datos.Motor;

import java.util.Random;

public class CrearBujia extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();

    private TextView tipoBujia,  potenciaBujia, descripcionBujia;
    private Button guardarBujia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_bujia);

        dataBase = FirebaseDatabase.getInstance().getReference();

        tipoBujia = findViewById(R.id.textTipoBujia);
        potenciaBujia = findViewById(R.id.textPotencia);
        descripcionBujia = findViewById(R.id.text_Descripcion);

        guardarBujia = findViewById(R.id.btnGuardarBujia);

        guardarBujia.setOnClickListener(this);

    }


    private void registrarBujia() {
        String bujia2 = tipoBujia.getText().toString();
        Float potencia2=  Float.valueOf(potenciaBujia.getText().toString());
        String descrip = descripcionBujia.getText().toString();
        Random random = new Random();


        Bujia a = new Bujia();


        a.setTipoBujia(bujia2);
        a.setPotencia(potencia2);
        a.setdescripcionBujia(descrip);

        dataBase.child("Bujia").child(String.valueOf((random.nextInt()))).setValue(a);
        Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        registrarBujia();
    }


}