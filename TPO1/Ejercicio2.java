/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPO1;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Ejercicio2 {
    public static void main(String [] args){
        Planta planta = new Planta();
        Embotellador maquina1=new Embotellador(planta);
        Empaquetador maquina2=new Empaquetador(planta);
        Thread embotellador=new Thread(maquina1,"Embotellador");
        Thread empaquetador=new Thread(maquina2,"Empaquetador");
        
        embotellador.start();
        empaquetador.start();
    }
}

class Embotellador implements Runnable{
    private Planta planta;
    
    public Embotellador(Planta fabrica){
        this.planta=fabrica;
    }
    
    private void prepararBotella(){
        System.out.println(Thread.currentThread().getName()+" PREPARANDO BOTELLA");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Embotellador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        while(true){
            prepararBotella();
            planta.ponerBotella();
        }
    }
}

class Empaquetador implements Runnable{
    private Planta planta;
    
    public Empaquetador(Planta fabrica){
        this.planta=fabrica;
    }
    
    private void guardarCaja(){
        System.out.println(Thread.currentThread().getName()+" SELLA Y GUARDA LA CAJA EN EL ALMACEN");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Empaquetador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        while(true){
            planta.ponerCaja();
            guardarCaja();
        }
    }
}


class Planta{
    private Semaphore caja;
    private Semaphore empaquetador;
    private int contador=0;
    
    public Planta(){
        this.caja=new Semaphore(0);
        this.empaquetador= new Semaphore(0);
    }
    
    public void ponerCaja(){
        System.out.println(Thread.currentThread().getName()+ " PONE CAJA NUEVA");
        this.caja.release(10);
        try {
            this.empaquetador.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Planta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ponerBotella(){
        try {
            if(this.contador<10){
                
                System.out.println("PONE UNA BOTELLA");
                this.caja.acquire();
                this.contador++;
                 
            }
            else{
                System.out.println("SE LLENO LA CAJA");
                System.out.println(this.contador);
                this.contador=1;
                
                this.empaquetador.release();
                this.caja.acquire();
                System.out.println("PONE UNA BOTELLA");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Planta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
