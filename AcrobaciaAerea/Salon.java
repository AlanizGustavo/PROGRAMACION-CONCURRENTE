/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AcrobaciaAerea;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alanizgustavo
 */
public class Salon {

    private int cantPersonas;
    private int numTurno;

    private Semaphore turno;
    private Semaphore act0;
    private Semaphore act1;
    private Semaphore act2;
    private Semaphore mutex;

    private Semaphore realizandoEjercicios;

    private Semaphore reloj;

    public Salon() {
        this.turno = new Semaphore(12);                                         //Semaforo mutex de sala
        this.act0 = new Semaphore(4);                                           //Semaforo mutex de act1
        this.act1 = new Semaphore(4);                                           //Semaforo mutex de act2
        this.act2 = new Semaphore(4);                                           //Semaforo mutex de act3
        this.mutex= new Semaphore(1);

        this.realizandoEjercicios = new Semaphore(0,true);                      //Semaforo para bloquear los hilos y simular la actividad

        this.reloj = new Semaphore(0);

        this.cantPersonas = 0;
        this.numTurno = 1;
    }

    public void tomarTurno() {
        try {
            turno.acquire();

        } catch (InterruptedException ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int elegirActividad(int act) {
        Random a = new Random();
        int actividad = act;
        boolean exito = false;
        while (!exito) {

            switch (actividad) {
                case 0:
                    if (act0.tryAcquire()) {
                        exito = true;
                        try {
                            this.mutex.acquire();
                            this.cantPersonas++;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.mutex.release();
                    }

                    break;

                case 1:
                    if (act1.tryAcquire()) {
                        exito = true;
                        try {
                            this.mutex.acquire();
                            this.cantPersonas++;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.mutex.release();
                    }
                    break;
                case 2:
                    if (act2.tryAcquire()) {
                        exito = true;
                        try {
                            this.mutex.acquire();
                            this.cantPersonas++;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.mutex.release();
                    }
                    break;
            }

            if (!exito) {
                actividad = a.nextInt(3);
            }
        }
        try {
            mutex.acquire();
            if (this.cantPersonas == 12) {

                reloj.release();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }
        mutex.release();
        return actividad;
    }

    public void realizarActividadElegida() {
        try {
            this.realizandoEjercicios.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarConteo() {
        try {
            this.reloj.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void timbreCambioActividades() {
        try {
            this.act0.release(4);
            this.act1.release(4);
            this.act2.release(4);
            this.cantPersonas=0;
            this.realizandoEjercicios.release(12);
            this.reloj.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void timbreCambioTurno() {
        this.numTurno++;
        this.realizandoEjercicios.release(12);
        this.cantPersonas = 0;
        this.act0.release(4);
        this.act1.release(4);
        this.act2.release(4);
        this.turno.release(12);
    }

    public int getTurno() {
        return this.numTurno;
    }

}
