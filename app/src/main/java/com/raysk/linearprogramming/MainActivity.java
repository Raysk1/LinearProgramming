package com.raysk.linearprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.raysk.linearprogramming.logic.Simplex;

public class MainActivity extends AppCompatActivity {
    Button simplexButton, graficoButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent = new Intent(this,CapturarDatosActivity.class);



       simplexButton = (Button) findViewById(R.id.simplexButton);
       simplexButton.setOnClickListener(v -> {
           intent.putExtra("id", 1);
           startActivity(intent);
       });

        graficoButton = (Button) findViewById(R.id.graficoButton);
        graficoButton.setOnClickListener(v -> {
            intent.putExtra("id",2);
            startActivity(intent);
        });



    }







}