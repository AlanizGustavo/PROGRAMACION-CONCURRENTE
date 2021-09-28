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
public class Arreglo {

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5,6,7,8};// puede utilizar distintos valores para ver los cambios con otros valores.
        MiHilo mh1 = MiHilo.creaEInicia("#1", arr);
        MiHilo mh2 = MiHilo.creaEInicia("#2", arr);
        /*try {
            mh1.hilo.join();
            mh2.hilo.join();
        } catch (InterruptedException exc) {
            System.out.println("Hilo principal interrumpido.");
        }*/
    }
}









class MiHilo implements Runnable {

    Thread hilo;
    static sumaMatriz sumaM = new sumaMatriz();
    int arr[];
    int resp;

    //Construye un nuevo hilo.
    MiHilo(String nombre, int nums[]) {
        hilo = new Thread(this, nombre);
        arr = nums;
    }

    //Un método que crea e inicia un hilo
    public static MiHilo creaEInicia(String nombre, int nums[]) {
        MiHilo miHilo = new MiHilo(nombre, nums);
        miHilo.hilo.start(); //Inicia el hilo
        return miHilo;
    }

    //Punto de entrada del hilo
    public void run() {
        int sum;
        System.out.println(hilo.getName() + " iniciando.");
        resp = sumaM.sumMatriz(arr);
        System.out.println("Suma para " + hilo.getName() + " es " + resp);
        System.out.println(hilo.getName() + " terminado.");
    }
}









class sumaMatriz {

    private int sum;

    synchronized int sumMatriz(int nums[]) {
        sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            System.out.println("Total acumulado de " + Thread.currentThread().getName() + " es " + sum);
            /*try {
                Thread.sleep(4000);//permitir el cambio de tarea 
            } catch (InterruptedException exc) {
                System.out.println("Hilo interrumpido");
            }*/
        }
        return sum;
    }
}
