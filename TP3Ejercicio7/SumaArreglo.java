/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP3Ejercicio7;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 *
 * @author alanizgustavo
 */
public class SumaArreglo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //LLENO EL ARREGLO CON RANDOM
        int [] arreglo=new int[50000];
        for(int i=0;i<arreglo.length;i++){
            arreglo[i]=(new Random().nextInt(10));
        }
        

        //LEE UNA CANTIDAD DE HILOS
        System.out.println("INGRESE LA CANTIDAD DE HILOS");
        int cantHilos = sc.nextInt();

        MiHilo[] arreglo2 = new MiHilo[cantHilos];
        Thread[] arreglo3 = new Thread[cantHilos];

        int valorIn = 0;
        int intervalo = (arreglo.length / cantHilos);
        System.out.println(intervalo);
        int valorFin = intervalo;
        int diferencia =arreglo.length % cantHilos;
        for (int i = 0; i < cantHilos; i++) {
            if(cantHilos-1==i && diferencia!=0){
                arreglo2[i] = new MiHilo("Hilo " + i, arreglo, valorIn, valorFin - 1+diferencia);
                
            }else{
                arreglo2[i] = new MiHilo("Hilo " + i, arreglo, valorIn, valorFin - 1);
            }
                arreglo3[i] = new Thread(arreglo2[i], "Hilo " + i);
                arreglo3[i].start();
                valorIn = valorIn + intervalo;
                valorFin = valorFin + intervalo;    
                
        }

        try {
            for (int i = 0; i < cantHilos; i++) {

                arreglo3[i].join();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SumaArreglo.class.getName()).log(Level.SEVERE, null, ex);
        }

        int valorFinal = 0;

        for (int i = 0; i < cantHilos; i++) {

            valorFinal += arreglo2[i].getParcial();
        }

        System.out.println(valorFinal);
    }
}

class MiHilo implements Runnable {

    Thread hilo;
    static sumaMatriz sumaM = new sumaMatriz();
    int arr[];
    int sumaParcial;
    int posInicial;
    int posFinal;

    //Construye un nuevo hilo.
    public MiHilo(String nombre, int nums[], int inicial, int finals) {
        hilo = new Thread(this, nombre);
        arr = nums;
        this.posFinal = finals;
        this.posInicial = inicial;
    }

    public int getParcial() {
        return this.sumaParcial;
    }

    //Punto de entrada del hilo
    public void run() {
        int sum;
        System.out.println(hilo.getName() + " iniciando.");
        sumaParcial = sumaM.sumMatriz(arr, this.posInicial, this.posFinal);
        System.out.println("Suma para " + hilo.getName() + " es " + sumaParcial);
        System.out.println(hilo.getName() + " terminado.");
    }
}



class sumaMatriz {

    public synchronized int sumMatriz(int nums[], int posInicial, int posFinal) {
        int sum = 0;
        int pos = posInicial;
        while (pos <= posFinal) {
            //System.out.println(pos);
            sum += nums[pos];
            pos++;
            //System.out.println("Total acumulado de " + Thread.currentThread().getName() + " es " + sum);
            /*try {
                Thread.sleep(10);//permitir el cambio de tarea 
            } catch (InterruptedException exc) {
                System.out.println("Hilo interrumpido");
            }*/
        }

        return sum;
    }
}
