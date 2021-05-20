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
import com.udecar.Datos.Frenos;
import com.udecar.Datos.Llantas;
import com.udecar.Datos.Valvulas;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ModificarFrenosAuto extends Fragment implements View.OnClickListener{
    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
    private Spinner listaValvulas;

    private TextView labelNombre;
    private TextView labelInfo;
    private TextView labelFrenadoModificado;
    private TextView labelPorcentaje;
    private Button botonGuardar;

    FragmentManager fragmentManagerF;
    FragmentTransaction fragmentTransactionF;

    private Frenos frenosAuto = new Frenos();
    private AutomovilesModificados autoModificado = new AutomovilesModificados();
    
    private float frenado = 0.0f;
    private float frenadoAumento = 0.0f;
    private float frenadoValvula = 0.0f;
    private float frenadoOriginal = 0 ;
    private DecimalFormat frmt = new DecimalFormat();

    private ArrayList<String> valvulas = new ArrayList<>();

    private String valvulaSeleccionada ;
    String valvulaMod ;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_frenos_auto,container,false);
        //llama los datos del fragment anterior
        Bundle datosRecuperados = getArguments();

        autoModificado = (AutomovilesModificados) datosRecuperados.getSerializable("frenosModificar");

        frenosAuto.setNombreFrenos(autoModificado.getNombreFrenosM());
        frenosAuto.setTipoValvulas(autoModificado.getTipoValvulasM());
        frenosAuto.setDescripcionFrenos(autoModificado.getDescripcionFrenosM());
        frenosAuto.setFrenado(autoModificado.getFrenadoM());

        //inicializar componentes
        listaValvulas = (Spinner) view.findViewById(R.id.ddl_listaValvulas);
        labelPorcentaje = view.findViewById(R.id.tv_porcentajeFrenado);
        labelFrenadoModificado = view.findViewById(R.id.tv_frenadoModificado);

        labelNombre = view.findViewById(R.id.tv_nombreFrenos);
        labelInfo = view.findViewById(R.id.infoFrenos);
        botonGuardar =  view.findViewById(R.id.btn_guardarModFrenos);
        frmt.setMaximumFractionDigits(3);
        //muestra los datos en pantalla
        String nombre = frenosAuto.getNombreFrenos();
        String informacion= "Frenado: " + frmt.format(frenosAuto.getFrenado()) + "\n" +
                            "Tipo valvula: " + frenosAuto.getTipoValvulas() + "\n";
        labelNombre.setText(nombre);
        labelInfo.setText(informacion);
        labelFrenadoModificado.setText(""+frenosAuto.getFrenado());

        dataBase.child("Valvulas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                valvulas.clear();
                valvulas.add("--Seleccionar--");
                if (snapshot.exists()){
                    for(DataSnapshot valvula: snapshot.getChildren()){
                        Valvulas valvulasSpinner = valvula.getValue(Valvulas.class);
                        valvulas.add(valvulasSpinner.getTipoValvula());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, valvulas);
                    listaValvulas.setAdapter(adaptador);

                    for (int i=0 ; i<valvulas.size() ; i++) {
                        if (frenosAuto.getTipoValvulas().equals(valvulas.get(i))) {
                            final int finalI = i;
                            if (snapshot.exists()) {
                                for (DataSnapshot valvulaQuery : snapshot.getChildren()) {
                                    Valvulas valvulaModificada = valvulaQuery.getValue(Valvulas.class);
                                    if (valvulaModificada.getTipoValvula().equals(valvulas.get(finalI))) {
                                        frenadoValvula = valvulaModificada.getFrenado();
                                    }
                                }
                            }
                        }
                    }
                }
                float ag = frenosAuto.getFrenado();
                float uno = 1.000f ;
                float div = (uno + frenadoValvula);
                frenadoOriginal = ag / div ;
                System.out.println(frenadoOriginal);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error con la base de datos: Llanta",Toast.LENGTH_LONG).show();
            }
        });

        listaValvulas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valvulaSeleccionada = listaValvulas.getSelectedItem().toString();
                if (valvulaSeleccionada.equals("--Seleccionar--")){
                    frenadoAumento = 0;
                    valvulaMod = frenosAuto.getTipoValvulas();
                    labelFrenadoModificado.setText(frmt.format(frenosAuto.getFrenado()));
                }else {
                    dataBase.child("Valvulas").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot valvulaQuery : snapshot.getChildren()) {
                                    Valvulas valvulaModificada = valvulaQuery.getValue(Valvulas.class);
                                    if(valvulaSeleccionada.equals(frenosAuto.getTipoValvulas())){
                                        valvulaMod = frenosAuto.getTipoValvulas();
                                        labelPorcentaje.setText("");
                                        frenadoAumento = 0 ;
                                        labelFrenadoModificado.setText(frmt.format(frenosAuto.getFrenado()));
                                        Toast.makeText(getContext(),"Ha seleccionado las mismas Valvulas",Toast.LENGTH_LONG).show();
                                    }else {
                                        if (valvulaModificada.getTipoValvula().equals(valvulaSeleccionada)) {
                                            frenadoValvula = valvulaModificada.getFrenado();
                                            frenadoAumento = frenadoOriginal * valvulaModificada.getFrenado();
                                            valvulaMod = (valvulaModificada.getTipoValvula());
                                            labelPorcentaje.setText("Aumenta el frenado original en " + (valvulaModificada.getFrenado() * 100) + "%");
                                            labelFrenadoModificado.setText(frmt.format(frenadoOriginal + frenadoAumento));
                                        }
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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        botonGuardar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Bundle frenosMod = new Bundle();
        switch (v.getId()){
            case R.id.btn_guardarModFrenos:
                if (frenadoAumento == 0) {
                    frenado = frenosAuto.getFrenado();
                    valvulaMod = frenosAuto.getTipoValvulas();
                }else {
                    frenado = frenadoAumento + frenadoOriginal;
                }

                frenosAuto.setFrenado(frenado);
                frenosAuto.setTipoValvulas(valvulaMod);

                autoModificado.setTipoValvulasM(frenosAuto.getTipoValvulas());
                autoModificado.setFrenadoM(frenosAuto.getFrenado());

                Fragment fragment = new ModificarAutos();
                frenosMod.putSerializable("autoMod",autoModificado);

                fragment.setArguments(frenosMod);
                fragmentManagerF = getActivity().getSupportFragmentManager();
                fragmentTransactionF = fragmentManagerF.beginTransaction();
                fragmentTransactionF.replace(R.id.content_fragment,fragment);
                fragmentTransactionF.addToBackStack(null);
                fragmentTransactionF.commit();
                break;
        }
    }
}