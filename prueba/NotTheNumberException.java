/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

/**
 *
 * @author alanizgustavo
 */
public class NotTheNumberException extends Exception {
    public NotTheNumberException(){
        super();
    }
    
    public NotTheNumberException(String message){
        super(message);
    }
}
