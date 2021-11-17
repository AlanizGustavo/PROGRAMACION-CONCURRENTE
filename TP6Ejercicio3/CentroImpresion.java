/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP6Ejercicio3;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class CentroImpresion {
    public static void main(String[] args){
        
        Persona[] personas=new Persona[50];
        ImpresoraA[] impresorasA=new ImpresoraA[5];
        ImpresoraB[] impresorasB=new ImpresoraB[5];
        
        Sala sala=new Sala(impresorasA.length,impresorasB.length);
        
        Thread[] hilosP=new Thread[50];
        /*Thread[] hilosA=new Thread[5];
        Thread[] hilosB=new Thread[5];*/
        
        for(int i=0;i<personas.length;i++){
            personas[i]=new Persona(sala);
            hilosP[i]=new Thread(personas[i],"PERSONA "+i);
            hilosP[i].start();
        }
        
        
    }
}

class ImpresoraA implements Runnable{
    private Sala sala;
    
    public ImpresoraA(Sala lugar){
        this.sala=lugar;
    }
    
    public void run(){
        
    }
}


class ImpresoraB implements Runnable{
    private Sala sala;
    
    public ImpresoraB(Sala lugar){
        this.sala=lugar;
    }
    
    public void run(){
        
    }
}

class Persona implements Runnable{
    private Sala sala;
    private char archivo;
    private char opcion;
    
    public Persona(Sala lugar){
        this.sala=lugar;
        Random a=new Random();
        int b=a.nextInt(4-1)+1;
        if(b==1){
            this.archivo='A';
            this.opcion='A';
        }
        else if(b==2){
            this.archivo='B';
            this.opcion='B';
        }
        else{
            this.archivo='C';
            this.opcion='C';
        }
    }
    
    public void imprimir(){
        System.out.println(Thread.currentThread().getName()+" ESTA IMPRIMIENDO ARCHIVO TIPO "+opcion);
        try {
            Thread.sleep(archivo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        archivo=sala.mandaImprimir(archivo);
        imprimir();
        sala.TerminarImpresion(archivo);
    }
}


class Sala{
    private int cantPendienteA;
    private int cantPendienteB;
    
    private int cantImpresorasA;
    private int cantImpresorasB;
    
    Lock cerrojo=new ReentrantLock();
    Condition impresorasA=cerrojo.newCondition();
    Condition impresorasB=cerrojo.newCondition();
    
    public Sala(int a, int b){
        this.cantImpresorasA=a;
        this.cantImpresorasB=b;
    }
    
    public char mandaImprimir(char archivo){
        cerrojo.lock();
        char res=archivo;
        if(archivo=='A'){
            try {
                this.cantPendienteA++;
                while(this.cantImpresorasA==0){
                    impresorasA.await();
                }    
                this.cantPendienteA--;
                this.cantImpresorasA--;
            } catch (InterruptedException ex) {
                Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(archivo=='B'){
            try {
                this.cantPendienteB++;
                while(this.cantImpresorasB==0){
                    impresorasB.await();
                }
                this.cantPendienteB--;
                this.cantImpresorasB--;
            } catch (InterruptedException ex) {
                Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            if(this.cantPendienteA<=this.cantPendienteB){
                try {
                    this.cantPendienteA++;
                    while(this.cantImpresorasA==0){
                        this.impresorasA.await();
                    }    
                    this.cantPendienteA--;
                    this.cantImpresorasA--;
                    res='A';
                } catch (InterruptedException ex) {
                    Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                try {
                    this.cantPendienteB++;
                    while(this.cantImpresorasB==0){
                        this.impresorasB.await();
                    }
                    this.cantPendienteB--;
                    this.cantImpresorasB--;
                    res='B';
                } catch (InterruptedException ex) {
                    Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        cerrojo.unlock();
        return res;
    }
    
    
    public void TerminarImpresion(char archivo){
        cerrojo.lock();
        if(archivo=='A'){
            this.cantImpresorasA++;
            impresorasA.signal();
            
        }
        else if(archivo=='B'){
            this.cantImpresorasB++;
            impresorasB.signal();
        }
        cerrojo.unlock();
    }
    
}