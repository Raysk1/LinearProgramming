package com.raysk.linearprogramming;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.raysk.linearprogramming.logic.Simplex;

public class SimplexTableActivity extends AppCompatActivity{
    Simplex simplex;
    private EditText vars,res;
    private Button siguiente;
    private double matrix[][];
    private int numVar, numRes;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captar_num_var_res);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        capturarDatos();


















        /*setContentView(R.layout.simplex_table);

        simplex = new Simplex(null);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),2);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);*/


    }


    private String[] getTabsTitles(){
        String[] titles = new String[5];
        titles[0] = "Tabla inicial";
        titles[titles.length-1] = "resultados";

        for (int i = 1; i < titles.length-1 ; i++) {
            titles[i] = "Tabla "+ i;
        }
        return titles;
    }

    private void capturarDatos(){
        vars = (EditText) findViewById(R.id.Vars);
        res = (EditText) findViewById(R.id.Rest);
        siguiente = findViewById(R.id.siguiente);

        siguiente.setOnClickListener(new View.OnClickListener() {

            private TableLayout tableLayout;
            Button button;
            public void onClick(View v) {


                if (vars.getText().toString().equals("") || res.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.siguiente),"Rellene todos los campos",Snackbar.LENGTH_SHORT).show();
                }else if (Integer.parseInt(vars.getText().toString()) == 0 || Integer.parseInt(res.getText().toString()) == 0 ){
                    Snackbar.make(findViewById(R.id.siguiente),"Los campos no pueden valer 0",Snackbar.LENGTH_SHORT).show();
                }else {
                    setNumVar(Integer.parseInt(vars.getText().toString()));
                    setNumRes(Integer.parseInt(res.getText().toString()));
                    setContentView(R.layout.capturar_datos);
                    prueba(numRes,numVar);
                }

            }
        });

    }

    private void setNumVar(int numVar){
        this.numVar = numVar;
    }

    public void setNumRes(int res) {
        this.numRes = res;
    }


    private void prueba(int numRes, int numVar){
        LinearLayout layout = (LinearLayout) findViewById(R.id.captura_datos);
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"

        for (int i = 0; i < numVar; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < numRes; j++) {
                Button btnTag = new Button(this);
                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                btnTag.setText("Button " + (j + 1 + (i * 4)));
                btnTag.setId(j + 1 + (i * 4));
                row.addView(btnTag);
            }

            layout.addView(row);

        }
    }

    private void capturaDatos(int vars, int res){
        LinearLayout layout = (LinearLayout) findViewById(R.id.captura_datos);
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"
    }

}