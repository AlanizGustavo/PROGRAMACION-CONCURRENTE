/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPO1;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Ejercicio2B {

    public static void main(String[] args) {
        PlantaB planta = new PlantaB();
        EmbotelladorB maquina1 = new EmbotelladorB(planta);
        EmpaquetadorB maquina2 = new EmpaquetadorB(planta);
        Thread embotellador = new Thread(maquina1, "Embotellador");
        Thread empaquetador = new Thread(maquina2, "Empaquetador");

        embotellador.start();
        empaquetador.start();
    }
}




class EmbotelladorB implements Runnable {

    private PlantaB planta;

    public EmbotelladorB(PlantaB fabrica) {
        this.planta = fabrica;
    }

    private void prepararBotella() {
        System.out.println(Thread.currentThread().getName() + " PREPARANDO BOTELLA");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Embotellador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            try {
                prepararBotella();
                planta.ponerBotella();
            } catch (InterruptedException ex) {
                Logger.getLogger(EmbotelladorB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}




class EmpaquetadorB implements Runnable {

    private PlantaB planta;

    public EmpaquetadorB(PlantaB fabrica) {
        this.planta = fabrica;
    }

    private void guardarCaja() {
        System.out.println(Thread.currentThread().getName() + " SELLA Y GUARDA LA CAJA EN EL ALMACEN");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Empaquetador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            try {
                planta.ponerCaja();
                guardarCaja();
            } catch (InterruptedException ex) {
                Logger.getLogger(EmpaquetadorB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}




class PlantaB {
    private int contador = 0;

    

    public synchronized void ponerCaja() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " PONE CAJA NUEVA");
        this.notify();
        while (contador != 10) {
            this.wait();
        }
        this.contador=1;
    }

    public synchronized void ponerBotella() throws InterruptedException {

        if (this.contador < 10) {

            System.out.println("PONE UNA BOTELLA");

            this.contador++;

        } else {
            System.out.println("SE LLENO LA CAJA");

            
            this.notify();
            this.wait();
            System.out.println("PONE UNA BOTELLA");
        }

    }

}
