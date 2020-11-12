package com.raysk.linearprogramming;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.raysk.linearprogramming.graphics.CapturaDatos;
import com.raysk.linearprogramming.graphics.DinamicTable;
import com.raysk.linearprogramming.logic.Simplex;

import java.util.ArrayList;

public class SimplexTableActivity extends AppCompatActivity {
    private EditText vars, res;
    private ArrayList<EditText> datos;
    private Button obtenerResul;
    private double[][] matrix;
    private int numVar, numRes;
    private ArrayList<String[][]> tablas;


    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datos = new ArrayList<>();
        obtenerResul = new Button(this);
        obtenerResul.setId(560000000);
        setContentView(R.layout.captar_num_var_res);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        capturaVarYRes();






        obtenerResul.setOnClickListener(v -> {

            if (!validacionDeDatos()){
                Snackbar.make(findViewById(obtenerResul.getId()), "Rellene todos los campos", Snackbar.LENGTH_SHORT).show();
            }else {
                datosToMatrix();
                setContentView(R.layout.simplex_table);
                Simplex simplex = new Simplex(matrix);
                tablas = simplex.processing();
                presentarTabla();

            }
        });
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
               textView.setText("Tabla inicial");
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
                setNumVar(Integer.parseInt(vars.getText().toString()));
                setNumRes(Integer.parseInt(res.getText().toString()));
                setContentView(R.layout.capturar_datos);
                //prueba(numRes,numVar);
                capturaDatos();
            }

        });

    }

    private void setNumVar(int numVar) {
        this.numVar = numVar;
    }

    public void setNumRes(int res) {
        this.numRes = res;
    }


    @SuppressLint("SetTextI18n")
    private void capturaDatos() {
        matrix = new double[numRes + 1][numVar + numRes + 1];
        LinearLayout layout = findViewById(R.id.captura_datos);
        CapturaDatos capturaDatos = new CapturaDatos(this,numVar,numRes,datos,layout,obtenerResul);
        capturaDatos.capturar();
    }

    private void datosToMatrix(){
    int index = 0;
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
            matrix[i][numVar++] = 1;
        }


        datos.clear();

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



}