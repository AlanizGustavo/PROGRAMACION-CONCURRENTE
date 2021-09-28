/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP4Ejercicio10;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Ejercicio10 {
    public static void main(String[]args){
        Scanner sc=new Scanner(System.in);
        int cantEmpleados;
        System.out.println("INGRESE LA CANTIDAD DE EMPLEADOS");
        cantEmpleados=sc.nextInt();
        
        Confiteria conf=new Confiteria();
        
        Empleado[] arregloEmpleados=new Empleado[cantEmpleados];
        
        Mozo mozo=new Mozo(conf);
        
        Thread hiloMozo=new Thread(mozo,"Mozo");
        hiloMozo.start();
        
        Thread[] hilos=new Thread[cantEmpleados];
      
        for(int i=0;i<cantEmpleados;i++){
            arregloEmpleados[i]=new Empleado(conf);
            hilos[i]=new Thread(arregloEmpleados[i],"Empleado "+i);
            
        }
        
        for(int j=0;j<cantEmpleados;j++){
           
            hilos[j].start();
        }
        
       
    }
}

class Empleado implements Runnable{
    private Confiteria confiteria;
    
    public Empleado(Confiteria conf){
        this.confiteria=conf;
    }
    
    public void comer(){
        System.out.println(Thread.currentThread().getName()+" ESTA COMIENDO");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        confiteria.ocuparMesa();
        confiteria.pedirComida();
        confiteria.esperarComida();
        comer();
        confiteria.dejarMesa();
    }    
}

class Mozo implements Runnable{
    private Confiteria confiteria;
    
    public Mozo(Confiteria conf){
        this.confiteria=conf;
    }
    
    public void cocinar(){
        System.out.println(Thread.currentThread().getName()+" ESTA COCINANDO");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Mozo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        while(true){
            System.out.println("INVENTANDO RECETAS");
            confiteria.atenderMesa();
            cocinar();
            confiteria.servirMesa();
        }
    }
}



class Confiteria{
    private Semaphore semEmpleado;
    private Semaphore semMozo;
    private Semaphore mutex;
    
    public Confiteria(){
        this.semEmpleado=new Semaphore(0);
        this.semMozo=new Semaphore(0);
        this.mutex=new Semaphore(1); 
    }
    
    public void atenderMesa(){
        try {
            this.semMozo.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void servirMesa(){
        this.semEmpleado.release();
        System.out.println(Thread.currentThread().getName()+" SIRVIO LA COMIDA");
        
    }
    
    public void pedirComida(){
        System.out.println(Thread.currentThread().getName()+" OCUPO LA MESA");    
        this.semMozo.release(); 
          
    }
    
    public void ocuparMesa(){
        try {
            this.mutex.acquire();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dejarMesa(){
        System.out.println(Thread.currentThread().getName()+" DEJA LA MESA");
        this.mutex.release();
        
    }
    
    public void esperarComida(){
        try {
            this.semEmpleado.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
