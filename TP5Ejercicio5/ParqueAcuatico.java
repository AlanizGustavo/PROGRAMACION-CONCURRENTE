/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP5Ejercicio5;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class ParqueAcuatico {

    public static void main(String[] args) {
        Mirador mirador = new Mirador();
        Turista[] turistas = new Turista[12];

        Thread[] hilosTuristas = new Thread[turistas.length];

        for (int i = 0; i < hilosTuristas.length; i++) {
            turistas[i] = new Turista(mirador);
            hilosTuristas[i] = new Thread(turistas[i], "Turista " + i);
            hilosTuristas[i].start();
        }

        Thread encargado = new Thread(new Encargado(mirador, turistas.length), "Encargado");
        encargado.start();
    }
}

class Encargado implements Runnable {

    private Mirador mirador;
    private int cantTuristas;

    public Encargado(Mirador vista, int cant) {
        this.mirador = vista;
        this.cantTuristas = cant;
    }

    public void run() {
        int i = 0;
        
        while (i < this.cantTuristas) {
            mirador.seleccionarTobogan();
            i++;
        }
    }
}

class Turista implements Runnable {
    private int tobganAsignado;
    private Mirador mirador;

    public Turista(Mirador vista) {
        this.mirador = vista;
    }
    
    public void descender(){
        System.out.println(Thread.currentThread().getName()+" DESCENDIENDO");
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Turista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        mirador.subirAlMirador();
        this.tobganAsignado=mirador.identificarTobogan();
        mirador.tirarseTobogan(this.tobganAsignado);
        descender();
        mirador.bajarseTobogan(tobganAsignado);
    }
}

class Mirador {

    private boolean tobogan1;
    private boolean tobogan2;

    private int tobogan;

    private Semaphore semTob1;
    private Semaphore semTob2;
    private Semaphore semEscalera;
    private Semaphore semTurno;

    private Semaphore semEncargado;

    public Mirador() {
        this.tobogan1 = true;
        this.tobogan2 = true;

        this.semEscalera = new Semaphore(10, true);
        this.semTob1 = new Semaphore(1);
        this.semTob2 = new Semaphore(1);
        this.semTurno = new Semaphore(1, true);

        this.semEncargado = new Semaphore(1);

    }

    public void subirAlMirador() {
        try {
            this.semEscalera.acquire();
            
            this.semTurno.acquire();
            //this.semEncargado.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Mirador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tirarseTobogan(int numTobogan) {
        try {
            if (numTobogan == 0) {
                this.semTob1.acquire();
                System.out.println(Thread.currentThread().getName()+" SE TIRO POR EL TOBOGAN 0");
                this.tobogan1=false;
            } else {
                this.semTob2.acquire();
                System.out.println(Thread.currentThread().getName()+" SE TIRO POR EL TOBOGAN 1");
                this.tobogan2=false;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Mirador.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.semEncargado.release();
    }

    public void bajarseTobogan(int numTobogan) {
        
        if(numTobogan==0){
            
            this.tobogan1=true;
            this.semTob1.release();
        }else{
            this.tobogan2=true;
            this.semTob2.release();
        }
    }
    
    public int identificarTobogan(){
        return this.tobogan;
    }

    public void seleccionarTobogan() {
        
        try {
            this.semEncargado.acquire();
            if (!this.tobogan1 && !this.tobogan2) {
                Random a = new Random();
                if (a.nextInt() % 2 == 0) {
                    this.tobogan = 0;
                    
                } else {
                    this.tobogan = 1;
                }
            } else if (this.tobogan1) {
                this.tobogan = 0;
            } else if (this.tobogan2) {
                this.tobogan = 1;
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Mirador.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.semTurno.release();
        this.semEscalera.release();
    }
}
