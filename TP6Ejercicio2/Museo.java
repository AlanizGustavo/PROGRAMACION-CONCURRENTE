/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP6Ejercicio2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Museo {
    public static void main(String[] args){
        Sala sala=new Sala();
        
        Persona[] personas=new Persona[100];
        Jubilado[] jubilados=new Jubilado[20];
        
        Thread[] hilosP=new Thread[100];
        Thread[] hilosJ=new Thread[20];
        
        Monitor monitor=new Monitor(sala);
        Thread hiloM=new Thread(monitor,"MONITOR");
        hiloM.start();
        
        for(int i=0;i<personas.length;i++){
            personas[i]=new Persona(sala);
            hilosP[i]=new Thread(personas[i],"PERSONA "+i);
            hilosP[i].start();
        }
        
        for(int j=0;j<jubilados.length;j++){
            jubilados[j]=new Jubilado(sala);
            hilosJ[j]=new Thread(jubilados[j],"JUBILADO "+j);
            hilosJ[j].start();
        }
        
    }
}

class Persona implements Runnable{
    private Sala sala;
    
    public Persona(Sala lugar){
        this.sala=lugar;
    }
    
    public void pasear(){
        System.out.println(Thread.currentThread().getName()+" ESTA PASEANDO");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        sala.entrarSala();
        pasear();
        sala.salirSala();
    }
    
}

class Jubilado implements Runnable{
    private Sala sala;
    
    public Jubilado(Sala lugar){
        this.sala=lugar;
    }
    
    public void pasear(){
        System.out.println(Thread.currentThread().getName()+" ESTA PASEANDO");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        sala.entrarSalaJubilado();
        pasear();
        sala.salirSala();
    }
}

class Monitor implements Runnable{
    private Sala sala;
    private int temp=20;
    
    public Monitor(Sala lugar){
        this.sala=lugar;
    }
    
    public void medirTemperatura(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.temp=this.temp+15;
    }
    
    @Override
    public void run() {
        medirTemperatura();
        sala.notificarTemperatura(temp);
    }
    
}

class Sala{
    private int cantVisitantes=0;
    private int cantVisitantesMax=50;
    
    private int cantJubiladosEspera=0;
    
    private Lock cerrojo=new ReentrantLock();
    private Condition colaPersonas=cerrojo.newCondition();
    
    
    public Sala(){
        
    }
    
    public void entrarSala(){
        cerrojo.lock();
        while(this.cantVisitantes>this.cantVisitantesMax-1 || this.cantJubiladosEspera>0){
            try {
                this.colaPersonas.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(Thread.currentThread().getName()+" ENTRO A LA SALA");
        System.out.println("JUBILADOS EN ESPERA"+ this.cantJubiladosEspera);
        this.cantVisitantes++;
        System.out.println(this.cantVisitantes);
        cerrojo.unlock();
    }
    
    public void entrarSalaJubilado(){
        cerrojo.lock();
        while(this.cantVisitantes>this.cantVisitantesMax-1){
            try {
                this.cantJubiladosEspera++;
                this.colaPersonas.await();
                this.cantJubiladosEspera--;
            } catch (InterruptedException ex) {
                Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(Thread.currentThread().getName()+" ENTRO A LA SALA");
        this.cantVisitantes++;
        System.out.println(this.cantVisitantes);
        cerrojo.unlock();
    }
    
    public void salirSala(){
        cerrojo.lock();
        System.out.println(Thread.currentThread().getName()+" SALIO DE LA SALA");
        this.cantVisitantes--;
        this.colaPersonas.signalAll();
        cerrojo.unlock();
    }
    
    public void notificarTemperatura(int temperatura){
        cerrojo.lock();
            if(temperatura>30){
                this.cantVisitantesMax=35;
                System.out.println("CAMBIO EL MAXIMO DE PERSONAS");
                System.out.println(this.cantVisitantes);
            }
        cerrojo.unlock();
    }
    
}