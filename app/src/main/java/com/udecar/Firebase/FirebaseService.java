package com.udecar.Firebase;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.udecar.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class FirebaseService {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static  String email = "", nombre = "", password = "";

    private void nuevoUsuario(String email,String password,String nombre){

        this.email = email;
        this.password = password;
        this.nombre = nombre;

        Map<String , Object> usuario = new HashMap<>();
        usuario.put("Email",this.email);
        usuario.put("Contraseña",this.password);
        usuario.put("Nombre",this.nombre);

        db.collection("usuarios").add(usuario).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(MainActivity.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(MainActivity.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        FirebaseService.email = email;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        FirebaseService.nombre = nombre;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        FirebaseService.password = password;
    }
}
