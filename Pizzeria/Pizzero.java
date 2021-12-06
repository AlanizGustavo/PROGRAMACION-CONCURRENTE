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
public class Pizzero implements Runnable{
    private Negocio negocio;
    
    public Pizzero(Negocio negocio){
        this.negocio=negocio;
    }
    
    public void hacerPedido(String tipo){
        if(tipo=="Napolitana"){
            System.out.println(Thread.currentThread().getName()+" ESTA COCINANDO UNA PIZA "+tipo);
        }
        else{
            System.out.println(Thread.currentThread().getName()+" ESTA COCINANDO 2 PIZAS "+tipo);
            
        }
    }
    
    public void run(){
        Pedidos pedido;
        while(true){
            pedido=negocio.hacerPedido();
            hacerPedido(pedido.getTipo());
            negocio.dejarEnMostrador(pedido);
            negocio.hacerPizaEmpleados();
        }
    }
}
