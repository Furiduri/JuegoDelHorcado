package com.example.GameHangman;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.btnStart:
                Intent miItent = new Intent(this, Start_Game.class);
                startActivity(miItent);
                break;
            case R.id.imgLogo:
                Uri uri = Uri.parse("http://gcatcode.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }
}
