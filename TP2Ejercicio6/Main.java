/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP2Ejercicio6;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Main {
    public static void main(String[]args){
        Cliente cliente1=new Cliente("Cliente 1",new int[]{2,2,1,5,2,3});
        Cliente cliente2=new Cliente("Cliente 2",new int[]{1,3,5,1,1});
        
        long initialTime = System.currentTimeMillis();
        CajeroThread cajero1=new CajeroThread("Cajero 1",cliente1,initialTime);
        CajeroThread cajero2=new CajeroThread("Cajero 2",cliente2,initialTime);
        cajero1.start();
        cajero2.start();
    }
}


class Cajero {
    private String nombre;

    public Cajero(String nombre) {
        this.nombre = nombre;
    }
    
    public void procesarCompra(Cliente cliente,long timeStamp){
        System.out.println("El cajero "+this.nombre+" COMIENZA A "
                + "PROCESAR LA COMPRA DEL CLIENTE "+ cliente.getNombre()
                +" EN EL TIEMPO: "+ (System.currentTimeMillis()-timeStamp)/1000 + "seg");
        for(int i=0;i<cliente.getCarroCompra().length;i++){
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesando el producto "+ (i+1)+
                    "->Tiempo: "+(System.currentTimeMillis()-timeStamp)/
                            1000+"seg");
        }
        System.out.println("El cajero "+this.nombre+" HA TERMINADO DE "
                + "PROCESAR "+ cliente.getNombre()+ " EN EL TIEMPO: "+ 
                        (System.currentTimeMillis()-timeStamp)/1000+"seg");
    }
    
    public void esperarXsegundos(int a){
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cajero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Cliente{
    private String nombre;
    private int[] carroCompra;

    public Cliente(String nombre, int[] carroCompra) {
        this.nombre = nombre;
        this.carroCompra = carroCompra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int[] getCarroCompra() {
        return carroCompra;
    }

    public void setCarroCompra(int[] carroCompra) {
        this.carroCompra = carroCompra;
    }
    
    
}

class CajeroThread extends Thread{
    private String nombre;
    private Cliente cliente;
    private long initialTime;

    public CajeroThread(String nombre, Cliente cliente, long initialTime) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.initialTime = initialTime;
    }
    
    public void run(){
        System.out.println("El cajero "+this.nombre+" COMIENZA A "
                + "PROCESAR LA COMPRA DEL CLIENTE "+ this.cliente.getNombre()
                +" EN EL TIEMPO: "+ (System.currentTimeMillis()-this.initialTime)/1000 + "seg");
        for(int i=0;i<this.cliente.getCarroCompra().length;i++){
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesando el producto "+ (i+1)+"del cliente "+ this.cliente.getNombre() +
                    "->Tiempo: "+(System.currentTimeMillis()-this.initialTime)/
                            1000+"seg");
        }
        System.out.println("El cajero "+this.nombre+" HA TERMINADO DE "
                + "PROCESAR "+ cliente.getNombre()+ " EN EL TIEMPO: "+ 
                        (System.currentTimeMillis()-this.initialTime)/1000+"seg");
    }
    
    public void esperarXsegundos(int a){
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cajero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
