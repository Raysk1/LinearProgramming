package com.raysk.linearprogramming.logic;

import java.util.ArrayList;

public class Simplex {
    private double z;
    private double matrix[][] = { { -2000, -2400, 0, 0, 0 }, { 6, 12, 1, 0, 120 }, { 8, 4, 0, 1, 64 } };
    private int numRestrictions = 0;
    private int numVars = 0;
    private boolean finish = false;
    private double menor = 0;
    private int colEntrada = 0;
    private double pivote = 0;
    private int renglonSalida = 0;
    private ArrayList<double[][]> tablas;

    public Simplex(double[][] matrix) {
       // this.matrix = matrix;
        this.numVars = numVars;
        this.numRestrictions = numRestrictions;
        tablas = new ArrayList<double[][]>();
        processing();
        //this.z = new int[numVars + 1];
        // this.matrix = new int [numVars + numRestrictions + 1][numRestrictions + 1];

    }

    // metodo de procesamiento que finaliza una ves encontrado el resultado optimo
    public void processing() {
        while (!this.finish) {
            tablas.add(matrix);
            comprobacion();
        }
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
            operacion(matrix);
        } else {
            finish = true;

        }
    }

    // se obtiene el pivote
    private void obtenerPivote() {
        double resultados[] = new double[matrix.length - 1];
        for (int i = 1; i < matrix.length; i++) {
            resultados[i - 1] = matrix[i][matrix[i].length - 1] / matrix[i][colEntrada];
            if(resultados[i-1] < 0) {
                resultados[i-1] = 0;
            }
        }


        this.menor = 0;
        for (int i = 0; i < resultados.length; i++) {
            if(this.menor == 0) {
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
    private void operacion(double[][] matrix) {
        obtenerPivote();

        // se convierte el pivote en 1
        for (int i = 0; i < matrix[0].length; i++) {
            this.matrix[renglonSalida][i] /= this.pivote;

        }


        //realiza las operaciones correspondientes en la matriz para
        //convertir los elementos arriba o debajo del pivote en 0
        for (int i = 0; i < matrix.length; i++) {
            double aux,aux2 = 0;
            if (i != this.renglonSalida) {
                aux = -matrix[i][colEntrada];
                for (int j = 0; j < matrix[i].length; j++) {
                    aux2 = ((matrix[renglonSalida][j]*aux));
                    matrix[i][j] += aux2;
                }
            }

        }

    }

    public ArrayList<double[][]> getTablas(){
        return tablas;
    }


    //dibuja matriz (temporal)


}