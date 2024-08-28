package edu.eci.arsw.primefinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.omg.CORBA.PRIVATE_MEMBER;

public class ControlThreads extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 10000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;
    private final Object monitor = new Object();
    private PrimeFinderThread[] pft;
    private BufferedReader reader;
    private AtomicBoolean isPaused;
    private int cont = 0;

    public ControlThreads() {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.isPaused = new AtomicBoolean(false);  

        int range = MAXVALUE / NTHREADS;
        int startIndex = 0;
        int occurrencesCount = 0;
        for (int i = 0; i < NTHREADS; i++) {
            int endIndex;
            if (i == NTHREADS - 1) {
                // Último hilo: debe cubrir hasta el final
                endIndex = MAXVALUE;
            } else {
                // Hilos intermedios: cubrir hasta el final del rango calculado
                endIndex = startIndex + range;
            }

            pft[i] = new PrimeFinderThread(startIndex, endIndex, monitor, isPaused);        
        }
    }

    @Override
    public void run() {
        for (PrimeFinderThread thread : pft) {
            thread.start();
        }

        while (true) {
            try {
                Thread.sleep(TMILISECONDS);
                isPaused.set(true);
                for (PrimeFinderThread thread : pft) {
                    cont =+ thread.getPrimes().size();
                }
                System.out.println(cont);
                System.out.println("Presione Enter para continuar...");
                reader.readLine();  
                isPaused.set(false);  
                synchronized (monitor) {
                    monitor.notifyAll(); 
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
