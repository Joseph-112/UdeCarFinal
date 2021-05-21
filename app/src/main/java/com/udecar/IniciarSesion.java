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

public class IniciarSesion extends Fragment {
    private EditText txtEmail;
    private EditText txtContraseña;
    private Button btnIngresar;
    private Button btnRecuperar;

    private FirebaseAuth auth;

    private String email ;
    private String password ;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_iniciar_sesion, container, false);

        auth = FirebaseAuth.getInstance();

        txtEmail = (EditText) view.findViewById(R.id.TextEmail);
        txtContraseña = (EditText) view.findViewById(R.id.TextPassword);

        btnIngresar = (Button) view.findViewById(R.id.btnLogin);
        btnRecuperar = (Button) view.findViewById(R.id.btnRecuperarContraseña);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                email = txtEmail.getText().toString();
                password = txtContraseña.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    iniciarSesion();
                }else{
                    Toast.makeText(getContext(), "Por Llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                startActivity(new Intent(getContext(), RecuperarContra.class));
            }
        });
        return view;
    }

    private void iniciarSesion() {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                    public void onComplete (@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getContext(),VistaUsuario.class));
                            getActivity().finish();;
                        } else {
                            Toast.makeText(getContext(), "No se pudo iniciar la sesion. Revise los datos e Intente nuevamente ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}