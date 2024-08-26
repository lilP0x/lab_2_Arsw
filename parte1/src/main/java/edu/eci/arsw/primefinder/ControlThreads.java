package edu.eci.arsw.primefinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

public class ControlThreads extends Thread{
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;
    private final Object monitor = new Object();
    private PrimeFinderThread[] pft;
    private BufferedReader reader;


    public ControlThreads(){
        
        this.pft = new PrimeFinderThread[NTHREADS];
        this.reader = new BufferedReader(new InputStreamReader(System.in));


    }



}