/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;
import java.io.*;
/**
 *
 * @author alanizgustavo
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AgeException, NotTheNumberException {
        
        edad(22);
        juegoRuleta(8);
    }
    
    public static void edad(int num) throws AgeException{
       
            if(num<18){
                throw new AgeException("Es menor de edad!!!!");
            }
    }
    
    public static void juegoRuleta(int num) throws NotTheNumberException{
        int n=(int)(Math.random() * ((10 - 1) + 1)) + 1;
        if(num!=n){
            throw new NotTheNumberException("Chupala puto");
        }
    }
}
