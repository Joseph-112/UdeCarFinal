package com.udecar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Bujia;
import com.udecar.Datos.Filtro;
import com.udecar.Datos.Motor;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class CrearMotor extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();

    // private DatabaseReference dataBase;
    private TextView nombreMotor, cilindraje, potencia, descripcion;
    private Spinner bujias, filtros;
    private Button guardar;
    private ListView lv_Motores;
    private ArrayList<Motor> listaMotores = new ArrayList<>();
    private Adaptador adaptador;
    private  String bujia1, filtro1;

    //private Motor motorAuto = new Motor();
    //private Motor a;

    private ProgressDialog progressDialog;

    private ArrayList<String> bujia = new ArrayList<>();
    private ArrayList<String> filtro = new ArrayList<>();

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_motor);

        dataBase = FirebaseDatabase.getInstance().getReference();

        lv_Motores = findViewById(R.id.lista_motores);
        nombreMotor = findViewById(R.id.txtNombreMotor);
        cilindraje = findViewById(R.id.txtCilindraje);
        potencia = findViewById(R.id.txtPotenciaMotor);
        descripcion = findViewById(R.id.txtDescripcion);
        guardar = findViewById(R.id.bnCrear);
        bujias = findViewById(R.id.sp_Bujia);
        filtros = findViewById(R.id.sp_Filtro);
        //getImage();
        //listarDatos();


        dataBase.child("Bujia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bujia.clear();
                bujia.add("--Seleccionar--");
                if (snapshot.exists()) {
                    for (DataSnapshot bujiaa : snapshot.getChildren()) {
                        Bujia bujiaSpinner = bujiaa.getValue(Bujia.class);
                        bujia.add(bujiaSpinner.getTipoBujia());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(CrearMotor.this, R.layout.support_simple_spinner_dropdown_item, bujia);
                    bujias.setAdapter(adaptador);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dataBase.child("Filtro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotFiltro) {
                filtro.clear();
                filtro.add("--Seleccionar--");
                if (snapshotFiltro.exists()) {
                    for (DataSnapshot filtroS : snapshotFiltro.getChildren()) {
                        Filtro filtroSpinner = new Filtro();
                        filtroSpinner.setPotencia(Float.parseFloat(filtroS.child("potencia").getValue().toString()));
                        filtroSpinner.setTipofiltro(filtroS.child("tipoFiltro").getValue().toString());
                        filtro.add(filtroSpinner.getTipofiltro());
                    }
                    ArrayAdapter<String> adaptadorfiltros = new ArrayAdapter<>( CrearMotor.this, R.layout.support_simple_spinner_dropdown_item, filtro);
                    filtros.setAdapter(adaptadorfiltros);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CrearMotor.this, "Error con la base de datos: bujia", Toast.LENGTH_LONG).show();
            }
        });
            bujias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            bujia1 = bujias.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
            });

            filtros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           filtro1 = filtros.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
            });
        guardar.setOnClickListener(this);

    }

    /*private void listarDatos() {
        listaMotores.clear();
        dataBase.child("Motor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot motorDB : snapshot.getChildren()) {
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
                        }

                        listaMotores.add(motor);

                    }

                    //lv_Motores.setAdapter(listaMotores);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }*/


    private void RegistrarMotor() {
        String nombre = nombreMotor.getText().toString();
        Float cilindraj = Float.valueOf(cilindraje.getText().toString());
        Float potenci = Float.valueOf(potencia.getText().toString());
        String descri = descripcion.getText().toString();
        String bujia = bujias.toString();
        String filtro = filtros.toString();


        if (TextUtils.isEmpty(nombre)) {

            Toast.makeText(this, "Por favor ingrese un nombre de motor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cilindraj == null) {
            Toast.makeText(this, "Por favor ingrese un tipo de freno", Toast.LENGTH_SHORT).show();
            return;
        }

        if (potenci == null) {
            Toast.makeText(this, "Por favor ingrese un tipo de motor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(descri)) {
            Toast.makeText(this, "Por favor ingrese una descripcion", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Motor a = new Motor();


            a.setNombreMotor(nombre);
            a.setCilindraje(cilindraj);
            a.setPotencia(potenci);
            a.setDescripcionMotor(descri);
            a.setTipoFiltro(filtro1);
            a.setTipoBujia(bujia1);




//Firebase
            dataBase.child("Motor").child(a.getNombreMotor()).setValue(a);
            Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    public void onClick(View v) {
        RegistrarMotor();
    }


}