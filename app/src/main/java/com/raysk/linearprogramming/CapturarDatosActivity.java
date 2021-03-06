package com.raysk.linearprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.raysk.linearprogramming.graphics.CapturaDatos;

import java.util.ArrayList;

public class CapturarDatosActivity extends AppCompatActivity {
    private EditText vars, res;
    private int numVar;
    private int numRes;
    private int numOrigen;
    private int numDestino;
    private ArrayList<EditText> datos;
    private Button obtenerResul;
    private double[][] matrix;
    private  int index = 0;
    private LinearLayout layout;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle d = this.getIntent().getExtras();
        int id = d.getInt("id");

        datos = new ArrayList<>();
        obtenerResul = new Button(this);
        obtenerResul.setId(560000000);

        if (id == 1) {
            setContentView(R.layout.captar_num_var_res);
            capturaVarYRes();
            obtenerResul.setOnClickListener(v -> {

                if (validacionDeDatos()) {
                    Snackbar.make(findViewById(obtenerResul.getId()), R.string.rellene_campos, Snackbar.LENGTH_LONG).show();
                }else {
                    datosToMatrixSimplex();
                    Intent intent = new Intent(this,SimplexTableActivity.class);
                    intent.putExtra("matrix_simplex", matrix );
                    startActivity(intent);

                }
            });
        }else if (id == 2){
            setContentView(R.layout.captar_num_res);
            capturarNumRes();
            obtenerResul.setOnClickListener(v -> {

                if (validacionDeDatos()) {
                    Snackbar.make(findViewById(obtenerResul.getId()), R.string.rellene_campos, Snackbar.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(this,GraficoActivity.class);
                    intent.putExtra("datos",datosToArray());
                    intent.putExtra("numRes",numRes);
                    startActivity(intent);
                }
            });
            
        }else if (id == 3){
            setContentView(R.layout.captar_des_origen);
            capturaDesOr();
            obtenerResul.setOnClickListener(v -> {

                if (validacionDeDatos()) {
                    Snackbar.make(findViewById(obtenerResul.getId()), R.string.rellene_campos, Snackbar.LENGTH_LONG).show();
                }else {
                    datosToMatrixTransporte();
                    Intent intent = new Intent(this,EsqSupNoroesteActivity.class);
                    intent.putExtra("matrix", matrix );
                    startActivity(intent);

                }
            });

        }
    }

