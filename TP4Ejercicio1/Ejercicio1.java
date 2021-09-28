/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP4Ejercicio1;

/**
 *
 * @author alanizgustavo
 */
public class Ejercicio1 {

}

class SynchronizedCounter {

    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}

class SynchronizedObjectCounter {

    private int c = 0;

    public void increment() {
        synchronized ((Integer)c) {
            c++;
        } // Este elemento debe ser casteado a Integer
    }
}
