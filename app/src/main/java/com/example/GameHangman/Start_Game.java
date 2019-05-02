package com.example.GameHangman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.GameHangman.Entidades.ObjPalabra;
import com.example.GameHangman.Utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Timer;

public class Start_Game extends AppCompatActivity {
    public LinearLayout ContentWord;
    public ImageView life1,life2,life3;
    public TextView lblError, lblLife,lblMSG, lblTime, lblLevel, lblPalabra;
    public String[] Palabras;
    public EditText txtLetra;
    public Intent IntentGame_Over, IntentHOME ;
    public CountDownTimer Timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__game);
        //Objetos en pantalla
        life1 = (ImageView)findViewById(R.id.life1);
        life2 = (ImageView)findViewById(R.id.life2);
        life3 = (ImageView)findViewById(R.id.life3);
        ContentWord = (LinearLayout)findViewById(R.id.ContentWord);
        lblError = (TextView)findViewById(R.id.lblError);
        lblLife = (TextView)findViewById(R.id.lblLife) ;
        lblMSG = (TextView)findViewById(R.id.lblMSG);
        lblTime = (TextView) findViewById(R.id.lblTime);
        lblLevel = (TextView) findViewById(R.id.lblDificultad);
        txtLetra = (EditText) findViewById(R.id.txtLetra);
        lblPalabra = (TextView) findViewById(R.id.lblPalabra);

        IntentGame_Over = new Intent(this, Game_Over.class);
        IntentHOME = new Intent(this,MainActivity.class);
        //Palabras
        ArrayList<ObjPalabra> listaPalabras = new ArrayList<ObjPalabra>();
        listaPalabras = Utilidades.GET_Palabras(this);
        if(listaPalabras.isEmpty()){
            startActivity(IntentHOME);
            Toast.makeText(getApplicationContext(),
                    "No hay datos de palabras para jugar", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        int palabraID = new Random().nextInt(listaPalabras.size());
        lblPalabra.setText(listaPalabras.get(palabraID).getPalabra().toUpperCase());
        Palabras = listaPalabras.get(palabraID).getPalabra().toUpperCase().split(" ");
        SetLevel(listaPalabras.get(palabraID).getNivel());
        //Muestra los espacios de la palabra
        for(String palabra : Palabras){
            char[] letras = palabra.toCharArray();
            for(final char letra : letras){
                final EditText txtChart = new EditText(getApplicationContext());
                ContentWord.addView(txtChart);
                txtChart.setId(letra);
                txtChart.setEnabled(false);
                txtChart.setMaxEms(1);
//                txtChart.setText(letra);
            }
            if(Palabras.length > 0) {
                TextView lblBlank = new TextView(getApplicationContext());
                ContentWord.addView(lblBlank);
                lblBlank.setText("      ");
            }
        }
        //Strat time
        StartTime();
    }


    private void SetLevel(Integer nivel) {
        switch (nivel){
            case 1:
                lblLevel.setText("Facil");
                lblLevel.setTextColor(Color.BLUE);
                break;
            case 2:
                lblLevel.setText("Medio");
                lblLevel.setTextColor(Color.GREEN);
                break;
            case 3:
                lblLevel.setText("Dificil");
                lblLevel.setTextColor(Color.RED);
                break;
                default:
                    lblLevel.setText("Error");
                    lblLevel.setTextColor(Color.YELLOW);
                    break;
        }
    }

    //Que exista la letra
    public void validate(View view){
        if((view.getId() == R.id.btnChek || view.getId() == R.id.txtLetra)
            && !txtLetra.getText().toString().isEmpty()){
            char chart = txtLetra.getText().charAt(0);
            chart = Character.toUpperCase(chart);
            boolean status = false;
            String Abc = String.valueOf(lblError.getText());
            int exist = Abc.indexOf(String.valueOf(chart));
            if(exist == -1) {
                for (int i = 0; i < ContentWord.getChildCount(); i++) {
                    try {
                        EditText txtchar = (EditText) ContentWord.getChildAt(i);
                        if (txtchar.getId() == chart) {
                            txtchar.setText(String.valueOf(chart));
                            status = true;
                        }
                    } catch (Exception ex) {
                        //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                    lblError.setText(lblError.getText() + String.valueOf(chart) + ", ");
                if (status) {
                    isWiner();
                }else{
                    switch (lblLife.getText().toString()){
                        case "3":
                            lblLife.setText("2");
                            life1.setImageResource(R.drawable.ic_death);
                            break;
                        case "2":
                            lblLife.setText("1");
                            life2.setImageResource(R.drawable.ic_death);
                            break;
                        case "1":
                            lblLife.setText("0");
                            life3.setImageResource(R.drawable.ic_death);
                            lblMSG.setVisibility(View.VISIBLE);
                            break;
                        case "0":
                            lblLife.setText("Death");
                            startActivity(IntentGame_Over);
                            Timer.cancel();
                            finish();
                            break;
                    }
                }
            }
            txtLetra.setText("");
        }
    }

    public void isWiner(){
        boolean status = true;
        for (int i = 0; i < ContentWord.getChildCount(); i++) {
            try {
                EditText txtchar = (EditText) ContentWord.getChildAt(i);
                String text = String.valueOf(txtchar.getText());
                status = text.isEmpty();
                if(status == true)
                    break;
            } catch (Exception ex) {
                //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if(!status){
            //Parametros por ganar
            Intent IntentWiner = new Intent(this, Winer.class);
            IntentWiner.putExtra("LEVEL",(String) lblLevel.getText());
            IntentWiner.putExtra("LIFE",(String) lblLife.getText());
            IntentWiner.putExtra("TIME",(String) lblTime.getText());
            IntentWiner.putExtra("PALABRA",(String) lblPalabra.getText());
            startActivity(IntentWiner);
            Timer.cancel();
            finish();
        }
    }

    public void StartTime(){
        Timer = new CountDownTimer(50000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                lblTime.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                lblLife.setText("Tiempo!");
                startActivity(IntentGame_Over);
                finish();
            }
        };
        Timer.start();
    }

}

