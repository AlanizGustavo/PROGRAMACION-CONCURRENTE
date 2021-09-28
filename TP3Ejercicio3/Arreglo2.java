/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP3Ejercicio3;

/**
 *
 * @author alanizgustavo
 */
public class Arreglo2 {
     public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5,6,7,8,9,19,11,3,2,343,364,457};// puede utilizar distintos valores para ver los cambios con otros valores.
        MiHilo2 mh1 = MiHilo2.creaEInicia("#1", arr);
        MiHilo2 mh2 = MiHilo2.creaEInicia("#2", arr);
        /*try {
            mh1.hilo.join();
            mh2.hilo.join();
        } catch (InterruptedException exc) {
            System.out.println("Hilo principal interrumpido.");
        }*/
    }
}



class MiHilo2 implements Runnable {

    Thread hilo;
    static sumaMatriz2 sumaM = new sumaMatriz2();
    int arr[];
    int resp;

    //Construye un nuevo hilo.
    MiHilo2(String nombre, int nums[]) {
        hilo = new Thread(this, nombre);
        arr = nums;
    }

    //Un método que crea e inicia un hilo
    public static MiHilo2 creaEInicia(String nombre, int nums[]) {
        MiHilo2 miHilo = new MiHilo2(nombre, nums);
        miHilo.hilo.start(); //Inicia el hilo
        return miHilo;
    }

    //Punto de entrada del hilo
    public void run() {
        int sum;
        System.out.println(hilo.getName() + " iniciando.");
        resp = sumaM.sumMatriz2(arr);
        System.out.println("Suma para " + hilo.getName() + " es " + resp);
        System.out.println(hilo.getName() + " terminado.");
    }
}









class sumaMatriz2 {

    private int sum=0;
    private int pos=0;
    int sumMatriz2(int nums[]) {
        
        synchronized (this){while(pos<nums.length){
            sum += nums[pos];
            pos++;
            System.out.println("Total acumulado de " + Thread.currentThread().getName() + " es " + sum);
            /*try {
                Thread.sleep(4000);//permitir el cambio de tarea 
            } catch (InterruptedException exc) {
                System.out.println("Hilo interrumpido");
            }*/
        }}
        return sum;
    }
}
