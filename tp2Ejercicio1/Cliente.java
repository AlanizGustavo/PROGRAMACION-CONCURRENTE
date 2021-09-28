/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2Ejercicio1;

/**
 *
 * @author alanizgustavo
 */
public class Cliente extends Thread{
    public void run(){
        System.out.println("soy "+Thread.currentThread().getName());
        Recursos.uso();
        try{
            Thread.sleep(0000);
        }catch(InterruptedException e){
            
        }
    }
}    
