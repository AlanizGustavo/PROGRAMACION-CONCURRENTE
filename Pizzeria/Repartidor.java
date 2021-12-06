/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pizzeria;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Repartidor implements Runnable {

    private Negocio negocio;
    private int cantRepartos;

    public Repartidor(Negocio negocio) {
        this.negocio = negocio;
        this.cantRepartos = 0;
    }

    public void repartir(String nombre) {
        System.out.println(Thread.currentThread().getName() + " HACE REPARTO A: "+nombre);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Repartidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void comerPiza(){
        System.out.println(Thread.currentThread().getName()+" ESTA COMIENDO PIZA");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Repartidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        Pedidos pedido;
        while (true) {
            pedido=negocio.hacerReparto();
            repartir(pedido.getNombre());
            this.cantRepartos++;
            System.out.println(this.cantRepartos);
            if (this.cantRepartos % 10 == 0) {
                System.out.println("###########################");
                negocio.comerPiza();
                comerPiza();
            }
        }
    }
}
