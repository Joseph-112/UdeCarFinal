package com.udecar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContra extends AppCompatActivity {

    private EditText TxtCorreo;
    private Button btnRecuperar;

    private String email = "";

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        TxtCorreo = (EditText) findViewById(R.id.TextCorreo);
        btnRecuperar = (Button) findViewById(R.id.btnRecuperar);

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = TxtCorreo.getText().toString();

                if (!email.isEmpty()){
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    RecuperarContraseña();
                }
                else{
                    Toast.makeText(RecuperarContra.this, "Por favor ingrese su Correo", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();



            }
        });
    }

    private void RecuperarContraseña(){

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(RecuperarContra.this, "Se ha enviado un correo para reestablecer tu contraseña", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(RecuperarContra.this, "No se pudo enviar el correo de recuperacion de contraseña", Toast.LENGTH_SHORT ).show();
                }

            }
        });

    }
}