    private void capturaVarYRes() {
        vars = findViewById(R.id.Vars);
        res = findViewById(R.id.Rest);
        Button siguiente = findViewById(R.id.siguiente);

        siguiente.setOnClickListener(v -> {


            if (vars.getText().toString().equals("") || res.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.siguiente), R.string.rellene_campos, Snackbar.LENGTH_LONG).show();
            } else if (Integer.parseInt(vars.getText().toString()) == 0 || Integer.parseInt(res.getText().toString()) == 0) {
                Snackbar.make(findViewById(R.id.siguiente), R.string.Campos_dif_0, Snackbar.LENGTH_LONG).show();

            }else if (Integer.parseInt(vars.getText().toString()) == 1){
                Snackbar.make(findViewById(R.id.siguiente), R.string.num_mayor_1, Snackbar.LENGTH_LONG).show();

            } else if (Integer.parseInt(vars.getText().toString()) > 10 || Integer.parseInt(res.getText().toString()) > 10){
                Snackbar.make(findViewById(siguiente.getId()), R.string.num_res_menor, Snackbar.LENGTH_LONG).show();
            }else {
                numVar = (Integer.parseInt(vars.getText().toString()));

                numRes = (Integer.parseInt(res.getText().toString()));
                setContentView(R.layout.capturar_datos);
                //prueba(numRes,numVar);
                capturaDatoSimplex();
            }

        });

    }
    @SuppressLint("SetTextI18n")
    private void capturaDatoSimplex() {
        layout = (LinearLayout) findViewById(R.id.captura_datos);
        matrix = new double[numRes + 1][numVar + numRes + 1];
        CapturaDatos capturaDatos = new CapturaDatos(this,numVar,numRes,datos,layout,obtenerResul);

    }
    private boolean validacionDeDatos(){
        boolean aux  = true;
        for (int i = 0; i < datos.size() && aux  ; i++) {
            if (datos.get(i).getText().toString().equals("") || datos.get(i).getText().toString().equals("-") || datos.get(i).getText().toString().equals(".") || datos.get(i).getText().toString().equals("-.")){
                aux = false;
            }
        }
        return !aux;
    }
    private void datosToMatrixSimplex(){

        ArrayList<String> strings = new ArrayList<>();
        int vars = numVar;


        for (int i = 0; i <matrix.length ; i++) {
            matrix[i][matrix[i].length-1] = Double.parseDouble(datos.get(numVar + (i*(numVar+1))).getText().toString());

        }

        for (int i = 0; i <datos.size() ; i++) {
            if (!datos.get(i).getTag().equals("=")){
                strings.add(datos.get(i).getText().toString());
            }

        }


        for (int i = 0; i <matrix.length ; i++) {
            for (int j = 0; j < numVar; j++) {
                matrix[i][j] = Double.parseDouble(strings.get(index));
                index++;
            }
        }


        for (int i = 0; i < matrix[0].length ; i++) {
            matrix[0][i] *= -1;
        }


        for (int i = 1; i <matrix.length ; i++) {
            matrix[i][vars++] = 1;
        }

        index = 0;

    }
    private void capturaDatosGrafico() {
        layout = (LinearLayout) findViewById(R.id.captura_datos);
        int numVar = 2;
        CapturaDatos capturaDatos = new CapturaDatos(this, numVar, numRes, datos, layout, obtenerResul);
    }
    private void capturarNumRes() {
        Button siguienteButton = findViewById(R.id.siguiente2);
        EditText rest = findViewById(R.id.rest);
        siguienteButton.setOnClickListener(v -> {
            if (rest.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.siguiente2), R.string.rellene_campo, Snackbar.LENGTH_LONG).show();
            } else if (Integer.parseInt(rest.getText().toString()) < 2) {
                Snackbar.make(findViewById(R.id.siguiente2), R.string.min_res, Snackbar.LENGTH_LONG).show();
            } else if (Integer.parseInt(rest.getText().toString()) > 10){
                Snackbar.make(findViewById(R.id.siguiente2), R.string.num_max, Snackbar.LENGTH_LONG).show();
            }else {
                numRes = Integer.parseInt(rest.getText().toString());
                setContentView(R.layout.capturar_datos);
                capturaDatosGrafico();
            }
        });
    }
    private double[] datosToArray(){
        double[]aux = new double[datos.size()];
        for (int i = 0; i < datos.size() ; i++) {
            aux[i] = Double.parseDouble(datos.get(i).getText().toString());
        }
        return aux;
    }
    private void capturaDesOr(){
        EditText nOr = findViewById(R.id.numOrigen);
        EditText nDes = findViewById(R.id.numDestino);
        Button siguiente = findViewById(R.id.siguiente3);

        siguiente.setOnClickListener(v -> {
            if (nOr.getText().toString().equals("") || nDes.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.siguiente3), R.string.rellene_campos, Snackbar.LENGTH_LONG).show();
            } else if (Integer.parseInt(nOr.getText().toString()) == 0 || Integer.parseInt(nDes.getText().toString()) == 0) {
                Snackbar.make(findViewById(R.id.siguiente3), R.string.Campos_dif_0, Snackbar.LENGTH_LONG).show();
            } else if (Integer.parseInt(nDes.getText().toString()) > 10 || Integer.parseInt(nOr.getText().toString()) > 10){
                Snackbar.make(findViewById(siguiente.getId()), R.string.num_max_or_des, Snackbar.LENGTH_LONG).show();
            }else {
                setContentView(R.layout.capturar_datos);
                numDestino = Integer.parseInt(nDes.getText().toString());
                numOrigen = Integer.parseInt(nOr.getText().toString());
                capturaDatosTransporte();
            }
        });


    }
    private void capturaDatosTransporte(){
        layout = (LinearLayout) findViewById(R.id.captura_datos);
        datos = new ArrayList<>();
        CapturaDatos capturaDatos = new CapturaDatos(this,datos,layout,obtenerResul,numOrigen,numDestino);

    }
    private void datosToMatrixTransporte(){

        matrix = new double[numOrigen+1][numDestino+1];
        for (int i = 0; i <matrix[0].length -1; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] = Double.parseDouble(datos.get(index).getText().toString());
                index++;
            }
        }

        for (int i = 0; i < matrix.length-1 ; i++) {
            matrix[i][numDestino] = Double.parseDouble(datos.get(index).getText().toString());
            index++;
        }
        index = 0;
    }

}