/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPO1;

/**
 *
 * @author alanizgustavo
 */
public class Ejercicio1 {
    public static void main(String[] args){
        Instrucciones in = new Instrucciones(1, 2, 3);
        Thread t1 = new Thread(new t1(in), "t1");
        Thread t2 = new Thread(new t2(in), "t2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            //TODO: handle exception
        }

        in.s3();
        in.s4();
        System.out.println(in.getW());
    }
}


class Instrucciones {
    
    private int a;
    private int b;
    private int x;
    private int y;
    private int z;
    private int c;
    private int w; 

    public Instrucciones(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        
    }

    public void s1(){
        a = x+y;
    }
    public void s2(){
        b = z+1;
    }
    public void s3(){
        c = a-b;
    }
    public void s4(){
        w = c+1;
    }

    public int getW(){
        return w;
    }
}



class t1 implements Runnable{

    private Instrucciones aux;

    public t1(Instrucciones i){
        aux = i;
    }

    @Override
    public void run() {
        aux.s1();
    }
}



class t2 implements Runnable{

    private Instrucciones aux;

    public t2(Instrucciones i){
        aux = i;
    }

    @Override
    public void run() {
        aux.s2();
    }
}


