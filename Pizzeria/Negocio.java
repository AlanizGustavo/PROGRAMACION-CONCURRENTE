/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pizzeria;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Negocio {
    private int cantPizas;
    
    private int maxMostrador=10;
    
    private Lock cerrojoPizasEmpleados;
    private Lock cerrojoPedidos;
    private Lock cerrojoMostrador;
    
    private Condition mostradorLleno;
    private Condition mostradorVacio;
    private Condition pizasEmpleadoVacia;
    private Condition pedidosVacio;
    
    private LinkedList pedidos=new LinkedList();
    private LinkedList mostrador=new LinkedList();
    private LinkedList pizasEmpleados=new LinkedList();
    
    
    public Negocio(){
        cerrojoPedidos=new ReentrantLock();
        cerrojoMostrador=new ReentrantLock();
        cerrojoPizasEmpleados=new ReentrantLock();
        
        mostradorLleno=cerrojoMostrador.newCondition();
        mostradorVacio=cerrojoMostrador.newCondition();
        pedidosVacio=cerrojoPedidos.newCondition();
        pizasEmpleadoVacia=cerrojoPizasEmpleados.newCondition();
    }
    
    public void agregarPedido(Pedidos pedido){
        cerrojoPedidos.lock();
        pedidos.add(pedido);
        pedidosVacio.signal();
        cerrojoPedidos.unlock();
    }
    
    public Pedidos hacerPedido(){
        cerrojoPedidos.lock();
        while(this.pedidos.isEmpty()){
            try {
                this.pedidosVacio.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Pedidos pedido=(Pedidos)pedidos.poll();
        cerrojoPedidos.unlock();
        return pedido;
    }
    
    public void dejarEnMostrador(Pedidos pedido){
        cerrojoMostrador.lock();
        while(this.mostrador.size()==this.maxMostrador){
            try {
                mostradorLleno.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        mostrador.add(pedido);
        this.cantPizas++;
        mostradorVacio.signal();
        cerrojoMostrador.unlock();
    }
    
    public Pedidos hacerReparto(){
        cerrojoMostrador.lock();
        while(this.mostrador.isEmpty()){
            try {
                this.mostradorVacio.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Pedidos pedido=(Pedidos)mostrador.poll();
        mostradorLleno.signal();
        cerrojoMostrador.unlock();
        return pedido;
    }
    
    public void hacerPizaEmpleados(){
        cerrojoMostrador.lock();
        
        if(this.cantPizas%10==0){
            
            this.cerrojoPizasEmpleados.lock();
            pizasEmpleados.add("UNA PIZA");
            
            pizasEmpleadoVacia.signal();
            this.cerrojoPizasEmpleados.unlock();
            this.cantPizas=0;
        }
        cerrojoMostrador.unlock();
    }
    
    public void comerPiza(){
        while(this.pizasEmpleados.isEmpty()){
            try {
                
                this.pizasEmpleadoVacia.await();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pizasEmpleados.poll();
    }
}
