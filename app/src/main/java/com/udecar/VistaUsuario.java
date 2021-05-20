package com.udecar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class VistaUsuario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String user = "name";

    private FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayoutR;
    ActionBarDrawerToggle actionBarDrawerToggleR;
    Toolbar toolbar;
    NavigationView navigationViewR;

    FragmentManager fragmentManagerR;
    FragmentTransaction fragmentTransactionR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayoutR = findViewById(R.id.drawer_registrado);
        navigationViewR = findViewById(R.id.nav_view);

        navigationViewR.setNavigationItemSelectedListener(this);

        actionBarDrawerToggleR = new ActionBarDrawerToggle(this,drawerLayoutR,toolbar,R.string.open,R.string.close);
        drawerLayoutR.addDrawerListener(actionBarDrawerToggleR);
        actionBarDrawerToggleR.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggleR.syncState();
        //cargar fragment principal en la actividad
        fragmentManagerR = getSupportFragmentManager();
        fragmentTransactionR = fragmentManagerR.beginTransaction();
        fragmentTransactionR.add(R.id.content_user,new InicioRegistrado());
        fragmentTransactionR.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayoutR.closeDrawer(GravityCompat.START);

        if(item.getItemId() == R.id.inicio_registrado){
            fragmentManagerR = getSupportFragmentManager();
            fragmentTransactionR = fragmentManagerR.beginTransaction();
            fragmentTransactionR.replace(R.id.content_user,new InicioRegistrado());
            fragmentTransactionR.commit();
        }
        if(item.getItemId() == R.id.listado_automoviles){
            fragmentManagerR = getSupportFragmentManager();
            fragmentTransactionR = fragmentManagerR.beginTransaction();
            fragmentTransactionR.replace(R.id.content_user,new ListaAutosModificables());
            fragmentTransactionR.commit();
        }
        if(item.getItemId() == R.id.biblioteca_automoviles){
            fragmentManagerR = getSupportFragmentManager();
            fragmentTransactionR = fragmentManagerR.beginTransaction();
            fragmentTransactionR.replace(R.id.content_user,new BibliotecaUsuario());
            fragmentTransactionR.commit();
        }
        if(item.getItemId() == R.id.cerrar_sesion){
            fragmentManagerR = getSupportFragmentManager();
            fragmentTransactionR = fragmentManagerR.beginTransaction();
            fragmentTransactionR.replace(R.id.content_user,new CerrarSesion());
            fragmentTransactionR.commit();
        }
        return false;
    }
}