/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.distanciamin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Somils
 */
public class escribir {

    File archivo;

    public void crearArchivo() {
        archivo = new File("times.txt");
        try {
            if (archivo.createNewFile()) {
                System.out.println("El archivo se ha creado correctamente");
            }
        } catch (IOException ex) {
            System.out.println("No se pudo crear el archivo");
        }
    }

    public void escribirArchivo(long[] operaciones, long[] tiempo, int num) {
        try {
            FileWriter escribir = new FileWriter(archivo, false);
            int i = 0;
            while (i < num) {
                String operacion = String.valueOf((int) operaciones[i]);
                String time = String.valueOf((int) tiempo[i]);
                String imput = String.valueOf(i);
                escribir.write(imput + " " + operacion + " " + time + "\r");
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
            }
            escribir.close();
        } catch (IOException ex) {
            System.out.println("No se puedo escribir en el archivo");
        }
    }

}
