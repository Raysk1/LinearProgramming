package com.raysk.linearprogramming;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raysk.linearprogramming.graphics.DinamicTable;
import com.raysk.linearprogramming.logic.Simplex;

import java.util.ArrayList;

public class SimplexTableActivity extends AppCompatActivity {
    private ArrayList<String[][]> tablas;
    private int rests;
    private int vars;
    double[][] matrix;
    private ArrayList<String[]> resultado = new ArrayList<>();

    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle d = this.getIntent().getExtras();
        matrix = (double[][]) d.get("matrix_simplex");
        rests = matrix.length - 1;
        vars = matrix[0].length - rests - 1;


        setContentView(R.layout.simplex_table);
        Simplex simplex = new Simplex(matrix);
        tablas = simplex.processing();
        presentarTabla();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    @SuppressLint("SetTextI18n")
    private void presentarTabla() {
        LinearLayout linearLayout;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        TextView textView;
        TableLayout tableLayout = findViewById(R.id.tabla);
        for (int i = 0; i < tablas.size() + 1; i++) {
            linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(params);

            textView = new TextView(this);
            textView.setTextSize(50);
            textView.setLayoutParams(params);
            textView.setBackgroundColor(Color.GREEN);
            if (i == 0) {
                textView.setText("Tabla Inicial");
            } else if (i == tablas.size() - 1) {
                textView.setText("Tabla Resultado");
            } else if (i == tablas.size()) {
                textView.setText("Resultado");
            } else {
                textView.setText("Tabla " + i);
            }
            linearLayout.addView(textView);
            tableLayout.addView(linearLayout);
            if (i < tablas.size()) {
                DinamicTable dinamicTable = new DinamicTable(tableLayout, this, tablas.get(i));
                dinamicTable.get();
            } else {
                encotrarResultado();
                for (int j = 0; j < resultado.size(); j++) {
                    LinearLayout layout = new LinearLayout(this);
                    layout.setLayoutParams(params);
                    TextView text = new TextView(this);
                    TextView resul = new TextView(this);
                    //text.setLayoutParams(params);
                    resul.setLayoutParams(params);
                    text.setGravity(Gravity.END);
                    text.setTextColor(Color.RED);
                    resul.setTextColor(Color.BLUE);
                    text.setText(resultado.get(j)[0]);
                    resul.setText(resultado.get(j)[1]);
                    text.setTextSize(50);
                    resul.setTextSize(50);
                    layout.addView(text);
                    layout.addView(resul);
                    tableLayout.addView(layout);
                }

            }
        }
    }

    private void encotrarResultado() {
        llenarTexto();
        boolean[] primer = new boolean[matrix.length];
        ArrayList<Integer> puntos = new ArrayList<>();

        for (int i = 0; i < matrix[0].length - 1; i++) {
            boolean flag = true;
            boolean unoEncontrado = false;
            int posJ = 0, posI = 0;
            boolean aux = true;
            int cont = 0;

            for (int j = 0; j < matrix.length && flag; j++) {
                if (matrix[j][i] != 1 && matrix[j][i] != 0) {
                    flag = false;
                } else if (matrix[j][i] == 1) {
                    posJ = j;
                    cont++;
                    unoEncontrado = true;
                }
            }

            boolean ok = false;
            if (cont == 1 && flag) {
                for (int k = 0; k < matrix[0].length - 1 && aux; k++) {
                    if (comprobar(puntos, posJ)) {
                        if (k != i) {
                            if (matrix[posJ][k] == 1) {
                                ok = true;
                                if (!primer[posJ]) {
                                    primer[posJ] = true;
                                    puntos.add(posJ);
                                    posI = i;
                                    ok = true;
                                }else {
                                    aux = false;
                                }
                            }else if (!ok){
                                primer[posJ] = true;
                                puntos.add(posJ);
                                posI = i;
                                ok = true;
                            }
                        }
                    }else {
                        aux = false;
                    }
                }

            }


            if (flag && unoEncontrado && ok) {
                String[] strings = new String[2];
                posI++;
                if (posI <= vars) {
                    strings[0] = " X" + posI + ": ";
                } else {
                    strings[0] = " h" + (posI - vars) + ": ";
                }
                double v = matrix[posJ][matrix[0].length - 1];
                v = Math.round(v*1000);
                v /= 1000;
                strings[1] = Double.toString(v);
                resultado.remove(posI-1);
                resultado.add(posI-1,strings);
            }
        }
    }

    private boolean comprobar(ArrayList<Integer> integers, int i) {

        if (integers.size() == 0) {
            return true;
        } else {
            for (int j = 0; j < integers.size(); j++) {
                if (integers.get(j) == i) {
                    return false;
                }
            }
        }
        return true;
    }

    private void llenarTexto(){
        for (int i = 0; i < matrix[0].length ; i++) {
            String[] strings = new String[2];
            strings[1] = "0.0";
            if (i == matrix[0].length-1){
                strings[0] = " Z: ";
                double v = matrix[0][matrix[0].length - 1];
                v = Math.round(v*1000);
                v /= 1000;
                strings[1] =Double.toString(v);
            }else if (i < vars) {
                strings[0] = " X" + (i+1) + ": ";
            } else {
                strings[0] = " h" + (i+1 - vars) + ": ";
            }
            resultado.add(strings);
        }
    }

}