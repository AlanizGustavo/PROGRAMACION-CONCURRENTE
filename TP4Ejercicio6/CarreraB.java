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
public class CarreraB {
    public static void main(String[] args){
        CorredoresB corredor1=new CorredoresB();
        CorredoresB corredor2=new CorredoresB();
        CorredoresB corredor3=new CorredoresB();
        CorredoresB corredor4=new CorredoresB();
        
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


class CorredoresB implements Runnable{
    static TestigoB testigo=new TestigoB();
    
    public void correr(){
        System.out.println(Thread.currentThread().getName()+" ESTA CORRIENDO");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CorredoresB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void run(){
        testigo.agarrarTestigo();
        correr();
        testigo.soltarTestigo();
    }
}




class TestigoB{
    private Semaphore mutex;
    
    public TestigoB(){
        
        this.mutex=new Semaphore(1);
    }
    
    public void agarrarTestigo(){
        try {
            this.mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Testigo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void soltarTestigo(){
        this.mutex.release();
    }
    
}