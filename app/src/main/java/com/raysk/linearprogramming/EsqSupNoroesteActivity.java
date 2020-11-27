package com.raysk.linearprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.raysk.linearprogramming.graphics.DinamicTable;
import com.raysk.linearprogramming.logic.EsqSupNoroeste;

public class EsqSupNoroesteActivity extends AppCompatActivity {
    private double[][] matrix;
    private String[][] stringMatrix;
    private String[][] asignacion;
    private String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        Bundle d = this.getIntent().getExtras();
        matrix = (double[][]) d.get("matrix");
        EsqSupNoroeste supNoroeste = new EsqSupNoroeste(matrix);
        asignacion = supNoroeste.getAsignacion();
        stringMatrix = supNoroeste.getStringMatrix();
        total = supNoroeste.getTotal();
        presentarTabla();
        
    }
    
    private void presentarTabla(){
        LinearLayout linearLayout;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        TextView textView;
        TableLayout tableLayout = findViewById(R.id.tabla);

        for (int i = 0; i < 3 ; i++) {
            linearLayout = new LinearLayout(this);
            textView = new TextView(this);
            textView.setTextSize(50);
            textView.setLayoutParams(params);
            textView.setBackgroundColor(Color.GREEN);
            linearLayout.addView(textView);
            tableLayout.addView(linearLayout);
            if (i == 0){
                textView.setText(R.string.tabla);
                DinamicTable dinamicTable = new DinamicTable(tableLayout,this, stringMatrix,asignacion,total);
            }else if (i ==1 ){
                textView.setText(R.string.resultado);
            }else {
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setBackgroundColor(Color.TRANSPARENT);
                textView.setTextSize(50);
                textView.setTextColor(Color.RED);
                textView.setText("Z: ");
                TextView textView1 = new TextView(this);
                textView1.setTextColor(Color.BLUE);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                textView1.setTextSize(50);
                textView1.setText(getSuma());
                textView1.setGravity(Gravity.END);
                linearLayout.addView(textView1);
            }

        }
    }

    private String getSuma(){
        double suma = 0;
        for (int i = 0; i < asignacion.length; i++) {
            for (int j = 0; j < asignacion[i].length ; j++) {
                if (Double.isInfinite(Double.parseDouble(asignacion[i][j]))){
                    suma += 0;
                }else if (!asignacion[i][j].equals("0.0")){
                    suma += Double.parseDouble(asignacion[i][j]) * Double.parseDouble(stringMatrix[i][j]);
                }

            }
        }


        return  Double.toString(suma);
    }

}