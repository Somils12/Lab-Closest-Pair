/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.distanciamin;

/**
 *
 * @author Somils
 */
public class punto {

    int x;
    int y;

    punto(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
        //distancia ecludiana entre dos puntos
    public static double distancia(punto p1, punto p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }
}
