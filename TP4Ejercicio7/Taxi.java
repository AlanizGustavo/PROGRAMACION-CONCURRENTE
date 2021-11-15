/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP4Ejercicio7;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Taxi {

    public static void main(String[] args) {
        Coche auto = new Coche();
        Taxista taxista = new Taxista(auto);
        Cliente cliente1 = new Cliente(auto);
        Cliente cliente2 = new Cliente(auto);

        Thread hilo1 = new Thread(taxista, "Taxista");
        Thread hilo2 = new Thread(cliente1, "Cliente 1");
        Thread hilo3 = new Thread(cliente2, "Cliente 2");

        hilo1.start();
        hilo2.start();
        hilo3.start();

    }
}

class Taxista implements Runnable {

    private Coche auto;

    public Taxista(Coche coche) {
        this.auto = coche;
    }

    private void manejar() {
        System.out.println("MANEJANDO");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Taxista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            
            auto.manejar();
            manejar();
            auto.parar();
            System.out.println("TAXISTA DURMIENDO");
        }
    }
}

class Cliente implements Runnable {

    private Coche auto;

    public Cliente(Coche coche) {
        this.auto = coche;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " CAMIENANDO");
        auto.ocuparTaxi();
        auto.viajar();
        auto.esperandoViaje();
        auto.liberarTaxi();
    }
}

class Coche {

    Semaphore semTaxista = new Semaphore(0);
    Semaphore semCliente = new Semaphore(0);
    Semaphore mutex = new Semaphore(1);

    public void ocuparTaxi() {
        try {
            this.mutex.acquire();
            System.out.println(Thread.currentThread().getName()+" OCUPO EL TAXI");
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void liberarTaxi() {
        System.out.println(Thread.currentThread().getName()+" DEJA EL TAXI");
        this.mutex.release();
    }
    
    public void manejar(){
        try {
            this.semTaxista.acquire();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viajar() {
        this.semTaxista.release();
        
    }

    public void parar() {
        this.semCliente.release();
    }
    
    public void esperandoViaje(){
        try {
            this.semCliente.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
