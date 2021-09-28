/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP2Ejercicio5;

/**
 *
 * @author alanizgustavo
 */
public class UsoHilo {
    public static void main(String[]args){
        System.out.println("Hilo principal iniciando");
        MiHilo mh=new MiHilo("#1");
        MiHilo mh2=new MiHilo("#2");
        MiHilo mh3=new MiHilo("#3");
        mh.start();
        mh2.start();
        mh3.start();
        for(int i=0;i<50;i++){
            System.out.println(" .");
        }try{
            Thread.sleep(100);
        }catch(InterruptedException exc){
            System.out.println("Hilo principal interrumpido");
        }
        System.out.println("Hilo principal finalizado");
    }
}

class MiHilo extends Thread{
    String nombreHilo;

    MiHilo(String nombre) {
        this.nombreHilo=nombre;
    }
    
    public void run(){
        System.out.println("Comenzando "+nombreHilo);
        try{
            for(int contar=0;contar<10;contar++){
                Thread.sleep(400);
                System.out.println("En "+nombreHilo+", el recuento "+ contar);
            }
        }catch(InterruptedException exc){
            System.out.println(nombreHilo + "Interrumpido");
        }
        System.out.println("Terminado "+nombreHilo);
    }
}