/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP5Ejercicio4;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class TorreControl {
    public static void main(String[] args){
        
        AvionDespegue[] avionesDespegue = new AvionDespegue[10];
        AvionAterrizaje[] avionesAterrizaje = new AvionAterrizaje[5];
        Aeropuerto estacion = new Aeropuerto(avionesAterrizaje.length);
        Thread[] hilosAvionesDespegue = new Thread[avionesDespegue.length];

        for (int i = 0; i < avionesDespegue.length; i++) {
            avionesDespegue[i] = new AvionDespegue(estacion);
            hilosAvionesDespegue[i] = new Thread(avionesDespegue[i], "Avion Despegue" + i);
            hilosAvionesDespegue[i].start();
        }
        
        Thread[] hilosAvionesAterrizaje = new Thread[avionesAterrizaje.length];

        for (int i = 0; i < avionesAterrizaje.length; i++) {
            avionesAterrizaje[i] = new AvionAterrizaje(estacion);
            hilosAvionesAterrizaje[i] = new Thread(avionesAterrizaje[i], "Avion Aterrizaje" + i);
            hilosAvionesAterrizaje[i].start();
        }
        
        Thread torre=new Thread(new Torre(estacion,avionesDespegue.length+avionesAterrizaje.length),"Torre");
        torre.start();
    }
}


class AvionDespegue implements Runnable{
    private Aeropuerto aeropuerto;
    
    public AvionDespegue(Aeropuerto estacion){
        this.aeropuerto=estacion;
    }
    
    public void despegar(){
        System.out.println(Thread.currentThread().getName()+" ESTA DESPEGANDO");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(AvionAterrizaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        aeropuerto.despegar();
        despegar();
        aeropuerto.terminarDespegar();
    }
}

class AvionAterrizaje implements Runnable{
    private Aeropuerto aeropuerto;
    
    public AvionAterrizaje(Aeropuerto estacion){
        this.aeropuerto=estacion;
    }
    
    public void aterrizar(){
        System.out.println(Thread.currentThread().getName()+" ESTA ATERRIZANDO");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(AvionAterrizaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        aeropuerto.aterrizar();
        aterrizar();
        aeropuerto.terminarAterrizar();
    }
}

class Torre implements Runnable{
    private Aeropuerto aeropuerto;
    private int totalAviones;
    
    public Torre(Aeropuerto estacion,int cantAviones){
        this.aeropuerto=estacion;
        this.totalAviones=cantAviones;
    }
    
    public void run(){
        int i=0;
        while(i<this.totalAviones){
            aeropuerto.autorizaciones();
            i++;
        }
    }
}

class Aeropuerto{
    private int contadorAterrizajes;
    private int contadorDespegues;
    private int avionesAutorizadosAterrizar;
    private int contadorAterrizajesSeguidos;
    private int topeMaxAterrizajesSeguidos;
    
    private Semaphore semTorre;
    private Semaphore semDespegue;
    private Semaphore semAterrizaje;
    
    public Aeropuerto(int cantAvionesAterrizando){
        this.contadorAterrizajes=0;
        this.contadorDespegues=0;
        this.avionesAutorizadosAterrizar=cantAvionesAterrizando;
        this.contadorAterrizajesSeguidos=0;
        this.topeMaxAterrizajesSeguidos=10;
        
        this.semAterrizaje=new Semaphore(0);
        this.semDespegue=new Semaphore(0);
        this.semTorre=new Semaphore(1);
    }
    
    public void aterrizar(){
        try {
            this.semAterrizaje.acquire();
            this.contadorAterrizajes++;
            this.contadorAterrizajesSeguidos++;
        } catch (InterruptedException ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void terminarAterrizar(){
        this.semTorre.release();
    }
    
    public void despegar(){
        try {
            this.semDespegue.acquire();
            this.contadorDespegues++;
        } catch (InterruptedException ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void terminarDespegar(){
        this.semTorre.release();
    }
    
    public void autorizaciones(){
        try {
            this.semTorre.acquire();
            if(this.contadorAterrizajesSeguidos==this.topeMaxAterrizajesSeguidos){
                System.out.println(Thread.currentThread().getName()+" AUTORIZA DESPEGUE");
                this.contadorAterrizajesSeguidos=0;
                this.semDespegue.release();
            }
            else if(this.contadorAterrizajes==this.avionesAutorizadosAterrizar){
                System.out.println(Thread.currentThread().getName()+" AUTORIZA DESPEGUE");
                this.semDespegue.release();
            }
            else{
                System.out.println(Thread.currentThread().getName()+" AUTORIZA ATERRIZAJE");
                this.semAterrizaje.release();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
