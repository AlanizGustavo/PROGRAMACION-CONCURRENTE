/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP3Ejercicio5;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Letras {

    public static void main(String[] args) {
        Letra a = new Letra('A', 0, 4);
        Letra b = new Letra('B', 1, 1);
        Letra c = new Letra('C', 2, 3);

        Thread letra1 = new Thread(a, "Letra A");
        Thread letra2 = new Thread(b, "Letra B");
        Thread letra3 = new Thread(c, "Letra C");

        letra1.start();
        letra2.start();
        letra3.start();
    }
}

class Letra implements Runnable {

    static GestorTurnos gestor = new GestorTurnos();
    private int turno;
    private char letra;
    private int cant;

    public Letra(char x, int lugar, int cantRep) {
        this.letra = x;
        this.turno = lugar;
        this.cant = cantRep;
    }

    public void imprimirLetra(int cant) {
        for (int i = 0; i < cant; i++) {
            System.out.println(this.letra);
        }

    }

    public void run() {
        int cant = 0;
        do {
            if (gestor.getTurno() == this.turno) {

                imprimirLetra(this.cant);

                gestor.siguienteTurno();

                cant++;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Letra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } while (cant < 3);

    }
}

class GestorTurnos {

    private int turno = 0;

    public GestorTurnos() {

    }

    public synchronized int getTurno() {

        return this.turno;
    }

    public synchronized void siguienteTurno() {

        this.turno = (this.turno + 1) % 3;
    }

}
