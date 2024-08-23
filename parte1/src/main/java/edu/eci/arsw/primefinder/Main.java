package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {		
		PrimeFinderThread pft1=new PrimeFinderThread(0, 100);
		PrimeFinderThread pft2=new PrimeFinderThread(100, 200);
		PrimeFinderThread pft3=new PrimeFinderThread(200, 300);
		long startTime = System.nanoTime();

		pft1.start();
		pft2.start();
		pft3.start();
		PrimeFinderThread[] threads = {pft1, pft2, pft3};

        for (int i = 0; i < threads.length; i++) {
            try {
                // Cada 5 segundos, hacer que un hilo se duerma
                TimeUnit.SECONDS.sleep(5);
                synchronized (threads[i]) {
                   // threads[i].pauseThread();  // Pausar el hilo
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	
		
 
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
		System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
		
		
	}
	
}

