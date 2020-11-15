package com.raysk.linearprogramming;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.raysk.linearprogramming.graphics.CapturaDatos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GraficoActivity extends AppCompatActivity {
    private int numRes = 1;
    private int x1, x2;
    private double[] resX1, resX2, resReul;
    private Button obtenerResul;
    private ArrayList<EditText> datos;
    private final ArrayList<DataPoint[]> restricciones = new ArrayList<>();
    private final ArrayList<DataPoint[]> funciones = new ArrayList<>();
    private ArrayList<DataPoint> intersecciones = new ArrayList<>();
    private int[] colors;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captar_num_res);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        obtenerResul = new Button(this);
        obtenerResul.setId(851254);


        obtenerResulButton();

        capturarNumRes();
    }

    @SuppressLint("SetTextI18n")
    private void siguienteButton() {
        Button siguiente = findViewById(R.id.siguiente3);
        double[] res = getX1X2Z();


        siguiente.setOnClickListener(v -> {
            setContentView(R.layout.resultados_grafica);
            TextView x1, x2, z;
            x1 = findViewById(R.id.x1);
            x2 = findViewById(R.id.x2);
            z = findViewById(R.id.z);
            x1.setText("X1: " + res[0]);
            x2.setText("X2: " + res[1]);
            z.setText("Z: " + res[2]);
        });

    }

    private double[] getX1X2Z() {
        double mayor = 0;
        int indice = 0;
        double z;
        for (int i = 0; i < intersecciones.size(); i++) {
            z = x1 * intersecciones.get(i).getX() + x2 * intersecciones.get(i).getY();
            if (z > mayor) {
                mayor = z;
                indice = i;
            }
        }
        double[] res = new double[3];
        res[0] = intersecciones.get(indice).getX();
        res[1] = intersecciones.get(indice).getY();
        res[2] = x1 * intersecciones.get(indice).getX() + x2 * intersecciones.get(indice).getY();

        return res;
    }

    private void obtenerResulButton() {
        obtenerResul.setOnClickListener(v -> {

            if (!validacionDeDatos()) {
                Snackbar.make(findViewById(obtenerResul.getId()), "Rellene todos los campos", Snackbar.LENGTH_SHORT).show();
            } else {
                datosToArray();
                datosToGrafica();
            }
        });
    }

    private void datosToArray() {
        x1 = Integer.parseInt(datos.get(0).getText().toString());
        x2 = Integer.parseInt(datos.get(1).getText().toString());
        resX1 = new double[numRes];
        resX2 = new double[numRes];
        resReul = new double[numRes];

        int cont = 0;
        for (int i = 3; i < datos.size(); i += 3) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    resX1[cont] = Double.parseDouble(datos.get(i + j).getText().toString());
                } else if (j == 1) {
                    resX2[cont] = Double.parseDouble(datos.get(i + j).getText().toString());
                } else {
                    resReul[cont] = Double.parseDouble(datos.get(i + j).getText().toString());
                }
            }
            cont++;
        }


        for (int i = 0; i < resReul.length; i++) {
            DataPoint[] points = new DataPoint[2];
            for (int j = 0; j < 2; j++) {
                double aux, x, y;

                if (j == 0) {
                    aux = resReul[i] / resX1[i];
                } else {
                    aux = resReul[i] / resX2[i];
                }


                if (j == 0) {
                    x = aux;
                    y = 0.0;
                } else {
                    y = aux;
                    x = 0.0;
                }


                points[j] = new DataPoint(x, y);
            }

            restricciones.add(revertir(points));
        }


        for (int i = 0; i < restricciones.size(); i++) {
            DataPoint[] point = new DataPoint[2];
            double x = maxX();
            double y = maxY();
            if (Double.isInfinite(restricciones.get(i)[0].getX())) {
                point[0] = new DataPoint(restricciones.get(i)[0].getX(), restricciones.get(i)[0].getY());
                point[1] = new DataPoint((resReul[i] - (resX2[i] * y)) / resX1[i], y);
            } else if (Double.isInfinite(restricciones.get(i)[0].getY())) {
                point[0] = new DataPoint(restricciones.get(i)[1].getX(), restricciones.get(i)[1].getY());
                //cambiar por getMaxX
                point[1] = new DataPoint((resReul[i] - (resX2[i] * y)) / resX1[i], y);
            } else {
                point[0] = new DataPoint(restricciones.get(i)[0].getX(), restricciones.get(i)[0].getY());
                //cambiar por getMaxX
                point[1] = new DataPoint(x, (resReul[i] - (resX1[i] * x)) / resX2[i]);
            }
            funciones.add(point);
        }


    }


    private void datosToGrafica() {
        setContentView(R.layout.grafica);
        getRandomColors();
        getIntersecciones();
        GraphView graph = (GraphView) findViewById(R.id.graph);
        //graph.getViewport().setScrollable(true);
        //graph.getViewport().setScalable(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(maxX());
        graph.getViewport().setMaxY(maxY());

        //graph.getViewport().setScalableY(true);
        //graph.getSecondScale();

        for (int i = 0; i < funciones.size(); i++) {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(funciones.get(i));
            series.setColor(colors[i]);
            //series.setDrawDataPoints(true);
            series.setTitle("R" + (i + 1));
            //series.setDataPointsRadius ( 20 );

            graph.addSeries(series);
        }

        DataPoint[] points = interDataPoints();
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(points);
        graph.addSeries(series);

        LineGraphSeries<DataPoint> areaSolucion = new LineGraphSeries<>(points);
        areaSolucion.setBackgroundColor(Color.argb(145, 63, 190, 63));
        areaSolucion.setDrawBackground(true);
        areaSolucion.setColor(Color.TRANSPARENT);
        graph.addSeries(areaSolucion);
        siguienteButton();


        //series.setBackgroundColor(Color.argb(145,63,190,63));


    }

    private void getIntersecciones() {
        for (int i = 0; i < resX1.length; i++) {
            for (int j = i; j < resX1.length - 1; j++) {

                double m1 = (funciones.get(i)[1].getX() - funciones.get(i)[0].getX()) / (funciones.get(i)[1].getY() - funciones.get(i)[0].getY());
                double m2 = (funciones.get(j + 1)[1].getX() - funciones.get(j + 1)[0].getX()) / (funciones.get(j + 1)[1].getY() - funciones.get(j + 1)[0].getY());
                if (m1 != m2) {
                    double x1, x2, mult, res2, res1;
                    if ((resX1[i] == 0 && resX2[j + 1] == 0) || (resX1[j + 1] == 0 && resX2[i] == 0)) {
                        if (resX1[i] == 0 && resX2[j + 1] == 0 ){
                            res2 = resReul[i]/resX2[i];
                            res1 = resReul[j+1]/resX1[j+1];
                        }else {
                            res2 = resReul[j+1]/resX2[j+1];
                            res1 = resReul[i]/resX1[i];
                        }

                    } else if (resX1[j + 1] == 0 || resX1[i] == 0) {
                        mult = (resX2[i] / resX2[j + 1]) * -1;
                        x1 = resX1[i] + resX1[j + 1] * mult;
                        res1 = resReul[i] + resReul[j + 1] * mult;
                        res1 /= x1;
                        if (resX1[j + 1] == 0) {
                            res2 = resReul[j + 1] / resX2[j + 1];
                        } else {
                            res2 = resReul[i] / resX2[i];
                        }
                    } else {
                        mult = (resX1[i] / resX1[j + 1]) * -1;
                        x2 = resX2[i] + resX2[j + 1] * mult;
                        res2 = resReul[i] + resReul[j + 1] * mult;
                        res2 /= x2;
                        res1 = (resReul[i] - (resX2[i] * res2)) / resX1[i];
                    }


                    intersecciones.add(new DataPoint(res1, res2));

                }
            }

        }

        for (int i = 0; i < restricciones.size(); i++) {
            intersecciones.addAll(Arrays.asList(restricciones.get(i)).subList(0, 2));

        }
    }

    private void capturarNumRes() {
        Button siguienteButton = findViewById(R.id.siguiente2);
        EditText rest = findViewById(R.id.rest);
        siguienteButton.setOnClickListener(v -> {
            if (rest.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.siguiente2), "Rellene el campo", Snackbar.LENGTH_SHORT).show();
            } else if (Integer.parseInt(rest.getText().toString()) < 2) {
                Snackbar.make(findViewById(R.id.siguiente2), "Se deben utilizar 2 o mas restricciones", Snackbar.LENGTH_SHORT).show();
            } else {
                numRes = Integer.parseInt(rest.getText().toString());
                setContentView(R.layout.capturar_datos);
                capturaDatos();
            }
        });
    }

    private void capturaDatos() {
        datos = new ArrayList<>();
        LinearLayout layout = findViewById(R.id.captura_datos);
        int numVar = 2;
        CapturaDatos capturaDatos = new CapturaDatos(this, numVar, numRes, datos, layout, obtenerResul);
        capturaDatos.capturar();
    }

    private boolean validacionDeDatos() {
        boolean aux = true;
        for (int i = 0; i < datos.size() && aux; i++) {
            if (datos.get(i).getText().toString().equals("")) {
                aux = false;
            }
        }
        return aux;
    }


    private DataPoint[] revertir(DataPoint[] dataPoints) {
        DataPoint[] aux = new DataPoint[dataPoints.length];

        if (dataPoints[0].getX() > dataPoints[1].getX()) {
            for (int i = 0; i < dataPoints.length; i++) {
                aux[i] = dataPoints[dataPoints.length - 1 - i];
            }
        } else {
            aux = dataPoints;
        }
        return aux;
    }

    private void getRandomColors() {
        colors = new int[restricciones.size()];
        for (int i = 0; i < colors.length; i++) {
            Random rnd = new Random();
            colors[i] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        }
    }

    private double maxY() {
        double max = 0;
        for (int i = 0; i < restricciones.size(); i++) {
            for (int j = 0; j < restricciones.get(i).length; j++) {
                if (restricciones.get(i)[j].getY() > max && Double.isFinite(restricciones.get(i)[j].getY())) {
                    max = (int) restricciones.get(i)[j].getY();
                }
            }
        }

        return max + 1;
    }

    private double maxX() {
        double max = 0;
        for (int i = 0; i < restricciones.size(); i++) {
            for (int j = 0; j < restricciones.get(i).length; j++) {
                if (restricciones.get(i)[j].getX() > max && Double.isFinite(restricciones.get(i)[j].getX())) {
                    max = (int) restricciones.get(i)[j].getX();
                }
            }
        }

        return max + 1;
    }

    private void comprobarIntersecciones() {
        ArrayList<DataPoint> tmp = new ArrayList<>();
        boolean flag;
        for (int i = 0; i < intersecciones.size(); i++) {
            flag = true;
            for (int j = 0; j < resX1.length && flag; j++) {
                double x1 = resX1[j] * intersecciones.get(i).getX();
                double x2 = resX2[j] * intersecciones.get(i).getY();
                if (x1 + x2 > resReul[j] || intersecciones.get(i).getX() < 0 || intersecciones.get(i).getY() < 0) {
                    flag = false;
                }
            }
            if (flag) {
                tmp.add(intersecciones.get(i));
            }
        }
        intersecciones = tmp;
    }

    private DataPoint[] interDataPoints() {
        comprobarIntersecciones();
        DataPoint[] dataPoints = new DataPoint[intersecciones.size()];
        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = intersecciones.get(i);
        }


        for (int x = 0; x < dataPoints.length; x++) {
            for (int i = 0; i < dataPoints.length - x - 1; i++) {
                if (dataPoints[i].getX() > dataPoints[i + 1].getX()) {
                    DataPoint tmp = dataPoints[i + 1];
                    dataPoints[i + 1] = dataPoints[i];
                    dataPoints[i] = tmp;
                }
            }
        }


        return dataPoints;
    }


}