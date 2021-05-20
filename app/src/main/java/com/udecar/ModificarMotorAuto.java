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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udecar.Datos.AutomovilesModificados;
import com.udecar.Datos.Bujia;
import com.udecar.Datos.Filtro;
import com.udecar.Datos.Motor;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ModificarMotorAuto extends Fragment implements View.OnClickListener {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
    private Spinner listaBujias;
    private Spinner listaFiltros;

    private TextView labelInfo;
    private TextView labelNombre;
    private TextView labelRendimientoModificado;
    private TextView labelPorcentaje;

    private Button botonGuardar;

    FragmentManager fragmentManagerR;
    FragmentTransaction fragmentTransactionR;

    private Motor motorAuto = new Motor();
    private AutomovilesModificados autoModificado = new AutomovilesModificados();
    private float potencia = 0.0f;
    private float potenciaAumentoBujia = 0.0f;
    private float potenciaAumentoFiltro= 0.0f;
    private float potenciaBujia = 0.0f;
    private float potenciafiltro = 0.0f;
    private float potenciaOriginal = 0 ;
    private float potenciafiltroOriginal = 0 ;
    private float potenciaBujiaOriginal = 0 ;

    private DecimalFormat frmt = new DecimalFormat();

    private ArrayList <String> bujias = new ArrayList<>();
    private ArrayList <String> filtros = new ArrayList<>();

    private String bujiaOriginal ;
    private String filtroOriginal ;
    private String bujiaSeleccionada ;
    private String filtroSeleccionado ;
    String bujiaMod ;
    String filtroMod ;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_motor_auto,container,false);
        //llama los datos del fragment anterior
        Bundle datosRecuperados = getArguments();

        autoModificado = (AutomovilesModificados) datosRecuperados.getSerializable("motorModificar");

        motorAuto.setNombreMotor(autoModificado.getNombreMotorM());
        motorAuto.setPotencia(autoModificado.getPotenciaM());
        motorAuto.setTipoBujia(autoModificado.getTipoBujiaM());
        motorAuto.setTipoFiltro(autoModificado.getTipoFiltroM());
        motorAuto.setDescripcionMotor(autoModificado.getDescripcionMotorM());
        //inicializar componentes
        listaBujias = (Spinner) view.findViewById(R.id.ddl_listaBujias);
        listaFiltros = (Spinner)view.findViewById(R.id.ddl_listaFiltros);
        labelPorcentaje = view.findViewById(R.id.tv_porcentajeRendimiento);
        labelRendimientoModificado = view.findViewById(R.id.tv_rendimientoModificado);
        labelNombre = view.findViewById(R.id.tv_nombreMotor);
        labelInfo = view.findViewById(R.id.infoMotor);
        botonGuardar =  view.findViewById(R.id.btn_guardarModMotor);
        frmt.setMaximumFractionDigits(3);
        //muestra los datos en pantalla
        String nombre = motorAuto.getNombreMotor();
        String informacion= "Potencia: " + frmt.format(motorAuto.getPotencia()) + "\n" +
                            "Bujias: " + motorAuto.getTipoBujia() + "\n" +
                            "Filtros: " + motorAuto.getTipoFiltro()+ "\n";
        labelNombre.setText(nombre);
        labelInfo.setText(informacion);
        labelRendimientoModificado.setText(""+motorAuto.getPotencia());

        dataBase.child("Bujia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bujias.clear();
                bujias.add("--Seleccionar--");
                if (snapshot.exists()){
                    for(DataSnapshot bujia : snapshot.getChildren()){
                        Bujia bujiaSpinner = bujia.getValue(Bujia.class);
                        bujias.add(bujiaSpinner.getTipoBujia());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, bujias);
                    listaBujias.setAdapter(adaptador);

                    for (int i=0 ; i<bujias.size() ; i++) {
                        if (motorAuto.getTipoBujia().equals(bujias.get(i))) {
                            final int finalI = i;
                            if (snapshot.exists()) {
                                for (DataSnapshot bujiaQuery : snapshot.getChildren()) {
                                    Bujia bujiaModificada = bujiaQuery.getValue(Bujia.class);
                                    if (bujiaModificada.getTipoBujia().equals(bujias.get(finalI))) {
                                        potenciaBujia = bujiaModificada.getPotencia();
                                        potenciaBujiaOriginal = bujiaModificada.getPotencia();
                                        bujiaOriginal = bujiaModificada.getTipoBujia();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error con la base de datos: bujia",Toast.LENGTH_LONG).show();
            }
        });

        dataBase.child("Filtro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotFiltro) {
                filtros.clear();
                filtros.add("--Seleccionar--");
                if (snapshotFiltro.exists()){
                    for(DataSnapshot filtroS : snapshotFiltro.getChildren()){
                        Filtro filtroSpinner = new Filtro();
                        filtroSpinner.setPotencia(Float.parseFloat(filtroS.child("potencia").getValue().toString()));
                        filtroSpinner.setTipofiltro(filtroS.child("tipoFiltro").getValue().toString());
                        filtros.add(filtroSpinner.getTipofiltro());
                    }
                    ArrayAdapter<String> adaptadorfiltros = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item,filtros);
                    listaFiltros.setAdapter(adaptadorfiltros);

                    for (int i=0 ; i<filtros.size() ; i++) {
                        if (motorAuto.getTipoFiltro().equals(filtros.get(i))) {
                            final int finalX = i;
                            if (snapshotFiltro.exists()) {
                                for (DataSnapshot filtroQuery : snapshotFiltro.getChildren()) {
                                    Filtro filtroSpinner = new Filtro();
                                    filtroSpinner.setPotencia(Float.parseFloat(filtroQuery.child("potencia").getValue().toString()));
                                    filtroSpinner.setTipofiltro(filtroQuery.child("tipoFiltro").getValue().toString());
                                    if (filtroSpinner.getTipofiltro().equals(filtros.get(finalX))) {
                                        potenciafiltro = filtroSpinner.getPotencia();
                                        potenciafiltroOriginal = filtroSpinner.getPotencia();
                                        filtroOriginal = filtroSpinner.getTipofiltro();
                                    }
                                }
                            }
                        }
                    }
                }
                float pot = motorAuto.getPotencia();
                float uno = 1.000f ;
                float div = (uno + potenciaBujia + potenciafiltro);
                potenciaOriginal = pot / div ;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error con la base de datos: bujia",Toast.LENGTH_LONG).show();
            }
        });

        listaBujias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bujiaSeleccionada = listaBujias.getSelectedItem().toString();
                if (bujiaSeleccionada.equals("--Seleccionar--")){
                    potenciaAumentoBujia = 0;
                    bujiaMod = motorAuto.getTipoBujia();
                    labelRendimientoModificado.setText(frmt.format(motorAuto.getPotencia()));
                }else {
                    if(bujiaSeleccionada.equals(bujiaOriginal)){
                        potenciaBujia = potenciaBujiaOriginal;
                        bujiaMod = motorAuto.getTipoBujia();
                        labelPorcentaje.setText("");
                        potenciaAumentoFiltro = potenciaOriginal * potenciafiltro;
                        potenciaAumentoBujia =  potenciaOriginal * potenciaBujia ;
                        labelRendimientoModificado.setText(frmt.format(potenciaOriginal + potenciaAumentoFiltro + potenciaAumentoBujia));
                        Toast.makeText(getContext(),"El motor contiene estas bujias",Toast.LENGTH_LONG).show();
                    }else {
                        dataBase.child("Bujia").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot bujiaQuery : snapshot.getChildren()) {
                                        Bujia bujiaModificada = bujiaQuery.getValue(Bujia.class);
                                        if (bujiaModificada.getTipoBujia().equals(bujiaSeleccionada)) {
                                            potenciaBujia = bujiaModificada.getPotencia();
                                            potenciaAumentoBujia = potenciaOriginal * bujiaModificada.getPotencia();
                                            potenciaAumentoFiltro = potenciaOriginal * potenciafiltro;
                                            bujiaMod = (bujiaModificada.getTipoBujia());
                                            labelPorcentaje.setText("Aumenta la potencia original en " + (bujiaModificada.getPotencia() * 100) + "%");
                                            labelRendimientoModificado.setText(frmt.format(potenciaOriginal + potenciaAumentoBujia +potenciaAumentoFiltro));
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "Error modificando bujia", Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        listaFiltros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtroSeleccionado = listaFiltros.getSelectedItem().toString();
                if (filtroSeleccionado.equals("--Seleccionar--")){
                    labelRendimientoModificado.setText(frmt.format(motorAuto.getPotencia()));
                    potenciaAumentoFiltro = 0;
                    filtroMod = motorAuto.getTipoFiltro();
                }else {
                    if (filtroSeleccionado.equals(filtroOriginal)) {
                        potenciafiltro = potenciafiltroOriginal;
                        filtroMod = motorAuto.getTipoFiltro();
                        labelPorcentaje.setText("");
                        potenciaAumentoFiltro = potenciaOriginal * potenciafiltro;
                        potenciaAumentoBujia = potenciaOriginal * potenciaBujia;
                        labelRendimientoModificado.setText(frmt.format(potenciaOriginal + potenciaAumentoFiltro + potenciaAumentoBujia));
                        Toast.makeText(getContext(), "El motor contiene estos filtros de aire", Toast.LENGTH_LONG).show();
                    } else {
                        dataBase.child("Filtro").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange (@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot filtroQuery : snapshot.getChildren()) {
                                        Filtro filtroSpinner = new Filtro();
                                        filtroSpinner.setPotencia(Float.parseFloat(filtroQuery.child("potencia").getValue().toString()));
                                        filtroSpinner.setTipofiltro(filtroQuery.child("tipoFiltro").getValue().toString());
                                        if (filtroSpinner.getTipofiltro().equals(filtroSeleccionado)) {
                                            potenciafiltro = filtroSpinner.getPotencia();
                                            potenciaAumentoFiltro = potenciaOriginal * filtroSpinner.getPotencia();
                                            potenciaAumentoBujia = potenciaOriginal * potenciaBujia;
                                            filtroMod = (filtroSpinner.getTipofiltro());
                                            labelPorcentaje.setText("Aumenta la potencia original en " + (filtroSpinner.getPotencia() * 100) + "%");
                                            labelRendimientoModificado.setText(frmt.format(potenciaOriginal + potenciaAumentoFiltro + potenciaAumentoBujia));
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled (@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "Error modificando filtro", Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        botonGuardar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Bundle motorMod = new Bundle();
       switch (v.getId()){
            case R.id.btn_guardarModMotor:
                if((potenciaAumentoBujia == 0 ) && (potenciaAumentoFiltro == 0)){
                    potencia = motorAuto.getPotencia();
                }else {
                    if (potenciaAumentoBujia == 0) {
                        float aumentoFiltro = potenciaOriginal + potenciaAumentoFiltro;
                        float auxF =  motorAuto.getPotencia() - aumentoFiltro;
                        potencia = potenciaOriginal + potenciaAumentoFiltro + auxF ;
                    } else {
                        if (potenciaAumentoFiltro == 0) {
                            float aumentobujia = potenciaOriginal + potenciaAumentoBujia ;
                            float auxB =  motorAuto.getPotencia() - aumentobujia;
                            potencia = potenciaOriginal + potenciaAumentoBujia + auxB ;
                        }else {
                            potencia = potenciaAumentoBujia+ potenciaAumentoFiltro + potenciaOriginal;
                        }
                    }
                }
                motorAuto.setPotencia(potencia);
                motorAuto.setTipoBujia(bujiaMod);
                motorAuto.setTipoFiltro(filtroMod);

                autoModificado.setNombreMotorM(motorAuto.getNombreMotor());
                autoModificado.setPotenciaM(motorAuto.getPotencia());
                autoModificado.setTipoBujiaM(motorAuto.getTipoBujia());
                autoModificado.setTipoFiltroM(motorAuto.getTipoFiltro());
                autoModificado.setDescripcionMotorM(motorAuto.getDescripcionMotor());

                Fragment fragment = new ModificarAutos();
                motorMod.putSerializable("autoMod",autoModificado);

                fragment.setArguments(motorMod);
                fragmentManagerR = getActivity().getSupportFragmentManager();
                fragmentTransactionR = fragmentManagerR.beginTransaction();
                fragmentTransactionR.replace(R.id.content_fragment,fragment);
                fragmentTransactionR.addToBackStack(null);
                fragmentTransactionR.commit();
                break;
        }
    }
}