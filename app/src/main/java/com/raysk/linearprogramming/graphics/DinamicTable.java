package com.raysk.linearprogramming.graphics;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DinamicTable {
    private final TableLayout tableLayout;
    private final Context context;
    private final String[] header;
    private final String[][] matrix;
    private final String[][] data;
    private TableRow tableRow;
    private TextView textCell;
    private int  vars;
    private final int height = 130;
    private final int widht = 250;

    public DinamicTable(TableLayout tableLayout, Context context, String[][] matrix) {
        this.tableLayout = tableLayout;
        this.context = context;
        this.matrix = matrix;
        vars = matrix.length - vars - 1;
        header = new String[matrix[0].length + 1];
        data = new String[matrix.length][matrix[0].length + 1];
        setHeader();


    }

    public void get() {
        createHeader();
        setDataTable();
        createDataTable();
    }

    private void setHeader() {

        header[0] = "";
        header[header.length - 1] = "HBi";
        int i = 1;

        for (int j = 1; j <= vars; j++) {
            header[i] = "X" + j;
            i++;

        }
        for (int j = 1; j <= vars; j++) {
            header[i] = "h" + j;
            i++;

        }
    }


    private void newRow() {
        tableRow = new TableRow(context);
    }

    private void newCell() {
        textCell = new TextView(context);
        textCell.setGravity(Gravity.CENTER);

        textCell.setTextSize(30);

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
                textCell.setWidth(widht);
                textCell.setHeight(height);
                textCell.setText(datum[j]);
                if (j == 0) {
                    textCell.setBackgroundColor(Color.RED);
                } else {
                    textCell.setBackgroundColor(Color.WHITE);
                }
                tableRow.addView(textCell, newTableRomParams());

            }
            tableLayout.addView(tableRow);
        }
    }

    private void setDataTable() {
        data[0][0] = "Zj - Cj";
        for (int i = 1; i < data.length; i++) {
            data[i][0] = "h" + i;
        }

        for (int i = 0; i < data.length; i++) {
            if (data[0].length - 1 >= 0)
                System.arraycopy(matrix[i], 0, data[i], 1, data[0].length - 1);
        }
    }

    private TableRow.LayoutParams newTableRomParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(10, 10, 10, 10);
        params.weight = 50;
        return params;
    }

    //convierte decimales a fracciones

}

