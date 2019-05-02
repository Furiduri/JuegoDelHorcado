package com.example.GameHangman;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.GameHangman.Utilidades.Utilidades;

public class Winer extends AppCompatActivity {
    public Intent miItent;
    public TextView lblLevel, lblLife, lblTime, lblTotal;
    public EditText txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winer);
        //Declaro mis objetos de la vista
        lblLevel = (TextView)findViewById(R.id.lblLevel);
        lblLife = (TextView)findViewById(R.id.lblLife);
        lblTime = (TextView)findViewById(R.id.lblTime);
        lblTotal = (TextView)findViewById(R.id.lblTotal);
        txtName =(EditText) findViewById(R.id.txtName);
        //Muestra el puntaje
        lblLevel.setText(getIntent().getStringExtra("LEVEL"));
        lblLife.setText(getIntent().getStringExtra("LIFE"));
        lblTime.setText(getIntent().getStringExtra("TIME"));
        lblTotal.setText(String.valueOf(SetPuntaje()));
    }

    private int SetPuntaje() {
        int puntaje, vida, tiempo;
        vida = Integer.valueOf((String) lblLife.getText());
        tiempo = Integer.valueOf((String) lblTime.getText());
        puntaje = (vida + tiempo);
        switch ((String)lblLevel.getText()){
            case "Facil":
                puntaje = puntaje * 1;
                break;
            case "Medio":
                puntaje = puntaje * 2;
                break;
            case "Dificil":
                puntaje = puntaje * 3;
                break;
                default:
                    puntaje = 0;
                    break;
        }
        return puntaje;
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.btnStart:
                miItent = new Intent(this, Start_Game.class);
                startActivity(miItent);
                finish();
                break;
            case R.id.imgLogo:
                Uri uri = Uri.parse("http://gcatcode.com");
                miItent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(miItent);
                onPause();
                break;
            case R.id.btnHome:
                 miItent = new Intent(this, MainActivity.class);
                startActivity(miItent);
                finish();
                break;
                default:
        }
    }

    //Guarda el score del jugador
    public void fnSaveScore( View view){
        if(!txtName.getText().toString().isEmpty()){
            String MSG = Utilidades.AddNewScore(txtName.getText().toString(),
                    lblTotal.getText().toString(),
                    lblLife.getText().toString(),
                    lblLevel.getText().toString(),
                    getIntent().getStringExtra("PALABRA").toUpperCase(),
                    lblTime.getText().toString(),this);
            if(!MSG.isEmpty()){
                Toast.makeText(getApplicationContext(),MSG
                        ,Toast.LENGTH_SHORT).show();
                view.setEnabled(false);
                txtName.setEnabled(false);
            }else{
                Toast.makeText(getApplicationContext(),"Error al guardar la puntuacion"
                        ,Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"Favor de escribir primero tu nombre"
                    ,Toast.LENGTH_SHORT).show();
        }
    }
}
