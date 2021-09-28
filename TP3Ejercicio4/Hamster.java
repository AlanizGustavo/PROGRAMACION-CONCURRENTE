/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP3Ejercicio4;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Hamster {
    public static void main(String[] args) {
        
        Hamsters ham1=new Hamsters("Hamster1");
        Hamsters ham2=new Hamsters("Hamster2");
        Hamsters ham3=new Hamsters("Hamster3");
        Thread hamster1 = new Thread (ham1,"Hamster1"); 
        Thread hamster2 = new Thread (ham2,"Hamster2");
        Thread hamster3 = new Thread (ham3,"Hamster3");
        hamster1.start();
        hamster2.start();
        hamster3.start();
    }
}





class Hamsters implements Runnable{
    private String nombre;
    
    
    public Hamsters(String nombre){
        this.nombre=nombre;
    }
    
    public void run(){
        Comer.comer();
        Hamaca.hamaca();
        Rueda.rueda();
    }
    
    public String getNombre(){
        return this.nombre;
    }
}


class Hamaca{
    public static synchronized void hamaca(){
        System.out.println(Thread.currentThread().getName()+" ESTA EN LA HAMACA");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Hamaca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

class Rueda{
    public static synchronized void rueda(){
        System.out.println(Thread.currentThread().getName()+" ESTA EN LA RUEDA");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Rueda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Comer{
    public static synchronized void comer(){
        System.out.println(Thread.currentThread().getName()+" ESTA COMIENDO");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Comer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


