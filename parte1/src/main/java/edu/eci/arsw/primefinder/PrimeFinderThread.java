package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeFinderThread extends Thread {

    int a, b;
    private List<Integer> primes = new LinkedList<Integer>();
    private final Object monitor;
    private final AtomicBoolean isPaused;

    public PrimeFinderThread(int a, int b, Object monitor, AtomicBoolean isPaused) {
        super();
        this.a = a;
        this.b = b;
        this.monitor = monitor;
        this.isPaused = isPaused;  
    }

    public void run() {
        for (int i = a; i <= b; i++) {
            synchronized (monitor) {
                while (isPaused.get()) {  // Comprueba si debe pausar el hilo
                    try {
                        monitor.wait();  
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (isPrime(i)) {
                primes.add(i);
                //System.out.println(i);
            }
        }
    }

    boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }
}
