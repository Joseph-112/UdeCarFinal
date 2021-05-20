package com.udecar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrincipalAdmi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admi);
    }

    public void vistaCrearAutos(View view){
        Intent myintent = new Intent(PrincipalAdmi.this, CrearAutos.class);
        startActivity(myintent);
    }
}