/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP2Ejercicio3;

/**
 *
 * @author alanizgustavo
 */
public class ThreadEjemplo implements Runnable{
    private String nombre;
    
    
    public ThreadEjemplo(String str){
        this.nombre=str;
    }
    public void run(){
        for(int i=0;i<10;i++){
            System.out.println(i + " "+getName());
            System.out.println("Termina thread "+getName());
        }
    }

    public String getName() {
        return nombre;
    }

  
    
    
}


