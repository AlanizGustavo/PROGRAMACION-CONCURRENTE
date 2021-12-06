/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pizzeria;

/**
 *
 * @author alanizgustavo
 */
public class Pedidos {
    private String tipo;
    private String nombre;
    
    public Pedidos(String tipo,String nombre){
        this.tipo=tipo;
        this.nombre=nombre;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public String getNombre(){
        return this.nombre;
    }
}
