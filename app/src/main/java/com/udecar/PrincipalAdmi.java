package com.udecar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrincipalAdmi extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admi);
    }

    public void vistaCrearAutos(View view){
        Intent myintent = new Intent(PrincipalAdmi.this, CrearAuto.class);
        startActivity(myintent);
    }

    public void vistaCrearMotor(View view){
        Intent myintent = new Intent(PrincipalAdmi.this, CrearMotor.class);
        startActivity(myintent);
    }
    public void vistaCrearBujia(View view){
        Intent myintent = new Intent(PrincipalAdmi.this, CrearBujia.class);
        startActivity(myintent);
    }

    public void vistaCrearFiltro(View view){
        Intent myintent = new Intent(PrincipalAdmi.this, CrearFiltro.class);
        startActivity(myintent);
    }

    public void vistaCrearValvulas(View view){
        Intent myintent = new Intent(PrincipalAdmi.this, CrearValvulas.class);
        startActivity(myintent);
    }

    public void vistaCrearLlantas(View view){
        Intent myintent = new Intent(PrincipalAdmi.this, CrearLlantas.class);
        startActivity(myintent);
    }
}