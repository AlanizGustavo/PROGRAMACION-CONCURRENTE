/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2Ejercicio2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class ThreadTeasting {
    public static void main(String[]args){
        Thread miHilo=new MiEjecucion();
        miHilo.start();
        try {
            miHilo.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadTeasting.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("En el main");
        
    }
}
