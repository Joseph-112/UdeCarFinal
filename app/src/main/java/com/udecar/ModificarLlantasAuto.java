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
import com.udecar.Datos.Llantas;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ModificarLlantasAuto extends Fragment implements View.OnClickListener {

    private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
    private Spinner listallantas;

    private TextView labelNombre;
    private TextView labelInfo;
    private TextView labelAgarreModificado;
    private TextView labelPorcentaje;
    private Button botonGuardar;

    FragmentManager fragmentManagerL;
    FragmentTransaction fragmentTransactionL;

    private Llantas llantasAuto = new Llantas();
    private AutomovilesModificados autoModificado = new AutomovilesModificados();

    private String nombreLlantas ;
    private float agarre = 0.0f;
    private float agarreAumento = 0.0f;
    private float agarreLlanta = 0.0f;
    private float agarreOriginal = 0 ;
    private DecimalFormat frmt = new DecimalFormat();

    private ArrayList<String> llantas = new ArrayList<>();

    private String llantaSeleccionada ;
    String llantaMod ;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_llantas_auto,container,false);
        //llama los datos del fragment anterior
        Bundle datosRecuperados = getArguments();

        autoModificado = (AutomovilesModificados) datosRecuperados.getSerializable("llantasModificar");

        llantasAuto.setNombreLlantas(autoModificado.getNombreLlantasM());
        llantasAuto.setTipoLlanta(autoModificado.getTipoLlantaM());
        llantasAuto.setDescripcionllantas(autoModificado.getDescripcionLlantasM());
        llantasAuto.setAgarreLlanta(autoModificado.getAgarreM());
        
        //inicializar componentes
        listallantas = (Spinner) view.findViewById(R.id.ddl_listaLlantas);
        labelPorcentaje = view.findViewById(R.id.tv_porcentajeAgarre);
        labelAgarreModificado = view.findViewById(R.id.tv_agarreModificado);
        
        labelNombre = view.findViewById(R.id.tv_nombreLlantas);
        labelInfo = view.findViewById(R.id.infoLlantas);
        botonGuardar =  view.findViewById(R.id.btn_guardarModLlantas);
        frmt.setMaximumFractionDigits(3);
        //muestra los datos en pantalla
        String nombre = llantasAuto.getNombreLlantas();
        String informacion= "Agarre: " + frmt.format(llantasAuto.getAgarreLlanta()) + "\n" +
                            "Tipo: " + llantasAuto.getTipoLlanta() + "\n";
        labelNombre.setText(nombre);
        labelInfo.setText(informacion);
        labelAgarreModificado.setText(""+llantasAuto.getAgarreLlanta());

        dataBase.child("Llanta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                llantas.clear();
                llantas.add("--Seleccionar--");
                if (snapshot.exists()){
                    for(DataSnapshot llanta : snapshot.getChildren()){
                        Llantas llantasSpinner = llanta.getValue(Llantas.class);
                        llantas.add(llantasSpinner.getTipoLlanta());
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, llantas);
                    listallantas.setAdapter(adaptador);

                    for (int i=0 ; i<llantas.size() ; i++) {
                        if (llantasAuto.getTipoLlanta().equals(llantas.get(i))) {
                            final int finalI = i;
                            if (snapshot.exists()) {
                                for (DataSnapshot llantaQuery : snapshot.getChildren()) {
                                    Llantas llantaModificada = llantaQuery.getValue(Llantas.class);
                                    if (llantaModificada.getTipoLlanta().equals(llantas.get(finalI))) {
                                        agarreLlanta = llantaModificada.getAgarreLlanta();
                                    }
                                }
                            }
                        }
                    }
                }
                float ag =llantasAuto.getAgarreLlanta();
                float uno = 1.000f ;
                float div = (uno + agarreLlanta);
                agarreOriginal = ag / div ;

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error con la base de datos: Llanta",Toast.LENGTH_LONG).show();
            }
        });
        
        listallantas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llantaSeleccionada = listallantas.getSelectedItem().toString();
                if (llantaSeleccionada.equals("--Seleccionar--")){
                    agarreAumento = 0;
                    llantaMod = llantasAuto.getTipoLlanta();
                    nombreLlantas = llantasAuto.getNombreLlantas();
                    labelAgarreModificado.setText(frmt.format(llantasAuto.getAgarreLlanta()));
                }else {
                    dataBase.child("Llanta").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot llantaQuery : snapshot.getChildren()) {
                                    Llantas llantaModificada = llantaQuery.getValue(Llantas.class);
                                    if(llantaSeleccionada.equals(llantasAuto.getTipoLlanta())){
                                        nombreLlantas = llantasAuto.getNombreLlantas();
                                        llantaMod = llantasAuto.getTipoLlanta();
                                        labelPorcentaje.setText("");
                                        agarreAumento = 0 ;
                                        labelAgarreModificado.setText(frmt.format(llantasAuto.getAgarreLlanta()));
                                        Toast.makeText(getContext(),"Ha seleccionado las mismas llantas",Toast.LENGTH_LONG).show();
                                    }else {
                                        if (llantaModificada.getTipoLlanta().equals(llantaSeleccionada)) {
                                            nombreLlantas = llantaModificada.getNombreLlantas();
                                            agarreLlanta = llantaModificada.getAgarreLlanta();
                                            agarreAumento = agarreOriginal * llantaModificada.getAgarreLlanta();
                                            llantaMod = (llantaModificada.getTipoLlanta());
                                            labelPorcentaje.setText("Aumenta el agarre original en " + (llantaModificada.getAgarreLlanta() * 100) + "%");
                                            labelAgarreModificado.setText(frmt.format(agarreOriginal + agarreAumento));
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
        Bundle llantasMod = new Bundle();
        switch (v.getId()){
            case R.id.btn_guardarModLlantas:
                if (agarreAumento == 0) {
                    agarre = llantasAuto.getAgarreLlanta()  ;
                    llantaMod = llantasAuto.getTipoLlanta();
                }else {
                    agarre = agarreAumento + agarreOriginal;
                }

                llantasAuto.setNombreLlantas(nombreLlantas);
                llantasAuto.setAgarreLlanta(agarre);
                llantasAuto.setTipoLlanta(llantaMod);

                autoModificado.setNombreLlantasM(llantasAuto.getNombreLlantas());
                autoModificado.setTipoLlantaM(llantasAuto.getTipoLlanta());
                autoModificado.setDescripcionLlantasM(llantasAuto.getDescripcionllantas());

                Fragment fragment = new ModificarAutos();
                llantasMod.putSerializable("autoMod",autoModificado);

                fragment.setArguments(llantasMod);
                fragmentManagerL = getActivity().getSupportFragmentManager();
                fragmentTransactionL = fragmentManagerL.beginTransaction();
                fragmentTransactionL.replace(R.id.content_fragment,fragment);
                fragmentTransactionL.addToBackStack(null);
                fragmentTransactionL.commit();
                break;
        }
    }
}