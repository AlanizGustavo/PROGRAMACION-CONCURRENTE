/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP5Ejercicio6;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Kruger {

    public static void main(String[] args) {

        BabuinosA[] babuinosA = new BabuinosA[10];
        BabuinosB[] babuinosB = new BabuinosB[10];

        Cuerda cuerda = new Cuerda(babuinosA.length, babuinosA.length);

        Thread[] hilosA = new Thread[babuinosA.length];
        Thread[] hilosB = new Thread[babuinosB.length];

        for (int i = 0; i < hilosA.length; i++) {
            babuinosA[i] = new BabuinosA(cuerda);
            hilosA[i] = new Thread(babuinosA[i], "BabuinoA " + i);
            hilosA[i].start();
        }

        for (int j = 0; j < hilosB.length; j++) {
            babuinosB[j] = new BabuinosB(cuerda);
            hilosB[j] = new Thread(babuinosB[j], "BabuinoB " + j);
            hilosB[j].start();
        }

        try {
            for (int j = 0; j < hilosA.length; j++) {
                hilosA[j].join();
            }//espero a que todos los hilos finalicen
            for (int k = 0; k < hilosB.length; k++) {
                hilosB[k].join();
            }//espero a que todos los hilos finalicen
        } catch (InterruptedException ex) {
            Logger.getLogger(Kruger.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("CRUZARON " + cuerda.getBabuinosA() + " DEL LADO A AL LADO B DEL CANION \n"
                + "CRUZARON " + cuerda.getBabuinosB() + " DEL LADO B AL LADO A DEL CANION ");
    }
}

class BabuinosA implements Runnable {

    private Cuerda cuerda;

    public BabuinosA(Cuerda soga) {
        this.cuerda = soga;
    }

    private void cruzarCanion() {
        // simulo con sleep que esta cruzando el canion
        System.out.println(Thread.currentThread().getName() + " ESTA CRUZANDO EL CANION");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(BabuinosA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        //de manera aleatoria se distrubuyen de un lado o del otro del canion

        cuerda.agarrarCuerdaA();
        cruzarCanion();

        cuerda.soltarCuerdaA();

    }
}

class BabuinosB implements Runnable {

    private Cuerda cuerda;

    public BabuinosB(Cuerda soga) {
        this.cuerda = soga;
    }

    private void cruzarCanion() {
        // simulo con sleep que esta cruzando el canion
        System.out.println(Thread.currentThread().getName() + " ESTA CRUZANDO EL CANION");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(BabuinosB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        //de manera aleatoria se distrubuyen de un lado o del otro del canion

        cuerda.agarrarCuerdaB();
        cruzarCanion();
        cuerda.soltarCuerdaB();

    }
}

class Cuerda {

    private int cantBabuinosA;
    private int cantBabuinosB;
    private int contadorA = 0;
    private int contadorB = 0;
    private int contadorBabuinosSeguidos = 0;
    private int maxBabuinosSeguidos;

    private int turno = 0;

    Semaphore mutex2;
    Semaphore mutex;                        // semaforo de exclusion mutua de la cuerda
    Semaphore ladoA;
    Semaphore ladoB;

    public Cuerda(int a, int b) {
        this.ladoA = new Semaphore(5, true);
        this.ladoB = new Semaphore(5, true);
        this.mutex = new Semaphore(1);
        this.mutex2 = new Semaphore(1);
        this.cantBabuinosA = a;
        this.cantBabuinosB = b;
        this.maxBabuinosSeguidos = 7;
    }

    public void agarrarCuerdaA() {
        try {
            mutex.acquire();
            if (this.turno == 0) {
                this.turno = 1;

                ladoB.acquire(5);

            }

            mutex.release();
            ladoA.acquire();

            System.out.println(Thread.currentThread().getName() + " AGARRA LA CUERDA");
        } catch (InterruptedException ex) {
            Logger.getLogger(Cuerda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agarrarCuerdaB() {
        try {
            mutex.acquire();
            if (this.turno == 0) {
                this.turno = 2;

                ladoA.acquire(5);

            }
            mutex.release();
            ladoB.acquire();

            System.out.println(Thread.currentThread().getName() + " AGARRA LA CUERDA");
        } catch (InterruptedException ex) {
            Logger.getLogger(Cuerda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void soltarCuerdaA() {
        try {
            this.mutex2.acquire();
            System.out.println(Thread.currentThread().getName() + " SUELTA LA CUERDA");
            this.contadorA++;
            this.contadorBabuinosSeguidos++;
            if (this.contadorBabuinosSeguidos == this.maxBabuinosSeguidos) {
                this.contadorBabuinosSeguidos = 0;
                this.ladoB.release(5);
            } else if (this.contadorA == this.cantBabuinosA) {
                this.ladoB.release(5);
            } else if (this.contadorBabuinosSeguidos < 3) {
                this.ladoA.release();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Cuerda.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.mutex2.release();
    }

    public void soltarCuerdaB() {
        try {
            this.mutex2.acquire();
            System.out.println(Thread.currentThread().getName() + " SUELTA LA CUERDA");
            this.contadorB++;
            this.contadorBabuinosSeguidos++;
            if (this.contadorBabuinosSeguidos == this.maxBabuinosSeguidos) {
                this.contadorBabuinosSeguidos = 0;
                this.ladoA.release(5);
            } else if (this.contadorB == this.cantBabuinosB) {
                this.ladoA.release(5);
            } else if (this.contadorBabuinosSeguidos < 3) {
                this.ladoB.release();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Cuerda.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.mutex2.release();
    }

    public int getBabuinosA() {
        return this.cantBabuinosA;
    }

    public int getBabuinosB() {
        return this.cantBabuinosB;
    }
}
