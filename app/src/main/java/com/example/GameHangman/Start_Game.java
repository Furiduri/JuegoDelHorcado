package com.example.GameHangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.Random;
import java.util.RandomAccess;

public class Start_Game extends AppCompatActivity {
    public LinearLayout ContentWord;
    public ImageView life1,life2,life3;
    public TextView lblError, lblLife,lblMSG;
    public String[] Palabras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__game);
        life1 = (ImageView)findViewById(R.id.life1);
        life2 = (ImageView)findViewById(R.id.life2);
        life3 = (ImageView)findViewById(R.id.life3);
        ContentWord = (LinearLayout)findViewById(R.id.ContentWord);
        lblError = (TextView)findViewById(R.id.lblError);
        lblLife = (TextView)findViewById(R.id.lblLife) ;
        lblMSG = (TextView)findViewById(R.id.lblMSG);
        ArrayList<ObjPalabra> lista = new ArrayList<ObjPalabra>();
        lista.add(new ObjPalabra(1,"Hola Mundo",2));
        lista.add(new ObjPalabra(2,"Furiduri",1));
        lista.add(new ObjPalabra(3,"Platano",1));
        lista.add(new ObjPalabra(4,"La Vida Es Muy Bella",3));


        int palabraID = new Random().nextInt(lista.size());
        Palabras = lista.get(palabraID).getPalabra().toUpperCase().split(" ");

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
    }

    //Que exista la letra
    public void validate(View view){
        if(view.getId() == R.id.btnChek){
            EditText txtLetra = (EditText)findViewById(R.id.txtLetra);
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
                            Intent miItent = new Intent(this, Game_Over.class);
                            startActivity(miItent);
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
            Intent miItent = new Intent(this, Winer.class);
            startActivity(miItent);
            finish();
        }
    }
}

