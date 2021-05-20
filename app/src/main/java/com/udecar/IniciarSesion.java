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

import java.util.concurrent.Executor;

public class IniciarSesion extends Fragment implements View.OnClickListener {
    private EditText txtEmail;
    private EditText txtContraseña;
    private Button btnIngresar;
    private Button btnRecuperar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_iniciar_sesion,container,false);

        firebaseAuth = FirebaseAuth.getInstance();

        txtEmail = (EditText) view.findViewById(R.id.TextEmail);
        txtContraseña = (EditText)view.findViewById(R.id.TextPassword);

        btnIngresar = (Button) view.findViewById(R.id.btnLogin);
        btnRecuperar = (Button) view.findViewById(R.id.btnRecuperarContraseña);
        progressDialog = new ProgressDialog(getContext());

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),VistaUsuario.class));
            }
        });
        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RecuperarContra.class));
            }
        });
        return view;
    }

    private void ingresarUsuario(){

        final String email = txtEmail.getText().toString().trim();
        String password = txtContraseña.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getContext(), "Por favor ingrese un correo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(getContext(), "Por favor ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Realizando proceso en line");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener ((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getContext(),VistaUsuario.class);
                            intencion.putExtra(VistaUsuario.user, email);
                            startActivity(intencion);
                        }else{
                            Toast.makeText(getContext(), "No se pudo registrar el usuario. Intente nuevamente ", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }

                });

    }
    @Override
    public void onClick(View v) {
        ingresarUsuario();
    }
}