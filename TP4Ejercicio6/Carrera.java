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
public class Carrera {
    public static void main(String[] args){
        Corredores corredor1=new Corredores();
        Corredores corredor2=new Corredores();
        Corredores2 corredor3=new Corredores2();
        Corredores2 corredor4=new Corredores2();
        
        Thread hilo1=new Thread(corredor1,"Corredor 1");
        Thread hilo2=new Thread(corredor2,"Corredor 2");
        Thread hilo3=new Thread(corredor3,"Corredor 3");
        Thread hilo4=new Thread(corredor4,"Corredor 4");
        
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
    }
}


class Corredores implements Runnable{
    static Testigo testigo=new Testigo();
    
    public void correr(){
        System.out.println(Thread.currentThread().getName()+" ESTA CORRIENDO DE IZQ A DER");
        
        /*try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Corredores.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    
    public void run(){
        testigo.agarrarTestigo();
        testigo.correrIzq();
        correr();
        testigo.soltarTestigo();
    }
}

class Corredores2 extends Corredores implements Runnable{
    
    public void correr(){
        System.out.println(Thread.currentThread().getName()+" ESTA CORRIENDO DE DER A IZQ");
        
        /*try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Corredores2.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    
    public void run(){
        System.out.println("Entro a corredor 2");
        testigo.agarrarTestigo();
        testigo.correrDer();
        correr();
        testigo.soltarTestigo();
    }
}


class Testigo{
    private Semaphore semIzq;
    private Semaphore semDer;
    private Semaphore mutex;
    
    public Testigo(){
        this.semIzq=new Semaphore(1);
        this.semDer=new Semaphore(0);
        this.mutex=new Semaphore(1);
    }
    
    
    public void correrIzq(){
        try {
            this.semIzq.acquire();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Testigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.semDer.release();
    }
    
    public void correrDer(){
        try {
            this.semDer.acquire();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Testigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.semIzq.release();
    }
    
    public void agarrarTestigo(){
        System.out.println(Thread.currentThread().getName()+" AGARRO TESTIGO");
        try {
            this.mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Testigo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void soltarTestigo(){
        System.out.println(Thread.currentThread().getName()+" SOLTO TESTIGO");
        this.mutex.release();
        
    }
    
}