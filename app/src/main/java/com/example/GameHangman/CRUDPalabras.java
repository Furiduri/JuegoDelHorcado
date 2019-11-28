package com.example.GameHangman;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.GameHangman.Entidades.ObjPalabra;
import com.example.GameHangman.Utilidades.Utilidades;

import java.util.ArrayList;

public class CRUDPalabras extends AppCompatActivity {
    EditText txtPalabra;
    RadioGroup rbgLevel;
    Button btnAddPalabra;
    ListView lvPalabras;
    ArrayList<ObjPalabra> ListaPalabras;
    public static String pablabraTap = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_palabras);
        //Obtengo los objetos a utilizar
        txtPalabra = (EditText) findViewById(R.id.txtPalabra);
        rbgLevel = (RadioGroup) findViewById(R.id.rbgLevel);
        btnAddPalabra = (Button) findViewById(R.id.btnAddPalabra);
        lvPalabras = (ListView) findViewById(R.id.lvPalabras);
        //Muestra la lista de las palabras
        fnShowPalabras();
    }

    private void fnShowPalabras() {
        ListaPalabras =  Utilidades.GET_Palabras(this);
        if(!ListaPalabras.isEmpty()){
            ArrayList<String> listaInformacion = new ArrayList<String>();

            for (int i=0; i<ListaPalabras.size();i++){
                listaInformacion.add("ID: "+ListaPalabras.get(i).getID()+" - "
                        +ListaPalabras.get(i).getPalabra()+" - Dificultad: "
                +ListaPalabras.get(i).getNivel());
            }
            ArrayAdapter adapter = new ArrayAdapter(this,
                    R.layout.support_simple_spinner_dropdown_item,
                    listaInformacion);
            lvPalabras.setAdapter(adapter);
            lvPalabras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    String informacion= "";
                    String palabra = ListaPalabras.get(pos).getPalabra();
                    if (pablabraTap.equalsIgnoreCase(palabra)) {
                        informacion = Utilidades.Del_Palabra(ListaPalabras.get(pos).getPalabra(), getApplicationContext());
                        pablabraTap = "";
                    }else {
                        pablabraTap = ListaPalabras.get(pos).getPalabra();
                        informacion = "ID: "+ListaPalabras.get(pos).getID()+
                                "\n Palabra: "+ListaPalabras.get(pos).getPalabra()+
                                "\n Dificultad: "+ListaPalabras.get(pos).getNivel();
                    }

                    Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_SHORT).show();
                    fnShowPalabras();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),
                    "Sin Palabras en la base de datos",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view){
        if(view.getId() == R.id.btnAddPalabra){
            String Level = "";
            switch (rbgLevel.getCheckedRadioButtonId()){
                case R.id.rbLevel1:
                    Level ="1";
                    break;
                case R.id.rbLevel2:
                    Level = "2";
                    break;
                case R.id.rbLevel3:
                    Level = "3";
                    break;
                default:
                    Level ="";
                    break;
            }

            if(!txtPalabra.getText().toString().isEmpty() && !Level.isEmpty()){
               String ResID = Utilidades.ADDPalabra(txtPalabra.getText().toString(),Level,this);

               Toast.makeText(getApplicationContext(),
                       "ID: "+ResID,
                       Toast.LENGTH_SHORT).show();
               txtPalabra.setText("");
               rbgLevel.clearCheck();
               fnShowPalabras();
            }else{
                Toast.makeText(getApplicationContext(),"Favor de escribir la palabra y seleccionar una dificultad"
                        ,Toast.LENGTH_SHORT).show();
            }
        }
    }


}
