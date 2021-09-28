/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingPong;

/**
 *
 * @author alanizgustavo
 */
public class PingPong extends Thread {

    private String cadena;
    private int delay;

    public PingPong(String cadena, int cantMilis) {
        this.cadena = cadena;
        this.delay = cantMilis;
    }

    public void run() {
        for (int i = 1; i < delay * 10; i++) {

            System.out.println(cadena + " iteracion: " + i);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Alcanzo el Exception");
            }
        }
    }
}
