package edu.eci.arsw.primefinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlThreads extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 10;
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

        for (int i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA, monitor, isPaused);
            pft[i] = elem;
        }
        pft[NTHREADS - 1] = new PrimeFinderThread((NTHREADS - 1) * NDATA, MAXVALUE + 1, monitor, isPaused);
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
