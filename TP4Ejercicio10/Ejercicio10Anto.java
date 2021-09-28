/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP4Ejercicio10;

import java.util.concurrent.Semaphore;

/**
 *
 * @author alanizgustavo
 */
public class Ejercicio10Anto {
    public static void main(String[] args) {
        Comedor c= new Comedor();
        Mozos m1= new Mozos(c);
        Empleados e1= new Empleados(c);
        Empleados e2= new Empleados(c);

        Thread mozo= new Thread(m1, "Mozo");
        Thread empleado= new Thread (e1, "Empleado1");
        Thread empleado2= new Thread (e2, "Empleado2");

        
        empleado.start();
        empleado2.start();
        mozo.start();
    }
}


class Comedor {
    //semEmpleado alertara al empleado que llego
    //semMozo avisara al empleado que su comida esta lista
    //mutex exclusion mutua entre empleados
    private static Semaphore semEmpleado= new Semaphore(0);
    private static Semaphore semMozo= new Semaphore(0);
    private static Semaphore mutex= new Semaphore (1);

    public void comenzarAPrepararComida (){
        try{
            //solo si el empleado  le aviso que llego (libero el semaforo )va a poder comenzar a preparar la comida
            semEmpleado.acquire();
        }catch (InterruptedException e){}
        
    }
    public void servirComida(){
        //le da al empleado su comida
        semMozo.release();
    }

    public void solicitarComida (){
        //lo hago synchronized x si viene mas de un empleado
        //el empleado solicita la comida
        System.out.println (Thread.currentThread().getName()+ "  llego al comedor");
        semEmpleado.release();            

    }
    public void ingresarAlComedor (){
        try{
            mutex.acquire();
        }catch(InterruptedException e){}
    }
    public void irseDelComedor (){
        mutex.release();        
    }
    public void esperarComida (){
        try{
            semMozo.acquire();
        }catch(InterruptedException e){}
    }
   
}


class Empleados implements Runnable {
    private Comedor c;
    public Empleados (Comedor c){
        this.c=c;
    }
    public void run (){
        c.ingresarAlComedor();
        c.solicitarComida();
        c.esperarComida();
        c.irseDelComedor();
        //sleep para simular que come

    }
}


class Mozos implements Runnable {
    private Comedor c;
    public Mozos (Comedor c){
        this.c=c;
    }
    public void run (){
        while (true){
            System.out.println ("Inventando recetas");
            c.comenzarAPrepararComida();
            System.out.println ("El mozo comienza a preparar la comida");
            //agregar sleep
            c.servirComida();
        }
    }
}