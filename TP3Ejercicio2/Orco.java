/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP3Ejercicio2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Orco {

    public static void main(String[] args) {
        Personaje heroe = new Personaje("Heroe");
        Orcos orco = new Orcos("Orco", heroe);
        Curandero curandero = new Curandero("Curandero", heroe);

        Thread h1 = new Thread(orco);
        Thread h2 = new Thread(curandero);

        h1.start();
        h2.start();

        
        
        try {
            
            h1.join();
            h2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Orco.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("final " + heroe.getVida());
    }
}

class Orcos implements Runnable {

    private String nombre;
    private Personaje heroe;

    public Orcos(String nombre, Personaje heroe) {
        this.nombre = nombre;
        this.heroe = heroe;
    }

    public void atacar() {
        heroe.reducirVida();
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            if (heroe.getVida() > 0) {
                System.out.println(this.nombre + " lo atacara "
                        + " al heroe...Ahora tiene:" + heroe.getVida());
                atacar();
                System.out.println(this.nombre + " le quito "
                        + " al heroe...Ahora tiene:" + heroe.getVida());
            }
            else{
                System.out.println("ESTA MUERTO NO SE PUEDE ATACAR");
            }
        }
    }
}

class Curandero implements Runnable {

    private String nombre;
    private Personaje heroe;

    public Curandero(String nombre, Personaje heroe) {
        this.nombre = nombre;
        this.heroe = heroe;
    }

    public void curar() {
        heroe.aumentarVida();
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            if (heroe.getVida() > 0) {
                System.out.println(this.nombre + " curara "
                        + " al heroe...Ahora tiene:" + heroe.getVida());
                curar();
                System.out.println(this.nombre + " curo "
                        + " al heroe...Ahora tiene:" + heroe.getVida());
            }
            else{
                System.out.println("ESTA MUERTO NO SE PUEDE CURAR");
            }

        }
    }
}

class Personaje {

    private int vida;
    private String nombre;

    public Personaje(String nombre) {
        this.nombre = nombre;
        this.vida = 10;
    }

    public synchronized int getVida() {
        return this.vida;
    }

    public synchronized void reducirVida() {
        this.vida -= 3;
    }

    public synchronized void aumentarVida() {
        this.vida += 3;
    }
}
