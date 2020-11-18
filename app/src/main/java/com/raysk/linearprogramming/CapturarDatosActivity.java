package com.raysk.linearprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    private ArrayList<EditText> datos;
    private Button obtenerResul;
    private int id;
    private double[][] matrix;
    private  int index = 0;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle d = this.getIntent().getExtras();
        id = d.getInt("id");
        datos = new ArrayList<>();
        obtenerResul = new Button(this);
        obtenerResul.setId(560000000);

        if (id == 1) {
            setContentView(R.layout.captar_num_var_res);
            capturaVarYRes();
            obtenerResul.setOnClickListener(v -> {

                if (!validacionDeDatos()){
                    Snackbar.make(findViewById(obtenerResul.getId()), "Rellene todos los campos", Snackbar.LENGTH_SHORT).show();
                }else {
                    datosToMatrix();
                    Intent intent = new Intent(this,SimplexTableActivity.class);
                    index = 0;
                    intent.putExtra("matrix_simplex", matrix );
                    startActivity(intent);

                }
            });
        }else if (id == 2){
            setContentView(R.layout.captar_num_res);
            capturarNumRes();
            obtenerResul.setOnClickListener(v -> {

                if (!validacionDeDatos()) {
                    Snackbar.make(findViewById(obtenerResul.getId()), "Rellene todos los campos", Snackbar.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this,GraficoActivity.class);
                    intent.putExtra("datos",datosToArray());
                    intent.putExtra("numRes",numRes);
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
                Snackbar.make(findViewById(R.id.siguiente), "Rellene todos los campos", Snackbar.LENGTH_SHORT).show();
            } else if (Integer.parseInt(vars.getText().toString()) == 0 || Integer.parseInt(res.getText().toString()) == 0) {
                Snackbar.make(findViewById(R.id.siguiente), "Los campos no pueden valer 0", Snackbar.LENGTH_SHORT).show();

            }else if (Integer.parseInt(vars.getText().toString()) == 1){
                Snackbar.make(findViewById(R.id.siguiente), "El numero de variables debe de ser mayor a 1", Snackbar.LENGTH_SHORT).show();

            } else {
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
        matrix = new double[numRes + 1][numVar + numRes + 1];
        LinearLayout layout = findViewById(R.id.captura_datos);
        CapturaDatos capturaDatos = new CapturaDatos(this,numVar,numRes,datos,layout,obtenerResul);
        capturaDatos.capturar();
    }



    private boolean validacionDeDatos(){
        boolean aux  = true;
        for (int i = 0; i < datos.size() && aux  ; i++) {
            if (datos.get(i).getText().toString().equals("")){
                aux = false;
            }
        }
        return aux;
    }
    private void datosToMatrix(){

        ArrayList<String> strings = new ArrayList<>();


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
            matrix[i][numVar+1] = 1;
        }

        index = 0;

    }




    private void capturaDatos() {
        datos = new ArrayList<>();
        LinearLayout layout = findViewById(R.id.captura_datos);
        int numVar = 2;
        CapturaDatos capturaDatos = new CapturaDatos(this, numVar, numRes, datos, layout, obtenerResul);
        capturaDatos.capturar();
    }

    private void capturarNumRes() {
        Button siguienteButton = findViewById(R.id.siguiente2);
        EditText rest = findViewById(R.id.rest);
        siguienteButton.setOnClickListener(v -> {
            if (rest.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.siguiente2), "Rellene el campo", Snackbar.LENGTH_SHORT).show();
            } else if (Integer.parseInt(rest.getText().toString()) < 2) {
                Snackbar.make(findViewById(R.id.siguiente2), "Se deben utilizar 2 o mas restricciones", Snackbar.LENGTH_SHORT).show();
            } else {
                numRes = Integer.parseInt(rest.getText().toString());
                setContentView(R.layout.capturar_datos);
                capturaDatos();
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

}