package com.example.GameHangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Game_Over extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__over);
    }


    public void GoHome(View view){
        Intent IntentHome = new Intent(this,MainActivity.class);
        startActivity(IntentHome);
        finish();
    }
}
