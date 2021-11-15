/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP4Ejercicio5;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
/*public class Printers {
    public static void main(String []args){
        CentroImpresion[]impresorasA=new CentroImpresion[5];
        CentroImpresion[]impresorasB=new CentroImpresion[5];
        Usuarios[] usuarios=new Usuarios[10];
        Thread[]hilos=new Thread[10];
        
        for(int i=0;i<impresorasA.length;i++){
            CentroImpresion[i]=new CentroImpresion("A");
        }
        
        for(int j=0;j<impresorasA.length;j++){
            impresorasA[j]=new Impresoras("B");
        }
        
        for(int k=0;k<usuarios.length;k++){
            usuarios[k]=new Usuarios(impresorasA,"Usuario "+(k+1));
            
        }
        
        for(int l=0;l<usuarios.length;l++){
            hilos[l]=new Thread(usuarios[l],"Hilo "+(l+1));
        }
        
    }
}


class Usuarios implements Runnable{
     static CentroImpresion centroImpresion=new CentroImpresion();
    private String usuario;
    
    public Usuarios(String usua){
        this.usuario=usua;
    }
    
    public void run(){
        
    }
}



class CentroImpresion{
    private String tipo;
    private Semaphore semImpresoraA;
    private Semaphore semImpresoraB;
    
    public CentroImpresion(){
        
        this.semImpresoraA=new Semaphore(0);
        this.semImpresoraB=new Semaphore(0);
    }
    
    public void imprimir(){
        try {
            mutex.acquire();
            System.out.println(Thread.currentThread().getName()+" esta imprimiendo trabajo tipo: "+this.tipo );
            mutex.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(CentroImpresion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}*/