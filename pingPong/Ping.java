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

public class Ping {

    public static void main(String[] args) {
        PingPong a = new PingPong("Ping", 3);
        PingPong b = new PingPong("Pong", 5);

        a.start();
        b.start();
        System.out.println("TERMINO MAIN");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            System.out.println("Alcanzo el Exception");
        }
        System.out.println("TERMINO MAIN");
    }
}
