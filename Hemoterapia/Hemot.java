/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hemoterapia;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Hemot {
    public static void main(String[] args) {
        Recepcionista rep=new Recepcionista();
        Thread hiloRep=new Thread(rep,"RECEPCIONISTA");
        hiloRep.start();
        
        Donante[] donantes=new Donante[3];
        Thread[] hilosDonantes=new Thread[3];
        
        for(int i=0;i<donantes.length;i++){
            donantes[i]=new Donante();
            hilosDonantes[i]=new Thread(donantes[i],"Donante "+i);
            hilosDonantes[i].start();
        }
        
    }
}




class Personas {

    protected static Centro centro = new Centro();
}



class Donante extends Personas implements Runnable {

    private void llamarCentro() {
        System.out.println(Thread.currentThread().getName() + " LLAMA AL CENTRO");
        
    }
    
    private void irCentro(){
        System.out.println(Thread.currentThread().getName() + " YENDO AL CENTRO");
        
    }
    
    public void preguntas(){
        try {
            System.out.println(Thread.currentThread().getName() + " RESPONDIENDO PREGUNTAS Y CONTROL DE SIGNOS CLINICOS");
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Donante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void donarSangre(){
        try {
            System.out.println(Thread.currentThread().getName() + " DONANDO SANGRE");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Donante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void certificacion(){
        System.out.println(Thread.currentThread().getName() +" ENTREGA DE CERTIFICADO");
        System.out.println(Thread.currentThread().getName() +" DESAYUNANDO");
    }

    public void run() {
        centro.llamarCentro();
        irCentro();
        centro.irSalaEspera();
        centro.preguntas();
        preguntas();
        centro.extraccion();
        donarSangre();
        centro.certificacion();
        certificacion();
        
        
        

    }
}

class Recepcionista extends Personas implements Runnable {

    public void hablar() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Recepcionista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " PROCESA Y ALMACENA LA SANGRE EN LAS HELADERAS");
            centro.atenderLlamada();
            hablar();
            centro.volverActividades();
        }
    }
}

class Centro {
    private Semaphore mutexLlamada;
    private Semaphore recepcionista;
    private Semaphore especialistaClinico;
    private Semaphore extractorSangre;
    private Semaphore donante;
    private SalaEspera salaEspera;
    private Semaphore sala;

    public Centro() {
        recepcionista = new Semaphore(0);
        especialistaClinico = new Semaphore(1,true);
        extractorSangre = new Semaphore(1,true);
        donante= new Semaphore(0);
        mutexLlamada = new Semaphore(1);
        salaEspera=new SalaEspera(2);
        sala=new Semaphore(0,true);
    }

    public void llamarCentro() {
        try {
            mutexLlamada.acquire();
            recepcionista.release();
            donante.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atenderLlamada() {
        try {
            recepcionista.acquire();
            System.out.println(Thread.currentThread().getName() + " ATENDIO LLAMADA");
        } catch (InterruptedException ex) {
            Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void volverActividades() {
        
        System.out.println("TERMINO LLAMADA");
        donante.release();
        mutexLlamada.release();
    }
    
    public void preguntas(){
        this.salaEspera.dejarLugar();
        this.sala.release();
    }
    
    public void extraccion(){
        this.especialistaClinico.release();
        try {
            this.extractorSangre.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void certificacion(){
        this.extractorSangre.release();
        
    }
    
    public void irSalaEspera(){
        if(this.salaEspera.hayLugar()){
            this.salaEspera.ocuparLugar();
            try {
                this.especialistaClinico.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                this.sala.acquire();
                this.salaEspera.ocuparLugar();
                this.especialistaClinico.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   
    
}



class SalaEspera{
    private int capacidad;
    private int ocupacion;
    private Semaphore mutex;
    
    public SalaEspera(int ocup){
        this.capacidad=ocup;
        this.ocupacion=0;
        this.mutex=new Semaphore(1);
    }
    
    public boolean hayLugar(){
        
        return (ocupacion<capacidad);
    }
    
    public void ocuparLugar(){
        try {
            mutex.acquire();
            this.ocupacion++;
        } catch (InterruptedException ex) {
            Logger.getLogger(SalaEspera.class.getName()).log(Level.SEVERE, null, ex);
        }
        mutex.release();
    }
    
    public void dejarLugar(){
        try {
            mutex.acquire();
            this.ocupacion--;
        } catch (InterruptedException ex) {
            Logger.getLogger(SalaEspera.class.getName()).log(Level.SEVERE, null, ex);
        }
        mutex.release();
    }
}