package com.raysk.linearprogramming;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GraficoActivity extends AppCompatActivity {
    private int numRes;
    private double x1, x2;
    private double[] resX1, resX2, resReul;
    private double[] datos;
    private final ArrayList<DataPoint[]> restricciones = new ArrayList<>();
    private final ArrayList<DataPoint[]> funciones = new ArrayList<>();
    private ArrayList<DataPoint> intersecciones = new ArrayList<>();
    private int[] colors;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafica);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle d = this.getIntent().getExtras();
        datos = (double[]) d.get("datos");
        numRes = d.getInt("numRes");
        datosToArray();
        datosToGrafica();

        



    }

    @SuppressLint("SetTextI18n")
   /* private void siguienteButton() {
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

    }*/

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



    private void datosToArray() {
        x1 = datos[0];
        x2 = datos[1];
        resX1 = new double[numRes];
        resX2 = new double[numRes];
        resReul = new double[numRes];

        int cont = 0;
        for (int i = 3; i < datos.length; i += 3) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    resX1[cont] = datos[i+j];
                } else if (j == 1) {
                    resX2[cont] = datos[i+j];
                } else {
                    resReul[cont] = datos[i+j];
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


    @SuppressLint("SetTextI18n")
    private void datosToGrafica() {
        getRandomColors();
        getIntersecciones();
        DataPoint[] points = interDataPoints();
        double[] xyz = getX1X2Z();
        double maxX = maxX();
        double maxY = maxY();
        LinearLayout principal = findViewById(R.id.principal);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,40);
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 2 ; j++) {
                if (j == 0){
                    TextView textView = new TextView(this);
                    textView.setLayoutParams(params);
                    textView.setBackgroundColor(Color.GREEN);
                    textView.setTextSize(50);
                    if (i == 1){
                        textView.setText("Puntos Posibles");
                    }else if (i == 2){
                        textView.setText("Punto Óptimo");
                    }else if (i == 3){
                        textView.setText("Resultado");
                    }else {
                        textView.setText("Area Solución");
                    }

                    principal.addView(textView);
                }else {
                    if (i == 3){
                        for (int l = 0; l < 3; l++) {
                            LinearLayout layout = new LinearLayout(this);
                            TextView textView = new TextView(this);
                            textView.setTextSize(50);
                            textView.setLayoutParams(params);
                            textView.setGravity(Gravity.END);
                            TextView textView2 = new TextView(this);
                            textView2.setTextSize(50);
                            textView2.setLayoutParams(params);
                            textView2.setTextColor(Color.BLUE);
                            textView.setTextColor(Color.RED);
                            //textView2.setGravity(Gravity.CENTER);
                            textView2.setText(Double.toString(xyz[l]));
                            if (l == 0){
                                textView.setText("X1: ");
                            }else if (l == 1){
                                textView.setText("X2: ");
                            }else {
                                textView.setText("Z: ");
                            }
                            layout.addView(textView);
                            layout.addView(textView2);
                            principal.addView(layout);
                        }
                    }else {
                        GraphView graphView = new GraphView(this);
                        //graph.getViewport().setScrollable(true);
                        //graph.getViewport().setScalable(true);
                        //graphView.setLayoutParams(params);
                        graphView.getViewport().setYAxisBoundsManual(true);
                        graphView.getViewport().setXAxisBoundsManual(true);
                        graphView.getViewport().setMaxX(maxX);
                        graphView.getViewport().setMaxY(maxY);
                        graphView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800));
                        for (int k = 0; k < funciones.size(); k++) {
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(funciones.get(k));
                            series.setColor(colors[k]);
                            //series.setDrawDataPoints(true);
                            //series.setDataPointsRadius ( 20 );

                            graphView.addSeries(series);
                        }
                        LineGraphSeries<DataPoint> areaSolucion = new LineGraphSeries<>(points);
                        areaSolucion.setBackgroundColor(Color.argb(145, 63, 190, 63));
                        areaSolucion.setDrawBackground(true);
                        areaSolucion.setColor(Color.TRANSPARENT);
                        graphView.addSeries(areaSolucion);


                        if (i == 1) {
                            PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(points);
                            graphView.addSeries(series);
                        } else if (i == 2) {
                            DataPoint[] point = new DataPoint[1];
                            point[0] = new DataPoint(xyz[0], xyz[1]);
                            PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(point);
                            series.setColor(Color.RED);
                            graphView.addSeries(series);

                        }
                        principal.addView(graphView);
                    }

                }
            }

        }












        /*
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

         */


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