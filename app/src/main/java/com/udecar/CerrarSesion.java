package com.udecar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CerrarSesion extends Fragment {
    public static final String user = "name";

    private Button btnCerrarSesion;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cerrar_sesion,container,false);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
        return view;
    }
}