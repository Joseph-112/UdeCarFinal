package com.udecar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Bujia;
import com.udecar.Datos.Frenos;
import com.udecar.Datos.Llantas;
import com.udecar.Datos.Motor;

import java.util.ArrayList;
import java.util.Random;

public class CrearLlantas extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();

    private TextView nombreLLanta, agarre, descripcionLlanta;
    private Spinner llantas;
    private Button guardarLlantas;
    private String llanta1;

    private ArrayList<String> tipos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_llantas);

        dataBase = FirebaseDatabase.getInstance().getReference();

        nombreLLanta = findViewById(R.id.textNombreLLanta);
        agarre = findViewById(R.id.text_Agarre);
        descripcionLlanta = findViewById(R.id.text_Descripcion2);
        llantas = findViewById(R.id.spTipoLLantas);

        guardarLlantas = findViewById(R.id.btnGuardarLlantas);

        guardarLlantas.setOnClickListener( this);


        dataBase.child("TiposLlantas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tipos.clear();
                tipos.add("--Seleccionar--");
                if (snapshot.exists()) {
                    for (DataSnapshot tipollanta : snapshot.getChildren()) {
                        tipos.add(tipollanta.child("nombreTipo").getValue().toString());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(CrearLlantas.this, R.layout.support_simple_spinner_dropdown_item, tipos);
                    llantas.setAdapter(adaptador);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CrearLlantas.this,"Error con la base de datos: Llanta",Toast.LENGTH_LONG).show();
            }
        });


        llantas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llanta1 = llantas.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void RegistrarLlantas() {
        String nombre = nombreLLanta.getText().toString();
        Float agarr = Float.valueOf(agarre.getText().toString());
        String descri = descripcionLlanta.getText().toString();
        String llant = llantas.toString();
        Random random = new Random();

        if (TextUtils.isEmpty(nombre)) {

            Toast.makeText(this, "Por favor ingrese un nombre de motor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (agarr == null) {
            Toast.makeText(this, "Por favor ingrese un tipo de freno", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(descri)) {
            Toast.makeText(this, "Por favor ingrese una descripcion", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Llantas a = new Llantas();


            a.setNombreLlanta(nombre);
            a.setAgarre(agarr);
            a.setDescripcion(descri);
            a.setTipoLlanta(llanta1);



            dataBase.child("Llanta").child(String.valueOf((random.nextInt()))).setValue(a);
            Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    public void onClick(View v) {
        RegistrarLlantas();
    }
}