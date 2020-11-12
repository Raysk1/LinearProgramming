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
    Intent intentToSimplex, intentToGrafico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intentToSimplex = new Intent(this, SimplexTableActivity.class);
        intentToGrafico = new Intent(this, GraficoActivity.class);



       simplexButton = (Button) findViewById(R.id.simplexButton);
       simplexButton.setOnClickListener(v -> startActivity(intentToSimplex));

        graficoButton = (Button) findViewById(R.id.graficoButton);
        graficoButton.setOnClickListener(v -> startActivity(intentToGrafico));



    }







}