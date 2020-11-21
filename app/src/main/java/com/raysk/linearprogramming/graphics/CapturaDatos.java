package com.raysk.linearprogramming.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raysk.linearprogramming.R;

import java.util.ArrayList;

public class CapturaDatos{
    private int numRes,numVar;
    private Context context;
    private Button obtenerResul;
    private ArrayList<EditText> datos;
    LinearLayout layout ;

    public CapturaDatos(Context context, int numVar,int numRes, ArrayList<EditText> datos,LinearLayout layout, Button obtenerResul) {
        this.context = context;
        this.numVar =numVar;
        this.numRes = numRes;
        this.datos = datos;
        this.layout =layout;
        this.obtenerResul = obtenerResul;

    }


    @SuppressLint("SetTextI18n")
    public void capturar(){
        int index;
        layout.setOrientation(LinearLayout.VERTICAL);  //Can also be done in xml by android:orientation="vertical"
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);



        for (int k = 0; k < numRes + 1; k++) {
            index = 1;
            LinearLayout res = new LinearLayout(context);
            res.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            TextView textView = new TextView(context);
            textView.setTextSize(50);
            textView.setBackgroundColor(Color.GREEN);
            textView.setLayoutParams(params);


            if (k == 0) {
                textView.setText("Función Objetivo:");
            } else {
                textView.setText("Restricción " + k + ":");
            }
            res.addView(textView);

            layout.addView(res);

            for (int i = 0; i < numVar + 1; i++) {
                LinearLayout row = new LinearLayout(context);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 2; j++) {

                    if (j == 0) {
                        TextView view = new TextView(context);
                        view.setTextSize(30);
                        view.setGravity(Gravity.CENTER);
                        view.setWidth(300);
                        view.setHeight(250);
                        if (i == numVar) {
                            view.setText("<=");
                            if (k== 0){
                                view.setVisibility(View.INVISIBLE);
                                view.setHeight(1);
                            }
                        } else {
                            view.setText("X" + (index) + " =");
                            index++;
                        }
                        row.addView(view);
                    } else {
                        EditText text = new EditText(context);

                        if (i == numVar && k == 0){
                            text.setText("0");
                            text.setVisibility(View.INVISIBLE);
                            text.setHeight(1);
                            row.setLayoutParams(new LinearLayout.LayoutParams(1,1));
                        }
                        text.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);
                        text.setWidth(300);
                        text.setGravity(Gravity.CENTER);
                        if (i == numVar){
                            text.setTag("=");
                            text.setInputType(InputType.TYPE_CLASS_NUMBER);
                        }else {
                            text.setTag("var");
                            text.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);

                        }
                        text.setTextSize(30);
                        row.addView(text);
                        datos.add(text);
                    }

                }


                layout.addView(row);

            }

        }

        obtenerResul.setTextSize(40);
        obtenerResul.setBackgroundColor(Color.CYAN);
        obtenerResul.setLayoutParams(params);
        obtenerResul.setText("Obtener Resultado");
        LinearLayout res = new LinearLayout(context);
        LinearLayout row = new LinearLayout(context);
        row.addView(obtenerResul);
        res.addView(row);
        layout.addView(res);


    }
}
