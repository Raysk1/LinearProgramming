package com.raysk.linearprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.raysk.linearprogramming.graphics.DinamicTable;
import com.raysk.linearprogramming.logic.Simplex;

public class MainActivity extends AppCompatActivity {
    Button simplexButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Simplex simplex = new Simplex(null);
        intent = new Intent(this, SimplexTableActivity.class);



       simplexButton = (Button) findViewById(R.id.simplexButton);
        simplexButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setContentView(R.layout.table_layout);
               //DinamicTable dinamicTable = new DinamicTable((TableLayout)findViewById(R.id.tabla),getApplicationContext(),simplex.processing());
               //dinamicTable.get();
               startActivity(intent);
              // setContentView(R.layout.table_layout);

           }
       });



    }






}