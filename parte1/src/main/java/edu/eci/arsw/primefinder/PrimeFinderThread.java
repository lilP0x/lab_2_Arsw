package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrimeFinderThread extends Thread{

    int a, b;
    private List<Integer> primes = new LinkedList<Integer>();

    public PrimeFinderThread(int a, int b) {
        super();
        this.a = a;
        this.b = b;
    }

    public void run() {
        int count = 0;
        for (int i = a; i <= b; i++) {
            if (isPrime(i)) {
                primes.add(i);
                System.out.println(i);
            }
            count++;

           
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
