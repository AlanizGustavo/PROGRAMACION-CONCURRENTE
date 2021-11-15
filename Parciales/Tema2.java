/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parciales;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Tema2 {

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
        
        centro.irAlCentro();
        irCentro();
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

    private Semaphore mutex;
    private Semaphore recepcionista;
    private Semaphore especialistaClinico;
    private Semaphore extractorSangre;
    private Semaphore donante;
    

    public Centro() {
        recepcionista = new Semaphore(0);
        especialistaClinico = new Semaphore(1,true);
        extractorSangre = new Semaphore(1,true);
        donante= new Semaphore(0);
        mutex = new Semaphore(1);
    }

    public void llamarCentro() {
        try {
            mutex.acquire();
            recepcionista.release();
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
        mutex.release();
    }
    
    public void irAlCentro(){
        try {
            donante.acquire();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void preguntas(){
        try {
            this.especialistaClinico.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Centro.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
}
