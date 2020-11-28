package com.raysk.linearprogramming.logic;

public class EsqSupNoroeste {
    private double[][] matrix;
    private final double[][] tabla;
    private final double[][] asignacion;
    private final int iDemanda;
    private final int iOferta;
    private boolean ofertaF = false;
    private boolean demandaF = false;
    private double oferta = 0;
    private double demanda = 0;
    private String[][] stringMatrix;


    public EsqSupNoroeste(double[][] matrix) {
        this.matrix = matrix;
        comparacionOfertaDemanda();
        matrixToString();
        asignacion = new double[this.matrix.length][this.matrix[0].length];
        iDemanda = this.matrix.length - 1;
        iOferta = this.matrix[0].length - 1;
        tabla = this.matrix;
        procesar();
    }

    private void comparacionOfertaDemanda() {

        double[][] aux;
        for (int i = 0; i < matrix[0].length; i++) {
            demanda += matrix[matrix.length - 1][i];
        }

        for (double[] doubles : matrix) {
            oferta += doubles[matrix[0].length - 1];
        }

        if (oferta > demanda) {
            double diferencia = oferta - demanda;
            aux = new double[matrix.length][matrix[0].length + 1];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (j == matrix[i].length - 1) {
                        aux[i][j + 1] = matrix[i][j];
                    } else {
                        aux[i][j] = matrix[i][j];
                    }

                }
            }
            aux[aux.length - 1][aux[0].length - 2] = diferencia;
            demandaF = true;
            matrix = aux;
        } else if (demanda > oferta) {
            double diferencia = demanda - oferta;
            aux = new double[matrix.length + 1][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (i == matrix.length - 1) {
                        aux[i + 1][j] = matrix[i][j];
                    } else {
                        aux[i][j] = matrix[i][j];
                    }

                }
            }
            aux[aux.length - 2][aux[0].length - 1] = diferencia;
            ofertaF = true;
            matrix = aux;
        }
    }

    private void procesar() {
        int i = 0;
        int j = 0;
        boolean flag = false;
        while (!flag) {
            if (i == iDemanda || j == iOferta) {
                flag = true;
            } else {

                if (tabla[i][iOferta] > tabla[iDemanda][j]) {
                    tabla[i][iOferta] -= tabla[iDemanda][j];
                    asignacion[i][j] = tabla[iDemanda][j];
                    if (asignacion[i][j] == 0) {
                        asignacion[i][j] = Double.POSITIVE_INFINITY;
                    }
                    j++;
                } else if (tabla[iDemanda][j] > tabla[i][iOferta]) {
                    tabla[iDemanda][j] -= tabla[i][iOferta];
                    asignacion[i][j] = tabla[i][iOferta];
                    if (asignacion[i][j] == 0) {
                        asignacion[i][j] = Double.POSITIVE_INFINITY;
                    }
                    i++;
                } else {
                    asignacion[i][j] = tabla[iDemanda][j];
                    if (asignacion[i][j] == 0) {
                        asignacion[i][j] = Double.POSITIVE_INFINITY;
                    }
                    tabla[iDemanda][j] = 0;
                    tabla[i][iOferta] = 0;
                    i++;
                }


            }
        }
    }
    public String[][] getAsignacion(){
        String[][] strings = new String[asignacion.length][asignacion[0].length];
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length ; j++) {
                strings[i][j] = Double.toString(asignacion[i][j]);
            }
        }
        return strings;
    }
    private void matrixToString(){
        stringMatrix = new String[matrix.length][matrix[0].length];
        for (int i = 0; i < stringMatrix.length; i++) {
            for (int j = 0; j < stringMatrix[i].length ; j++) {
                stringMatrix[i][j] = Double.toString(matrix[i][j]);
            }
        }
    }

    public String[][] getStringMatrix(){
        return stringMatrix;
    }

    public String getTotal(){
        if (oferta > demanda){
            return oferta + "/" + oferta;
        }else {
            return demanda + "/" + demanda;
        }

    }

    public boolean getOfertaF(){
        return ofertaF;
    }

    public  boolean getDemandaF(){
        return demandaF;
    }

}
