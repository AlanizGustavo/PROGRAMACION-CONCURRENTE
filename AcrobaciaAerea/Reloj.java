/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcrobaciaAerea;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Reloj implements Runnable{
    private Salon salon;
    
    public Reloj(Salon salon){
        this.salon=salon;
    }
    
    public void pasarTiempo(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Reloj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tocarTimbreCambioActividades(){
        System.out.println(Thread.currentThread().getName()+" TOCO TIMBRE DE CAMBIO DE ACTIVIDADES");
    }
    
    public void tocarTimbreCambioATurno(){
        System.out.println(Thread.currentThread().getName()+" TOCO TIMBRE DE CAMBIO DE TURNO");
    }
    
    public void run(){
        while(true){
            salon.iniciarConteo();
            pasarTiempo();
            this.tocarTimbreCambioActividades();
            salon.timbreCambioActividades();
            this.pasarTiempo();
            this.tocarTimbreCambioATurno();
            salon.timbreCambioTurno();
        }
    }
}
