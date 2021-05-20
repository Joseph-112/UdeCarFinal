package com.udecar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registrarse extends Fragment implements View.OnClickListener{

    private EditText txtEmail;
    private EditText txtNombre;
    private EditText txtContraseña;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    private String nombre = "";
    private String email = "";
    private String password = "";


    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrarse,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtEmail = (EditText)  view.findViewById(R.id.TxtEmail);
        txtContraseña = (EditText) view.findViewById(R.id.TxtContraseña);
        txtNombre = (EditText) view.findViewById(R.id.TxtNombre);

        btnRegistrar = (Button) view.findViewById(R.id.botonRegistrar);

        progressDialog = new ProgressDialog(getContext());
        btnRegistrar.setOnClickListener(this);

        return view;
    }
    private void registrarUsuario(){

        final String email = txtEmail.getText().toString().trim();
        final String password = txtContraseña.getText().toString().trim();
        final String nombre = txtNombre.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getContext(), "Por favor ingrese un correo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(getContext(), "Por favor ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(getContext(), "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Realizando proceso en linea");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener (new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Map<String, Object> map = new HashMap<>();
                            map.put("nombre", nombre);
                            map.put("correo", email);
                            map.put("contraseña", password);

                            String id = firebaseAuth.getCurrentUser().getUid();

                            mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if(task2.isSuccessful()){
                                        startActivity(new Intent(getContext(), VistaUsuario.class));
                                        getActivity().finish();
                                    }
                                }
                            });
                            Toast.makeText(getContext(), "se ha registrado el email", Toast.LENGTH_LONG).show();

                        }else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getContext(), "Este usuario ya existe ", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getContext(), "No se pudo registrar el usuario. Intente nuevamente ", Toast.LENGTH_LONG).show();
                            }
                        }

                        progressDialog.dismiss();
                    }

                });

    }
    @Override
    public void onClick (View view){
        registrarUsuario();
    }
}