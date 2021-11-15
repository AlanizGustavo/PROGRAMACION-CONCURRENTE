/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CruceAutos;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class AutosCruce {
    public static void main (String[] args){
        GestorCruce gestor=new GestorCruce();
        
        HiloDeControl control=new HiloDeControl(gestor);
        Thread hiloControl=new Thread(control,"HILO CONTROL");
        hiloControl.start();
        
        Autos[] autos=new Autos[20];
        Thread[] hilos=new Thread[autos.length];
        
        for(int i=0;i<autos.length;i++){
            autos[i]=new Autos(gestor);
            hilos[i]=new Thread(autos[i],"AUTO "+i);
            hilos[i].start();
        }
    }
}

class Autos implements Runnable{
    private GestorCruce gestorCruce;
    
    public Autos(GestorCruce gest){
        this.gestorCruce=gest;
    }
    
    public void run(){
        Random num=new Random();
        if(num.nextInt()%2==0){
            
        }
        else{
            
        }
    }
}

class GestorCruce{
    private static HiloDeControl controlSem;
    private ReentrantLock semNorte;
    private ReentrantLock semOeste; 
}


class HiloDeControl implements Runnable{
    private GestorCruce gestor;
    
    public HiloDeControl(GestorCruce gest){
        this.gestor=gest;
    }
    
    public void run(){
        while(true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloDeControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            //gestor.cambiarSemaforo();
        }
    }
}