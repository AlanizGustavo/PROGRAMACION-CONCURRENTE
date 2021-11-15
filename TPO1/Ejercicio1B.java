/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPO1;

import java.util.concurrent.Semaphore;

/**
 *
 * @author alanizgustavo
 */
public class Ejercicio1B {
    public static void main(String[] args) {
        InstruccionesB in = new InstruccionesB(1, 2, 3);
        Thread [] arr = new Thread[4];
        for (int i = 0; i < args.length; i++) {
            arr[i] = new Thread(new Hilo(i+1, in), "t"+i);
            arr[i].start();
        }

        try {
            for (int i = 0; i < arr.length; i++) {
                arr[i].join();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
        System.out.println(in.getW());
    }
}


class Hilo implements Runnable{
    
    private int act;
    private InstruccionesB in;

    public Hilo(int act, InstruccionesB in){
        this.in = in;
        this.act = act; 
    }

    @Override
    public void run() {
        switch (this.act) {
            case 1:
                in.s1();
                break;
            case 2:
                in.s2();
                break;
            case 3:
                in.s3();
                break;
            case 4:
                in.s4();
                break;
        }
    }

}


class InstruccionesB {
    private int a;
    private int b;
    private int x;
    private int y;
    private int z;
    private int c;
    private int w;
    private Semaphore actS3; 
    private Semaphore actS4; 

    public InstruccionesB(int x, int y, int z){
        actS3 = new Semaphore(0);
        actS4 = new Semaphore(0);
        this.x = x;
        this.y = y;
        this.z = z;
        
    }

    public void s1(){
        a = x+y;
        actS3.release();
    }
    public void s2(){
        b = z+1;
        actS3.release();
    }
    public void s3(){
        try {
            actS3.acquire(2);
        } catch (Exception e) {
            //TODO: handle exception
        }
        c = a-b;
        actS4.release();
    }
    public void s4(){
        try {
            actS4.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        w = c+1;
    }

    public int getW(){
        return w;
    }
}
