package com.raysk.linearprogramming.graphics;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class DinamicTable {
    private final TableLayout tableLayout;
    private final Context context;
    private  String[] header;
    private final String[][] matrix;
    private  String[][] data;
    private TableRow tableRow;
    private TextView textCell;
    private int rests;
    private int vars;
    private  int height;
    private int widht;
    private String[][] asignacion;
    private int textSize;
    private ArrayList<TextView> datos;
    private boolean ofertaF, demandaF;

    public DinamicTable(TableLayout tableLayout, Context context, String[][] matrix) {
        this.tableLayout = tableLayout;
        datos = new ArrayList<>();
        height = 180;
        widht = 300;
        textSize = 20;
        this.context = context;
        this.matrix = matrix;
        rests = matrix.length - 1;
        vars = matrix[0].length - rests -1;
        header = new String[matrix[0].length + 1];
        data = new String[matrix.length][matrix[0].length + 1];
        redondear();
        setHeaderSimplex();
        createHeader();
        setDataTableSimplex();
        createDataTable();
    }

    public DinamicTable(TableLayout tableLayout, Context context, String[][] matrix, String[][] asignacion, String total,boolean ofertaF, boolean demandaF){
        height = 180;
        datos = new ArrayList<>();
        textSize = 20;
        widht = 300;
        this.demandaF = demandaF;
        this.ofertaF = ofertaF;
        this.tableLayout = tableLayout;
        this.context = context;
        this.matrix = matrix;
        this.asignacion = asignacion;
        header = new String[matrix[0].length + 1];
        data = new String[matrix.length][matrix[0].length + 1];
        setHeaderTransporte();
        createHeader();
        setDataTableTransporte();
        data[matrix.length-1][matrix[0].length] = total;
        createDataTable();
        setColor();

    }

    private void setColor() {
        for (int i = 0; i < datos.size() ; i++) {
            if (datos.get(i).getText().toString().contains("(")){
                datos.get(i).setBackgroundColor(Color.argb(74,70,230,230));
            }
        }
    }


    private void setHeaderSimplex() {

        header[0] = "";
        header[header.length - 1] = "HBi";
        int i = 1;

        for (int j = 1; j <= vars; j++) {
            header[i] = "X" + j;
            i++;

        }
        for (int j = 1; j <= rests; j++) {
            header[i] = "h" + j;
            i++;

        }
    }

    private void setHeaderTransporte(){
        header[0] = "";
        header[header.length - 1] = "Oferta";
        for (int i = 1; i < header.length - 1 ; i++) {
            header[i] = "Destino " + i;
        }
        if (demandaF){
            header[header.length - 2] += "(F)";
        }

    }


    private void newRow() {
        tableRow = new TableRow(context);
    }

    private void newCell() {
        textCell = new TextView(context);
        textCell.setGravity(Gravity.CENTER);


        textCell.setTextSize(textSize);

    }

    private void createHeader() {
        newRow();
        for (int i = 0; i < matrix[0].length + 1; i++) {
            newCell();
            textCell.setText(header[i]);
            textCell.setWidth(widht);
            textCell.setHeight(height);
            textCell.setBackgroundColor(Color.RED);

            tableRow.addView(textCell, newTableRomParams());
        }
        tableLayout.addView(tableRow);
    }

    private void createDataTable() {
        for (String[] datum : data) {
            newRow();
            for (int j = 0; j < data[0].length; j++) {
                newCell();
                //textCell.setWidth(widht);
                textCell.setHeight(height);
                textCell.setBackgroundColor(Color.argb(70,230,70,210));
                textCell.setText(datum[j]);

                if (j == 0) {
                    textCell.setBackgroundColor(Color.RED);
                }else {
                    datos.add(textCell);
                }
                tableRow.addView(textCell, newTableRomParams());

            }
            tableLayout.addView(tableRow);
        }
    }

    private void setDataTableSimplex() {
        data[0][0] = "Zj - Cj";
        for (int i = 1; i < data.length; i++) {

                data[i][0] = "h" + i;
        }

        for (int i = 0; i < data.length; i++) {
            if (data[0].length - 1 >= 0) {
                System.arraycopy(matrix[i], 0, data[i], 1, data[0].length - 1);
            }
        }
    }

    private void setDataTableTransporte(){
        for (int i = 0; i < data.length; i++) {
            if (i == data.length-1){
                data[i][0] = "Demanda";
            }else {
                data[i][0] = "Orígen " + (i+1);
            }

        }
        if (ofertaF){
            data[data.length-2][0] += "(F)";
        }

        int cont = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length-1; j++) {
                if (asignacion[i][j].equals("0.0")){
                    data[i][j+1] = matrix[i][j];
                }else {
                    if (asignacion[i][j].equals("Infinity")) {
                        asignacion[i][j] = "0.0";
                    }
                    data[i][j+1] = matrix[i][j] +" ("+asignacion[i][j]+")";
                }

            }
        }

    }

    private TableRow.LayoutParams newTableRomParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(8, 8, 8, 8);
       // params.weight = 50000;
        return params;
    }

    private void redondear(){
        for (int i = 0; i <matrix.length ; i++) {
            for (int j = 0; j <matrix[i].length ; j++) {
                double aux = Double.parseDouble(matrix[i][j]);
                aux = Math.round(aux*1000);
                aux /= 1000;
                matrix[i][j] = Double.toString(aux);
            }

        }
    }
}

