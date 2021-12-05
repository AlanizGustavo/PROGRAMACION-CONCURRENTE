/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcrobaciaAerea;


/**
 *
 * @author alanizgustavo
 */
public class SalonAcrobacia {
    public static void main(String[] args){
        Salon salon=new Salon();
        
        for(int i=0;i<50;i++){
            new Thread(new Persona(salon),"PERSONA "+i).start();
        }
        
        new Thread(new Reloj(salon),"RELOJ ").start();
        
    }
}
