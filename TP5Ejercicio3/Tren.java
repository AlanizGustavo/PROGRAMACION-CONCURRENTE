/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP5Ejercicio3;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Tren {

    public static void main(String[] args) {
        Estacion estacion = new Estacion();
        Pasajeros[] pasajeros = new Pasajeros[12];

        Thread[] hilosPasajeros = new Thread[pasajeros.length];

        for (int i = 0; i < hilosPasajeros.length; i++) {
            pasajeros[i] = new Pasajeros(estacion);
            hilosPasajeros[i] = new Thread(pasajeros[i], "Pasajero " + i);
            hilosPasajeros[i].start();
        }

        Thread vendedor = new Thread(new VendedorTickets(estacion), "Vendedor");
        Thread controlTren = new Thread(new ControlTren(estacion), "Control Tren");

        vendedor.start();
        controlTren.start();
    }
}

class VendedorTickets implements Runnable {

    private Estacion estacion;

    public VendedorTickets(Estacion est) {
        this.estacion = est;
    }

    public void run() {
        while (true) {
            estacion.venderTicket();
        }

    }
}

class Pasajeros implements Runnable {

    private Estacion estacion;

    public Pasajeros(Estacion est) {
        this.estacion = est;
    }
    

    public void run() {
        while (true) {
            estacion.comprarTicket();
            estacion.irTren();
            
        }
    }
}

class ControlTren implements Runnable {

    private Estacion estacion;

    public ControlTren(Estacion est) {
        this.estacion = est;
    }
    
    public void hacerBajarTren(){
        System.out.println("SE BAJAN LOS PASAJEROS DEL TREN");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Pasajeros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viajar() {
        System.out.println("EL TREN ESTA VIAJANDO");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlTren.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            estacion.habilitarSalidaTren();
            viajar();
            estacion.hacerDescenderPasajeros();
            hacerBajarTren();
            estacion.hacerSubirPasajeros();
        }
    }
}

class Estacion {

    private int cantPasajerosMax;
    private int cantAsientosOcupados;

    private Semaphore semTicket;                    //controla vendedor Tickets
    private Semaphore semPasajero;                  //controla pasajero
    private Semaphore semControlTren;               //controla cuantos pasajeros entran al tren
    private Semaphore semViajar;                    //bloquea a los pasajeros que entraron al tren para viajar
    private Semaphore semSalidaTren;

    private Semaphore mutex;

    public Estacion() {
        this.cantPasajerosMax = 5;
        this.cantAsientosOcupados = 0;

        this.mutex = new Semaphore(1);

        this.semTicket = new Semaphore(0, true);
        this.semPasajero = new Semaphore(0, true);
        this.semControlTren = new Semaphore(this.cantPasajerosMax, true);
        this.semViajar = new Semaphore(0);
        this.semSalidaTren = new Semaphore(0);

    }

    public void comprarTicket() {

        try {
            System.out.println(Thread.currentThread().getName() + " SOLICITA UN TICKET");
            semTicket.release();
            semPasajero.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Estacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void venderTicket() {
        try {
            semTicket.acquire();
            System.out.println(Thread.currentThread().getName() + " ENTREGA UN TICKET");
            semPasajero.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Estacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void irTren() {
        try {
            this.semControlTren.acquire();
            this.mutex.acquire();
            this.cantAsientosOcupados++;
            System.out.println("asientos ocupados: "+this.cantAsientosOcupados);
            System.out.println(Thread.currentThread().getName() + " ESTA ENCERRADO EN EL TREN");
            
            if (this.cantAsientosOcupados == this.cantPasajerosMax) {
                System.out.println(Thread.currentThread().getName() + " *******************");

                this.semSalidaTren.release();
                
            }
            mutex.release();
            semViajar.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Estacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void habilitarSalidaTren() {
        try {
            this.semSalidaTren.acquire();
            System.out.println(Thread.currentThread().getName() + " HABILITA LA SALIDA DEL TREN");
        } catch (InterruptedException ex) {
            Logger.getLogger(Estacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hacerDescenderPasajeros() {
        this.cantAsientosOcupados = 0;
        this.semViajar.release(this.cantPasajerosMax);
        
    }
    
    public void hacerSubirPasajeros(){
        this.semControlTren.release(this.cantPasajerosMax);
    }
}
