/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcrobaciaAerea;

import java.util.Random;

/**
 *
 * @author alanizgustavo
 */
public class Persona implements Runnable{
    private Salon salon;
    private int cantActividades;
    private int actividadRealizada;
    
    public Persona(Salon salon){
        this.salon=salon;
        this.cantActividades=2;
    }
    
    public void actividadRealizando(int act){
        System.out.println(Thread.currentThread().getName()+" ESTA REALIZANDO LA ACTIVIDAD: "+ act);
    }
    
    public void tomarTurno(){
        System.out.println(Thread.currentThread().getName()+" TOMO TURNO: "+ salon.getTurno());
        
    }
    
    public void run(){
        int i=0;
        Random a=new Random();
        salon.tomarTurno();
        tomarTurno();
        while(i<this.cantActividades){
            this.actividadRealizada=salon.elegirActividad(a.nextInt(3));
            this.actividadRealizando(this.actividadRealizada);
            salon.realizarActividadElegida();
            i++;
        }
    }
}
