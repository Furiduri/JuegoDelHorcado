package com.example.GameHangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.GameHangman.Entidades.ObjPuntuacion;
import com.example.GameHangman.Utilidades.Utilidades;

import java.util.ArrayList;

public class Scores extends AppCompatActivity {
    ListView lvScores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        lvScores = (ListView) findViewById(R.id.lvScores);
        fnShowScores();
    }

    protected void fnShowScores(){
        final ArrayList<ObjPuntuacion> ListaPuntuaciones =  Utilidades.GET_Puntuaciones(this);
        if(!ListaPuntuaciones.isEmpty()){
            ArrayList<String> listaInformacion=new ArrayList<String>();

            for (int i=0; i<ListaPuntuaciones.size();i++){
                listaInformacion.add(ListaPuntuaciones.get(i).getName()+" \n "
                        +"Palabra: "+ListaPuntuaciones.get(i).getPalabra()+"\nPuntuacion: "
                        +ListaPuntuaciones.get(i).getPuntuacion());
            }
            ArrayAdapter adapter = new ArrayAdapter(this,
                    R.layout.support_simple_spinner_dropdown_item,
                    listaInformacion);
            lvScores.setAdapter(adapter);
            lvScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    String informacion="ID: "+ListaPuntuaciones.get(pos).getID()+"\n";
                    informacion+="Nombre: "+ListaPuntuaciones.get(pos).getName()+"\n";
                    informacion+="Palabra: "+ListaPuntuaciones.get(pos).getPalabra()+"\n";
                    informacion+="Dificultad: "+ListaPuntuaciones.get(pos).getNivel()+"\n";
                    informacion+="Vidas: "+ListaPuntuaciones.get(pos).getLife()+"\n";
                    informacion+="Tiempo: "+ListaPuntuaciones.get(pos).getTime()+"\n";
                    informacion+="TOTAL: "+ListaPuntuaciones.get(pos).getPuntuacion()+"\n";

                    Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(getApplicationContext(),
                    "Sin Puntuaciones en la base de datos",Toast.LENGTH_SHORT).show();
        }
    }
}
