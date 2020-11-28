package com.raysk.linearprogramming.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class CapturaDatos {
    private int numRes, numVar, numOrigen, numDestino;
    private final Context context;
    private final Button obtenerResul;
    private final ArrayList<EditText> datos;
    private final LinearLayout layout;
    private final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
    private InputFilter[] filterArray = new InputFilter[1];
    private boolean transporte;

    public CapturaDatos(Context context, int numVar, int numRes, ArrayList<EditText> datos, LinearLayout layout, Button obtenerResul) {
        this.context = context;
        this.numVar = numVar;
        this.numRes = numRes;
        this.datos = datos;
        this.layout = layout;
        this.obtenerResul = obtenerResul;
        filterArray[0] = new InputFilter.LengthFilter(8);
        capturar();
    }

    public CapturaDatos(Context context, ArrayList<EditText> datos, LinearLayout layout, Button obtenerResul, int numOrigen, int numDestino) {
        this.context = context;
        this.numOrigen = numOrigen;
        this.numDestino = numDestino;
        this.datos = datos;
        this.layout = layout;
        this.obtenerResul = obtenerResul;
        filterArray[0] = new InputFilter.LengthFilter(8);
        capturarMTransporte();
    }


    @SuppressLint("SetTextI18n")
    private void capturar() {
        int index;
        layout.setOrientation(VERTICAL);  //Can also be done in xml by android:orientation="vertical"


        for (int k = 0; k < numRes + 1; k++) {
            index = 1;
            LinearLayout res = new LinearLayout(context);
            res.setLayoutParams(params);
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
                row.setLayoutParams(params);
                for (int j = 0; j < 2; j++) {

                    if (j == 0) {
                        TextView view = new TextView(context);
                        view.setTextSize(30);
                        view.setGravity(Gravity.CENTER);
                        view.setWidth(300);
                        view.setHeight(250);
                        if (i == numVar) {
                            view.setText("<=");
                            if (k == 0) {
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
                        text.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT));

                        text.setFilters(filterArray);
                        if (i == numVar && k == 0) {
                            text.setText("0");
                            text.setVisibility(View.INVISIBLE);
                            text.setHeight(1);
                            row.setLayoutParams(new LinearLayout.LayoutParams(1, 1));
                        }
                        text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        text.setWidth(300);
                        text.setGravity(Gravity.CENTER);
                        if (i == numVar) {
                            text.setTag("=");
                            text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        } else {
                            text.setTag("var");
                            //text.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);

                        }
                        text.setTextSize(30);
                        row.addView(text);
                        datos.add(text);
                    }

                }


                layout.addView(row);

            }

        }

        button();


    }

    @SuppressLint("SetTextI18n")
    private void button() {
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

    @SuppressLint("SetTextI18n")
    private void capturarMTransporte() {
        for (int i = 1; i <= numDestino; i++) {
            LinearLayout res = new LinearLayout(context);
            res.setLayoutParams(params);
            TextView textView = new TextView(context);
            textView.setTextSize(50);
            textView.setBackgroundColor(Color.GREEN);
            textView.setLayoutParams(params);
            textView.setText("Destino " + i);

            res.addView(textView);
            layout.addView(res);

            for (int j = 1; j <= numOrigen + 1; j++) {
                LinearLayout row = new LinearLayout(context);
                row.setLayoutParams(params);
                for (int k = 0; k < 2; k++) {
                    if (k == 0) {
                        TextView view = new TextView(context);
                        view.setTextSize(30);
                        view.setGravity(Gravity.CENTER);
                        view.setWidth(370);
                        view.setHeight(250);

                        if (j == numOrigen + 1) {
                            view.setText("Demanda:");
                        } else {
                            view.setText("Orígen " + j + ":");
                        }

                        row.addView(view);
                    } else {
                        EditText text = new EditText(context);
                        text.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT));

                        text.setFilters(filterArray);
                        text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        text.setWidth(300);
                        text.setGravity(Gravity.CENTER);
                        text.setTextSize(30);
                        row.addView(text);
                        datos.add(text);
                    }
                }
                layout.addView(row);
            }
        }
        LinearLayout res = new LinearLayout(context);
        res.setLayoutParams(params);
        TextView textView = new TextView(context);
        textView.setTextSize(50);
        textView.setBackgroundColor(Color.GREEN);
        textView.setLayoutParams(params);
        textView.setText("Oferta");
        res.addView(textView);
        layout.addView(res);
        for (int i = 1; i <= numOrigen; i++) {
            LinearLayout row = new LinearLayout(context);
            row.setLayoutParams(params);
            for (int k = 0; k < 2; k++) {
                if (k == 0) {
                    TextView view = new TextView(context);
                    view.setTextSize(30);
                    view.setGravity(Gravity.CENTER);
                    view.setWidth(370);
                    view.setHeight(250);
                    view.setText("Orígen " + i + ":");
                    row.addView(view);
                } else {
                    EditText text = new EditText(context);
                    text.setLayoutParams(new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT));
                    text.setFilters(filterArray);
                    text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    text.setWidth(300);
                    text.setGravity(Gravity.CENTER);
                    text.setTextSize(30);
                    row.addView(text);
                    datos.add(text);
                }
            }
            layout.addView(row);
        }
        button();
    }
}
