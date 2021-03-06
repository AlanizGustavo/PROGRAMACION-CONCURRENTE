/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP4Ejercicio6;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class CarreraA {

    public static void main(String[] args) {
        Corredores corredor1 = new Corredores("A");
        Corredores corredor2 = new Corredores("A");
        Corredores corredor3 = new Corredores("B");
        Corredores corredor4 = new Corredores("B");

        Thread hilo1 = new Thread(corredor1, "Corredor 1");
        Thread hilo2 = new Thread(corredor2, "Corredor 2");
        Thread hilo3 = new Thread(corredor3, "Corredor 3");
        Thread hilo4 = new Thread(corredor4, "Corredor 4");

        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
    }
}

class Corredores implements Runnable {

    static Testigos testigos = new Testigos();
    protected String salida;
    
    public Corredores(String sal){
        this.salida=sal;
    }

    public void correr() {
        System.out.println(Thread.currentThread().getName() + " ESTA CORRIENDO DE IZQ A DER");

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Corredores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void run() {
        if (this.salida.equals("A")) {
            testigos.correrIzq();
            correr();
            testigos.soltarTestigoA();
        } else {
            testigos.correrDer();
            correr();
            testigos.soltarTestigoB();
        }

    }
}

class Testigos {

    private Semaphore semIzq;
    private Semaphore semDer;

    public Testigos() {
        this.semIzq = new Semaphore(1);
        this.semDer = new Semaphore(0);

    }

    public void correrIzq() {
        try {
            this.semIzq.acquire();

        } catch (InterruptedException ex) {
            Logger.getLogger(Testigos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void correrDer() {
        try {
            this.semDer.acquire();

        } catch (InterruptedException ex) {
            Logger.getLogger(Testigos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void soltarTestigoA() {
        this.semDer.release();
    }

    public void soltarTestigoB() {
        this.semIzq.release();
    }
}
