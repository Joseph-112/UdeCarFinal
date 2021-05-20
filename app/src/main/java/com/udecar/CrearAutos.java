package com.udecar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.Autos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrearAutos extends AppCompatActivity implements View.OnClickListener {

    private List<Autos> listAutos = new ArrayList<Autos>();
    ArrayAdapter<Autos> arrayAdapterAutos;

    private EditText nombreAuto;
    private EditText tipoFreno;
    private EditText tipoMotor;
    private Button btnCrear, btnActualizar, btnEliminar;
    private Spinner sp_Categoria;
    private ListView autos;

    private ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Autos autoSelected;
   
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_autos);

        nombreAuto = (EditText) findViewById(R.id.textNombreAuto);
        tipoFreno = (EditText) findViewById(R.id.txtTipoFreno);
        tipoMotor = (EditText) findViewById(R.id.txtTipoMotor);
        btnCrear= (Button) findViewById(R.id.btnCrear);
        btnActualizar= (Button) findViewById(R.id.btn_Actualizar);
        btnEliminar= (Button) findViewById(R.id.btn_Eliminar);
        sp_Categoria = (Spinner) findViewById(R.id.spCategoria); 

        autos = (ListView) findViewById(R.id.lvAutos);


        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        listAutos();
        llenarCategoria();


       btnCrear.setOnClickListener(this);
       btnActualizar.setOnClickListener(this);
       btnEliminar.setOnClickListener(this);

        autos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoSelected = (Autos) parent.getItemAtPosition(position);
                nombreAuto.setText(autoSelected.getNombreAuto());
                tipoFreno.setText(autoSelected.getTipoFreno());
                tipoMotor.setText(autoSelected.getTipoMotor());
            }
        });

    }

    private void llenarCategoria() {
    }

    private void listAutos() {
        databaseReference.child("Autos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAutos.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Autos a = objSnaptshot.getValue(Autos.class);
                    listAutos.add(a);

                    arrayAdapterAutos = new ArrayAdapter<Autos>(CrearAutos.this, android.R.layout.simple_list_item_1, listAutos);
                    autos.setAdapter(arrayAdapterAutos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void Registrar (){
        String nombre = nombreAuto.getText().toString();
        String freno = tipoFreno.getText().toString();
        String motor = tipoMotor.getText().toString();

        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Por favor ingrese un nombre de auto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(freno)){
            Toast.makeText(this, "Por favor ingrese un tipo de freno", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(motor)){
            Toast.makeText(this, "Por favor ingrese un tipo de motor", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Autos a = new Autos();
            a.setIdAuto(UUID.randomUUID().toString());
            a.setNombreAuto(nombre);
            a.setTipoFreno(freno);
            a.setTipoMotor(motor);
            databaseReference.child("Autos").child(a.getIdAuto()).setValue(a);
            Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
            limpiarCajas();
        }



    }

    private void actualizar(){
        String nombre = nombreAuto.getText().toString();
        String freno = tipoFreno.getText().toString();
        String motor = tipoMotor.getText().toString();

        Autos b = new Autos();
        b.setIdAuto(autoSelected.getIdAuto());
        b.setNombreAuto(nombreAuto.getText().toString().trim());
        b.setNombreAuto(tipoFreno.getText().toString().trim());
        b.setNombreAuto(tipoMotor.getText().toString().trim());
        databaseReference.child("Autos").child(b.getIdAuto()).setValue(b);
        Toast.makeText(this,"Actualizado", Toast.LENGTH_LONG).show();
        limpiarCajas();


    }

    private void eliminar() {
        Autos p = new Autos();
        p.setIdAuto(autoSelected.getIdAuto());
        databaseReference.child("Autos").child(p.getIdAuto()).removeValue();
        Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG).show();
        limpiarCajas();

    }


    private void limpiarCajas(){
        nombreAuto.setText("");
        tipoFreno.setText("");
        tipoMotor.setText("");

    }


    @Override
    public void onClick(View v) {
        Registrar();
        actualizar();
        eliminar();
    }


}