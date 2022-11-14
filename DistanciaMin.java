/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.distanciamin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/*Somil Sandoval Diaz
  Codigo del curso: IST 4310-01
  Codigo: 200151782
  Nombre Lab Closest Pair
  Fecha: 4 de agosto del 2022
  La actividad consta de diseñar un algoritmo que permita determinar el par de puntos mas cercanos mediante una arreglo
de puntos utilizando metodos recursivos.*/
/**
 *
 * @author Somils
 */
public class DistanciaMin {

    private static double MIN_VAL = Double.MAX_VALUE;

    public double fuerzabruta(punto[] list) {
        double min = MIN_VAL;
        for (int i = 0; i < list.length; i++) {
            for (int j = i + 1; j < list.length; j++) {
                double dist = punto.distancia(list[i], list[j]);
                if (dist < min) {
                    min = dist;
                }
                min = min(min, dist);
            }
        }
        return min;
    }

    //metodo para ordenar los puntos de la lista de acuerdo a "x" y de acuerdo a "y"
    public double recursivoPar(punto[] puntos) {
        int n = puntos.length;
        punto[] ordenX = new punto[n];
        punto[] ordenY = new punto[n];
        for (int i = 0; i < n; i++) {
            ordenX[i] = puntos[i];
            ordenY[i] = puntos[i];
        }
        Arrays.sort(ordenX, new PointXComparator());
        Arrays.sort(ordenY, new PointYComparator());
        return recursivoPar(ordenX, ordenY, 0, n - 1);
    }

    public double recursivoPar(punto[] ordenX, punto[] ondenY, int inicio, int ancho) {
        int n = ancho - inicio + 1;
        //No hay problema en utilizar el metodo de fuerza bruta para conjuntos pequeños de puntos.
        if (n <= 3) {
            return fuerzabruta(ordenX);
        }
        // divididimos el conjunto en dos mitades para encontrar y poder trazar la linea imaginaria
        //que distinga entre la parte derecha e izquierda. 
        int mitad = inicio + (ancho - inicio) / 2;
        punto coord_mitad = ordenX[mitad];

        // encontrar la distancia minima en el subconjunto izquierdo 
        double distancia_minIzq = recursivoPar(ordenX, ondenY, inicio, mitad);
        //encontrar la distancia minima en el subconjunto oderecho recursivamente
        double distancia_minDer = recursivoPar(ordenX, ondenY, mitad + 1, ancho);

        // compraramos la distancia minima encontrada en el subconjunto izquierdo con la minima encontrada en el subconjunto
        //derecho para determinar cual es menor
        double distanciaMin = Math.min(distancia_minIzq, distancia_minDer);

        // hay la posibilidad de que la distancia mínima sea entre un punto ubicado a la derecha y otro a la izquierda
        // para encontrar tal escenario creamos una franja imaginaria de distancia minDistancia(la minima distancia encontrada previamente)
        //a ambos lados
        int franjaIZQ = -1;
        int franjaDER = -1;
        for (int i = inicio; i < ancho; i++) {
            if (Math.abs(ondenY[i].x - coord_mitad.x) < distanciaMin) {
                if (franjaIZQ == -1) {
                    franjaIZQ = i;
                } else {
                    franjaDER = i;
                }
            }
        }
        // ahora encuentra la distancia mínima en la franja
        double distanciaMin_franja = distancia_minFranja(ondenY, franjaIZQ, franjaDER);

        // finalmente se compara si la distancia minima de la franja es menor a la encontrada previamente. 
        return min(distanciaMin, distanciaMin_franja);
    }

    // minima distancia entre los puntos de la franja
    public double distancia_minFranja(punto[] ordenY, int inicio, int ancho) {
        double min = MIN_VAL;
        for (int i = inicio; i <= ancho; i++) {
            for (int j = i + 1; j <= ancho; j++) {
                min = min(min, punto.distancia(ordenY[i], ordenY[j]));
            }
        }
        return min;
    }

    public static punto[] completList(punto[] List_puntos, int i) {
        int sw;
        int x_min = -i, x_max = i;
        int y_min = -4, y_max = 4;
        Random rand = new Random();
        for (int j = 0; j < i; j++) {
            do {
                int x = x_min + rand.nextInt(x_max - x_min);
                int y = y_min + rand.nextInt(y_max - y_min);
                sw = repetidos(List_puntos, x, y, j);
                if (sw == 0) {
                    List_puntos[j] = new punto(x, y);
                }
            } while (sw == 1);
        }
        return List_puntos;

    }

    public static int repetidos(punto[] lista_puntos, int x, int y, int j) {
        int sw = 0, k = 0;
        while ((k < j) && (sw == 0)) {
            if ((lista_puntos[k].x == x) && (lista_puntos[k].y == y)) {
                sw = 1;
            }
            k = k + 1;
        }
        return sw;
    }

    public static long timing(punto[] List_puntos, long suma) {
        //Inicio de ejecucion
        long inicioEjecucion = System.nanoTime();
        DistanciaMin obj = new DistanciaMin();
        //double distancia = obj.fuerzabruta(List_puntos);
        double distancia = obj.recursivoPar(List_puntos);
        long finEjecucion = System.nanoTime();  //Fin de ejecuacion
        System.out.println("La distancia minima es: " + distancia);
        suma = (finEjecucion - inicioEjecucion) + suma;
        return suma;
    }

    public static int indice(int i) {
        if (i < 100) {
            i = i + 10;
        } else {
            if ((i > 100) && (i < 300)) {
                i = i + 50;
            } else {
                if (i < 1000) {
                    i = i + 100;
                } else {
                    if ((i > 1000) && (i < 3000)) {
                        i = i + 300;
                    } else {
                        if (i < 10000) {
                            i = i + 1000;
                        } else {
                            i = i + 10000;
                        }
                    }
                }
            }
        }
        return i;
    }

    //Encontrar el minimo entre dos valores
    public double min(double val1, double val2) {
        return Math.min(val1, val2);
    }

    public static void main(String[] args) {
        int tamaño = 10000, repeticiones = 256;
        long suma;
        long[] tiempo = new long[tamaño];
        long[] operaciones = new long[tamaño];
        int i = 10;
        //tamaño del conjunto de puntos
        while (i < tamaño) {
            System.out.println("El tamaño es: " + (i));
            suma = 0; //Se reinicia el acumulador del tiempo de ejecucion
            //Se realizan 256 repeticiones para posteriormente obtener un promedio del tiempo de ejecucion para cada
            //tamaño "i" de la lista de puntos
            for (int k = 0; k < repeticiones; k++) {
                punto[] List_puntos = new punto[i];
                //Se llena la lista de puntos con numeros aleatorios
                List_puntos = completList(List_puntos, i);
                suma = timing(List_puntos, suma);
            }
            tiempo[i] = suma / repeticiones; //tiempo promedio de ejecucion para cada tamaño i (numero de puntos en la lista)
            System.out.println("El tiempo promedio para un tamaño " + i + " es: " + tiempo[i]);
            i = indice(i);
        }
        escribir P = new escribir();
        P.crearArchivo();
        P.escribirArchivo(operaciones, tiempo, tamaño);
    }
}

class PointXComparator implements Comparator<punto> {

    @Override
    public int compare(punto pointA, punto pointB) {
        return Integer.compare(pointA.x, pointB.x);
    }
}

class PointYComparator implements Comparator<punto> {

    @Override
    public int compare(punto pointA, punto pointB) {
        return Integer.compare(pointA.y, pointB.y);
    }

}
