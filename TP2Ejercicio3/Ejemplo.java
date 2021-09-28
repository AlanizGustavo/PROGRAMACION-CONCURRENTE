/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP2Ejercicio3;

/**
 *
 * @author alanizgustavo
 */
public class Ejemplo {

    public static void main(String[] args) {
        ThreadEjemplo a=new ThreadEjemplo("Maria Jose");
        ThreadEjemplo b=new ThreadEjemplo("Jose Maria");
        Thread h1=new Thread(a);
        Thread h2=new Thread(b);
        h1.start();
        h2.start();
        System.out.println("Termina thread main");
    }
}
