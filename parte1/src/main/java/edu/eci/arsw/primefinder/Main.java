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

		pft1.start();
	
		try {
			synchronized (pft1) {
				System.out.println("se está durmiendo el hilo");
				pft1.wait(5000);
			} 
		}catch (InterruptedException e) {
			e.printStackTrace();
		}	
	
		try {
			synchronized (pft2) {
				System.out.println("se está durmiendo el hilo");
				pft2.wait(5000);
			} 
		}catch (InterruptedException e) {
			e.printStackTrace();
		}	

		try {
			synchronized (pft3) {
				System.out.println("se está durmiendo el hilo");
				pft3.wait(5000);
			} 
		}catch (InterruptedException e) {
			e.printStackTrace();
		}	

		long startTime = System.nanoTime();
 
        try {
			TimeUnit.SECONDS.sleep(14);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
		System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
		
		
	}
	
}

