/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pizzeria;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class GeneradorPedidos implements Runnable {

    private Negocio negocio;
    private int numeroPedido = 0;
    private Pedidos pedido;

    public GeneradorPedidos(Negocio negocio) {
        this.negocio = negocio;
    }

    public void tomarPedido(String pedido) {
        System.out.println(Thread.currentThread().getName() + " ESTA TOMANDO PEDIDO DE: " + pedido);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(GeneradorPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        Random a = new Random();
        while (true) {
            if (a.nextInt(2) == 0) {
                pedido = new Pedidos("Napolitana", "Cliente " + this.numeroPedido);
                this.numeroPedido++;
            } else {
                pedido = new Pedidos("Vegana", "Cliente " + this.numeroPedido);
                this.numeroPedido++;

            }
            tomarPedido(pedido.getTipo());
            negocio.agregarPedido(pedido);
        }
    }
}
