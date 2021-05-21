package com.udecar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.udecar.Datos.Automovil;
import com.udecar.Datos.AutomovilesModificados;
import com.udecar.Datos.Frenos;
import com.udecar.Datos.Llantas;
import com.udecar.Datos.Motor;

import java.util.Random;

public class ModificarAutos extends Fragment implements View.OnClickListener {
    //componentes
    private TextView tv_NombreAuto, tv_InfoAuto , tv_descripcion;
    private Button botonMotor;
    private Button botonLlantas;
    private Button botonFrenos;
    private Button botonTerminar;
    private ImageView img_Auto;
    //private ImageView imgFoto;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    //Objetos
    private Automovil auto = new Automovil();
    private Motor motor = new Motor();
    private Llantas llantas = new Llantas();
    private Frenos frenos = new Frenos();
    private AutomovilesModificados autoModificado = new AutomovilesModificados();
    //fragment
    Fragment modificarMotor = new ModificarMotorAuto();
    Fragment modificarLlantas = new ModificarLlantasAuto();
    Fragment modificarFrenos = new ModificarFrenosAuto();
    Fragment biblioteca = new BibliotecaUsuario();

    FragmentManager fragmentManagerR;
    FragmentTransaction fragmentTransactionR;
    Bundle motorAuto= new Bundle();
    Bundle lantasAuto= new Bundle();
    Bundle frenosAuto= new Bundle();

    String estado ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_autos,container,false);

        Bundle datosRecuperados = getArguments();
        autoModificado = (AutomovilesModificados) datosRecuperados.getSerializable("autoMod");
        estado = datosRecuperados.getString("estado");

        if (autoModificado != null) {
            tv_NombreAuto = view.findViewById(R.id.tv_nombreAuto);
            tv_InfoAuto = view.findViewById(R.id.tv_infoAuto);
            tv_descripcion = view.findViewById(R.id.tv_descripcionAuto);
            botonMotor = view.findViewById(R.id.btn_modificarMotor);
            botonLlantas = view.findViewById(R.id.btn_modificarLlantas);
            botonFrenos = view.findViewById(R.id.btn_modificarFrenos);
            botonTerminar = view.findViewById(R.id.btn_terminar);
            img_Auto = view.findViewById(R.id.img_imagenAuto);

            Picasso.get().load(autoModificado.getImagenAutomovilM()).into(img_Auto);

            auto.setCategoria(autoModificado.getCategoriaM());
            auto.setDescripcion(autoModificado.getDescripcionM());
            auto.setNombreAutomovil(autoModificado.getNombreAutomovilM());
            auto.setNombreLlantas(autoModificado.getNombreLlantasM());
            auto.setNombreMotor(autoModificado.getNombreMotorM());
            auto.setNombreFrenos(autoModificado.getNombreFrenosM());
            auto.setImagenAutomovil(autoModificado.getImagenAutomovilM());
            auto.setAgarre(autoModificado.getAgarreM());

            tv_NombreAuto.setText(auto.getNombreAutomovil());
            String informacion= "Categoria: " + auto.getCategoria() + "\n" +
                                "Motor: " + auto.getNombreMotor() + "\n" +
                                "Frenos:" + auto.getNombreFrenos() + "\n" +
                                "Llantas: " + auto.getNombreLlantas() + "\n";
            tv_InfoAuto.setText(informacion);
            tv_descripcion.setText(auto.getDescripcion());
            botonMotor.setOnClickListener(this);
            botonLlantas.setOnClickListener(this);
            botonFrenos.setOnClickListener(this);
            botonTerminar.setOnClickListener(this);
        }else{

        }
        return view;
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
            case R.id.btn_modificarMotor :
                motorAuto.putSerializable("motorModificar",autoModificado);
                motorAuto.putString("estado",estado);
                modificarMotor.setArguments(motorAuto);

                fragmentManagerR = getActivity().getSupportFragmentManager();
                fragmentTransactionR = fragmentManagerR.beginTransaction();
                fragmentTransactionR.replace(R.id.content_user,modificarMotor);
                fragmentTransactionR.addToBackStack(null);
                fragmentTransactionR.commit();
                break;

            case R.id.btn_modificarFrenos :
                frenosAuto.putSerializable("frenosModificar",autoModificado);
                motorAuto.putString("estado",estado);
                modificarFrenos.setArguments(frenosAuto);

                fragmentManagerR = getActivity().getSupportFragmentManager();
                fragmentTransactionR = fragmentManagerR.beginTransaction();
                fragmentTransactionR.replace(R.id.content_user,modificarFrenos);
                fragmentTransactionR.addToBackStack(null);
                fragmentTransactionR.commit();

                break;

           case R.id.btn_modificarLlantas :
               lantasAuto.putSerializable("llantasModificar",autoModificado);
               motorAuto.putString("estado", estado);

               modificarLlantas.setArguments(lantasAuto);

               fragmentManagerR = getActivity().getSupportFragmentManager();
               fragmentTransactionR = fragmentManagerR.beginTransaction();
               fragmentTransactionR.replace(R.id.content_user,modificarLlantas);
               fragmentTransactionR.addToBackStack(null);
               fragmentTransactionR.commit();

               break;

           case R.id.btn_terminar:
               //guardar en BD autoModificado y el usuario

                if(estado.equals("nuevo")){
                    database.child("AutosModificados").child("Modificados").child(String.valueOf(autoModificado.getIdAuto())).setValue(autoModificado);
                    Toast.makeText(getContext(), "Agregado", Toast.LENGTH_SHORT).show();
                }else {

                    database.child("AutosModificados").child("Modificados").child(String.valueOf(autoModificado.getIdAuto())).setValue(autoModificado);
                    Toast.makeText(getContext(), "Modificado", Toast.LENGTH_SHORT).show();
                }

               fragmentManagerR = getActivity().getSupportFragmentManager();
               fragmentTransactionR = fragmentManagerR.beginTransaction();
               fragmentTransactionR.replace(R.id.content_user,biblioteca);
               fragmentTransactionR.addToBackStack(null);
               fragmentTransactionR.commit();

               break;
        }
    }
}