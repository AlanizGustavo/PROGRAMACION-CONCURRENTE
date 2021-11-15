/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP4Ejercicio9;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Hamsters {
    public static void main(String[] args) {
        
        HamsterL ham1=new HamsterL("Hamster1");
        HamsterL ham2=new HamsterL("Hamster2");
        HamsterL ham3=new HamsterL("Hamster3");
        Thread hamster1 = new Thread (ham1,"Hamster1"); 
        Thread hamster2 = new Thread (ham2,"Hamster2");
        Thread hamster3 = new Thread (ham3,"Hamster3");
        hamster1.start();
        hamster2.start();
        hamster3.start();
    }
}


class HamsterL implements Runnable{
    private String nombre;
    static Plato plato=new Plato();
    static Rueda rueda=new Rueda();
    static Hamaca hamaca=new Hamaca();
    
    
    public HamsterL(String nombre){
        this.nombre=nombre;
        
    }
    
    public void comer(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HamsterL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void hamacarse(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HamsterL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rueda(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HamsterL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        
        plato.agarrarPlato();
        comer();
        plato.dejarPlato();
        hamaca.agarrarHamaca();
        hamacarse();
        hamaca.dejarHamaca();
        rueda.agarrarRueda();
        rueda();
        rueda.dejarRueda();
        
    }
    
    public String getNombre(){
        return this.nombre;
    }
}


class Hamaca{
    private ReentrantLock cerrojo1;
    public Hamaca(){
        cerrojo1=new ReentrantLock();
    }
    
    public void agarrarHamaca(){
        
        cerrojo1.lock();
        System.out.println(Thread.currentThread().getName()+" ESTA EN LA HAMACA");
        
    }
    
    public void dejarHamaca(){
        System.out.println(Thread.currentThread().getName()+" DEJA HAMACA");
        cerrojo1.unlock();
    }
        
}

class Rueda{
    private ReentrantLock cerrojo2;
    public Rueda(){
        cerrojo2=new ReentrantLock();
    }
    
    public void agarrarRueda(){
        
        cerrojo2.lock();
        System.out.println(Thread.currentThread().getName()+" ESTA EN LA RUEDA");
        
    }
    
    public void dejarRueda(){
        System.out.println(Thread.currentThread().getName()+" DEJA RUEDA");
        cerrojo2.unlock();
    }
}

class Plato{
    private ReentrantLock cerrojo3;
    public Plato(){
        cerrojo3=new ReentrantLock();
    }
    
    public void agarrarPlato(){
        
        cerrojo3.lock();
        System.out.println(Thread.currentThread().getName()+" ESTA EN LA PLATO");
        
    }
    
    public void dejarPlato(){
        System.out.println(Thread.currentThread().getName()+" DEJA PLATO");
        cerrojo3.unlock();
    }
}