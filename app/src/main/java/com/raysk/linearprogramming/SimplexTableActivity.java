package com.raysk.linearprogramming;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raysk.linearprogramming.graphics.DinamicTable;
import com.raysk.linearprogramming.logic.Simplex;

import java.util.ArrayList;

public class SimplexTableActivity extends AppCompatActivity {
    private ArrayList<String[][]> tablas;


    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle d = this.getIntent().getExtras();
        double[][] matrix = (double[][]) d.get("matrix_simplex");


        setContentView(R.layout.simplex_table);
        Simplex simplex = new Simplex(matrix);
        tablas = simplex.processing();
        presentarTabla();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


   @SuppressLint("SetTextI18n")
   private void presentarTabla(){
       LinearLayout linearLayout;
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
       TextView textView;
       TableLayout tableLayout = findViewById(R.id.tabla);
       for (int i = 0; i < tablas.size() ; i++) {
           linearLayout = new LinearLayout(this);
           linearLayout.setLayoutParams(params);

           textView = new TextView(this);
           textView.setTextSize(50);
           textView.setLayoutParams(params);
           textView.setBackgroundColor(Color.GREEN);
           if (i == 0){
               textView.setText("Tabla Inicial");
           }else if (i == tablas.size()-1){
               textView.setText("Tabla Resultado");
           }else {
               textView.setText("Tabla " + i);
           }
           linearLayout.addView(textView);
           tableLayout.addView(linearLayout);
           DinamicTable dinamicTable = new DinamicTable(tableLayout,this,tablas.get(i));
           dinamicTable.get();

       }
   }







}