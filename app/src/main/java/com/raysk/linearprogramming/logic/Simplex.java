package com.raysk.linearprogramming.logic;

import java.util.ArrayList;

public class Simplex {
    private final double[][] matrix;
    private boolean finish = false;
    private double menor = 0;
    private int colEntrada = 0;
    private double pivote = 0;
    private int renglonSalida = 0;
    private final ArrayList<String[][]> tablas;

    public Simplex(double[][] matrix) {
        this.matrix = matrix;
        tablas = new ArrayList<>();
        //this.z = new int[numVars + 1];
        // this.matrix = new int [numVars + numRestrictions + 1][numRestrictions + 1];
    }

    // metodo de procesamiento que finaliza una ves encontrado el resultado optimo
    public ArrayList<String[][]> processing() {
        while (!this.finish) {
            tablas.add(matrixToString());
            comprobacion();
        }
        return tablas;
    }

    //obtiene el numero mas negativo de la fucion objetivo
    private void obtenerMenor() {
        this.menor = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] < menor) {
                menor = matrix[0][i];
                colEntrada = i;
            }

        }
    }

    //comprueba si en la funcion objetivo hay numeros negativos
    private void comprobacion() {
        this.menor = 0;
        obtenerMenor();

        if (this.menor < 0) {
            operacion();
        } else {
            finish = true;

        }
    }

    // se obtiene el pivote
    private void obtenerPivote() {
        double[] resultados = new double[matrix.length - 1];
        for (int i = 1; i < matrix.length; i++) {
            resultados[i - 1] = matrix[i][matrix[i].length - 1] / matrix[i][colEntrada];
            if (resultados[i - 1] < 0) {
                resultados[i - 1] = 0;
            }
        }


        this.menor = 0;
        for (int i = 0; i < resultados.length; i++) {
            if (this.menor == 0) {
                menor = resultados[i];
            }
            if (resultados[i] <= this.menor && resultados[i] > 0) {
                menor = resultados[i];
                renglonSalida = i + 1;
            }
        }
        this.pivote = matrix[renglonSalida][colEntrada];

    }

    // realiza los calculos en la matriz
    private void operacion() {
        obtenerPivote();

        // se convierte el pivote en 1
        for (int i = 0; i < matrix[0].length; i++) {
            this.matrix[renglonSalida][i] /= this.pivote;

        }


        //realiza las operaciones correspondientes en la matriz para
        //convertir los elementos arriba o debajo del pivote en 0
        for (int i = 0; i < matrix.length; i++) {
            double aux, aux2;
            if (i != this.renglonSalida) {
                aux = -matrix[i][colEntrada];
                for (int j = 0; j < matrix[i].length; j++) {
                    aux2 = ((matrix[renglonSalida][j] * aux));
                    matrix[i][j] += aux2;
                }
            }

        }

    }

    private String[][] matrixToString() {
        String[][] string = new String[matrix.length][matrix[0].length];
        for (int i = 0; i < string.length; i++) {
            for (int j = 0; j < string[i].length; j++) {
                string[i][j] = Double.toString(matrix[i][j]);
            }
        }

        return string;
    }


    //dibuja matriz (temporal)


}