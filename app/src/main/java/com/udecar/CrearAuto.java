package com.udecar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Automovil;
import com.udecar.Datos.Frenos;
import com.udecar.Datos.Llantas;
import com.udecar.Datos.Motor;

import java.util.ArrayList;
import java.util.List;

public class CrearAuto extends AppCompatActivity implements View.OnClickListener {

    private List<Automovil> listAutos = new ArrayList<Automovil>();
    ArrayAdapter<Automovil> arrayAdapterAutos;

    private EditText nombreAuto;
    private Spinner tipoLlantas;
    private Spinner motorLista;
    private Spinner frenosLista;
    private Spinner llantaLista;
    private EditText descripcion;
    private Button btnCrear, btnActualizar;
    private Spinner sp_Categoria;
    private ListView autos;

    private ArrayList<String> llantas = new ArrayList<>();
    private ArrayList<String> tipos = new ArrayList<>();
    private ArrayList<String> frenos= new ArrayList<>();
    private ArrayList<String> motor = new ArrayList<>();
    private ArrayList<String> categoria = new ArrayList<>();
    private ArrayList<Llantas> aux = new ArrayList<>();

    private ProgressDialog progressDialog;

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
    private String tipoSeleccionado ;
    private String motorSeleccionado ;
    private String llantaSeleccionado ;
    private String frenoSeleccionado ;
    private String categoriaSeleccionado ;



    Automovil auto = new Automovil() ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_auto);

        dataBase = FirebaseDatabase.getInstance().getReference();

        nombreAuto = (EditText) findViewById(R.id.nombreAuto);
        motorLista = (Spinner) findViewById(R.id.spMotor);
        llantaLista = (Spinner) findViewById(R.id.spLlanta);
        frenosLista = (Spinner) findViewById(R.id.spFrenos);

        tipoLlantas =  findViewById(R.id.spTipoLlanta);
        descripcion = (EditText) findViewById(R.id.descripcion);
        sp_Categoria = findViewById(R.id.spCategoria);

        btnCrear= (Button) findViewById(R.id.btnCrear);

        //btnActualizar= (Button) findViewById(R.id.btn_Actualizar);

        //autos = (ListView) findViewById(R.id.lvAutos);
        btnCrear.setOnClickListener(this);

        dataBase.child("TiposLlantas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tipos.clear();
                tipos.add("--Seleccionar--");
                if (snapshot.exists()) {
                    for (DataSnapshot tipollanta : snapshot.getChildren()) {
                        tipos.add(tipollanta.child("nombreTipo").getValue().toString());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(CrearAuto.this, R.layout.support_simple_spinner_dropdown_item, tipos);
                    tipoLlantas.setAdapter(adaptador);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CrearAuto.this,"Error con la base de datos Llanta",Toast.LENGTH_LONG).show();
            }
        });

        dataBase.child("Llanta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aux.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot llanta : snapshot.getChildren()) {
                        Llantas llantaModificada = llanta.getValue(Llantas.class);
                        aux.add(llantaModificada);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CrearAuto.this,"Error con la base de datos Llanta" ,Toast.LENGTH_LONG).show();
            }
        });

        tipoLlantas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                tipoSeleccionado = tipoLlantas.getSelectedItem().toString();
                if(tipoSeleccionado.equals("--Seleccionar--")){

                }else{
                    llantas.clear();
                    llantas.add("--Seleccionar--");
                    for(int i = 0; i < aux.size(); i++){
                        if(tipoSeleccionado.equals(aux.get(i).getTipoLlanta())){
                            llantas.add(aux.get(i).getNombreLlanta());
                        }
                        ArrayAdapter<String> adaptador = new ArrayAdapter<>(CrearAuto.this, R.layout.support_simple_spinner_dropdown_item, llantas);
                        llantaLista.setAdapter(adaptador);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });

        dataBase.child("Motor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               motor.clear();
               motor.add("--Seleccionar--");
                if (snapshot.exists()) {
                    for (DataSnapshot llanta : snapshot.getChildren()) {
                        Motor llantaModificada = llanta.getValue(Motor.class);
                        motor.add(llantaModificada.getNombreMotor());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(CrearAuto.this, R.layout.support_simple_spinner_dropdown_item,motor);
                    motorLista.setAdapter(adaptador);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CrearAuto.this,"Error con la base de datos Llanta" ,Toast.LENGTH_LONG).show();
            }
        });

        dataBase.child("Categorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoria.clear();
                categoria.add("--Seleccionar--");
                if (snapshot.exists()) {
                    for (DataSnapshot llanta : snapshot.getChildren()) {
                         categoria.add(llanta.child("nombreCategoria").getValue().toString());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(CrearAuto.this, R.layout.support_simple_spinner_dropdown_item,categoria);
                    sp_Categoria.setAdapter(adaptador);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CrearAuto.this,"Error con la base de datos Llanta" ,Toast.LENGTH_LONG).show();
            }
        });

        dataBase.child("Frenos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                frenos.clear();
                frenos.add("--Seleccionar--");
                if (snapshot.exists()) {
                    for (DataSnapshot llanta : snapshot.getChildren()) {
                        Frenos llantaModificada = llanta.getValue(Frenos.class);
                        frenos.add(llantaModificada.getNombreFrenos());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(CrearAuto.this, R.layout.support_simple_spinner_dropdown_item,frenos);
                    frenosLista.setAdapter(adaptador);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CrearAuto.this,"Error con la base de datos Llanta" ,Toast.LENGTH_LONG).show();
            }
        });


        motorLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                motorSeleccionado = motorLista.getSelectedItem().toString();
                if (motorSeleccionado.equals("--Seleccionar--")) {
                    Toast.makeText(CrearAuto.this, "Seleccione un motor", Toast.LENGTH_SHORT).show();
                } else {
                    auto.setNombreMotor(motorSeleccionado);
                }
            }
            @Override
            public void onNothingSelected(AdapterView parent) {
            }
        });
        frenosLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                frenoSeleccionado = frenosLista.getSelectedItem().toString();
                if(frenoSeleccionado.equals("--Seleccionar--")){
                    Toast.makeText(CrearAuto.this, "Seleccione unos frenos", Toast.LENGTH_SHORT).show();
                }else{
                    auto.setNombreFrenos(frenoSeleccionado);
                }

            }
            @Override
            public void onNothingSelected(AdapterView parent) {
            }
        });
        sp_Categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                categoriaSeleccionado = sp_Categoria.getSelectedItem().toString();

                if(categoriaSeleccionado.equals("--Seleccionar--")){
                    Toast.makeText(CrearAuto.this, "Seleccione una categoria", Toast.LENGTH_SHORT).show();
                }else{
                    auto.setCategoria(categoriaSeleccionado);
                }

            }
            @Override
            public void onNothingSelected(AdapterView parent) {
            }
        });

        llantaLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                llantaSeleccionado = llantaLista.getSelectedItem().toString();

                if(llantaSeleccionado.equals("--Seleccionar--")){
                    Toast.makeText(CrearAuto.this, "Seleccione una llanta", Toast.LENGTH_SHORT).show();
                }else{
                    auto.setNombreLlantas(llantaSeleccionado);
                    auto.setAgarre(100);
                }

            }
            @Override
            public void onNothingSelected(AdapterView parent) {
            }
        });




    }

    @Override
    public void onClick (View v) {

        auto.setNombreAutomovil(nombreAuto.getText().toString());
        auto.setDescripcion(descripcion.getText().toString());
        auto.setImagenAutomovil("img");

        dataBase.child("Automoviles").child(auto.getNombreAutomovil()).setValue(auto);
        Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();

    }
}