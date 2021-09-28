/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nafta;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Carros {

    public static void main(String[] args) {
        Auto ka = new Auto("AAA-222", "KA", "FORD", 1000, 600);
        Auto fiesta = new Auto("BBB-222", "FIESTA", "FORD", 700, 700);
        Auto focus = new Auto("CCC-222", "FOCUS", "FORD", 1200, 900);
        Auto uno = new Auto("DDD-222", "UNO", "FIAT", 900, 500);
        Auto qubo = new Auto("EEE-222", "QUBO", "FIAT", 500, 400);

        Thread auto1 = new Thread(ka, "KA");
        Thread auto2 = new Thread(fiesta, "FIESTA");
        Thread auto3 = new Thread(focus, "FOCUS");
        Thread auto4 = new Thread(uno, "UNO");
        Thread auto5 = new Thread(qubo, "QUBO");

        auto1.start();
        auto2.start();
        auto3.start();
        auto4.start();
        auto5.start();

        try {
            auto1.join();
            auto2.join();
            auto3.join();
            auto4.join();
            auto5.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Carros.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(ka.verCapacidadSurtidor());

    }
}

class Vehiculo {

    static Surtidor cargador = new Surtidor();
    protected String patente;
    protected String modelo;
    protected String marca;
    protected int kmFaltantesParaElService;
    protected int tanque;

    public Vehiculo(String matricula, String mod, String mar, int km, int gasolina) {
        this.patente = matricula;
        this.modelo = mod;
        this.marca = mar;
        this.kmFaltantesParaElService = km;
        this.tanque = gasolina;
    }
}

class Auto extends Vehiculo implements Runnable {

    public Auto(String matricula, String mod, String mar, int km, int gasolina) {
        super(matricula, mod, mar, km, gasolina);
    }

    public void run() {
        andar();
    }

    public void andar() {
        while (this.kmFaltantesParaElService > 0) {
            this.kmFaltantesParaElService -= 200;
            if (this.kmFaltantesParaElService <= 0) {

                cargador.cargarAuto(this.tanque);
            }
        }
    }

    public int verCapacidadSurtidor() {
        return cargador.getCantLitros();
    }

}

class Surtidor {

    private int cantidadLitros = 5000;

    public Surtidor() {

    }

    public synchronized void cargarAuto(int tanque) {
        if (this.cantidadLitros > tanque) {
            System.out.println(Thread.currentThread().getName() + " cargo nafta. el Surtidor tenia " + this.cantidadLitros);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Surtidor.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.cantidadLitros -= tanque;

            System.out.println(Thread.currentThread().getName() + " cargo nafta. el Surtidor quedo en " + this.cantidadLitros);
        }

    }

    public int getCantLitros() {
        return this.cantidadLitros;
    }
}
