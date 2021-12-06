/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pizzeria;

/**
 *
 * @author alanizgustavo
 */
public class Pizzeria {
    public static void main(String[] args){
        Negocio salon=new Negocio();
        
        for(int i=0;i<3;i++){
            new Thread(new Pizzero(salon),"PIZZERO "+i).start();
            new Thread(new Repartidor(salon),"REPARTIDOR "+i).start();
        }
        
        new Thread(new GeneradorPedidos(salon),"GENERADOR DE PEDIDOS ").start();
    }
}
