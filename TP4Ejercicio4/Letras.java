/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP4Ejercicio4;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Letras {

    public static void main(String[] args) {
        Letra[] arregloLetras=new Letra[3];
        Thread[] arregloHilos=new Thread[3];
        arregloLetras[0]=new Letra('A', 0, 2);     //primer parametro es la letra, segundo el turno para imprimir y el tercero la cantidad de repeticiones de cada letra
        arregloLetras[1]=new Letra('B', 1, 3);
        arregloLetras[2]=new Letra('C', 2, 4);

        for(int i=0;i<arregloLetras.length;i++){
            arregloHilos[i]=new Thread(arregloLetras[i]);
            arregloHilos[i].start();
        }
        

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
        for (int i = 0; i < 2; i++) {
            gestor.getTurno(this.turno);

            imprimirLetra(this.cant);

            gestor.siguienteTurno();
        }
    }
}

class GestorTurnos {

    private Semaphore mutexA;
    private Semaphore mutexB;
    private Semaphore mutexC;

    private int turno = 0;

    public GestorTurnos() {
        this.mutexA = new Semaphore(1);
        this.mutexB = new Semaphore(0);
        this.mutexC = new Semaphore(0);
    }

    public void getTurno(int turno) {
        try {
            if (turno == 0) {
                this.mutexA.acquire();
            } else if (turno == 1) {
                this.mutexB.acquire();
            } else {
                this.mutexC.acquire();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(GestorTurnos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void siguienteTurno() {

        this.turno = (this.turno + 1) % 3;
        if (this.turno == 0) {
            this.mutexA.release();
        } else if (this.turno == 1) {
            this.mutexB.release();
        } else {
            this.mutexC.release();
        }

    }

}
