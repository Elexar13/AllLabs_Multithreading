package com.company.lab5.lab;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

class AverageCounterThread extends Thread {
    private final CountDownLatch startLatch;
    private final List<Integer> numberArray;

    public AverageCounterThread(CountDownLatch s, List<Integer> numberArray) {
        startLatch = s;
        this.numberArray = numberArray;
        System.out.println( "Thread(AverageCounterThread): created");
    }

    public void run() {
        System.out.println( "Thread(AverageCounterThread): started");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startLatch.countDown();
        System.out.println( "Thread(AverageCounterThread): completed");
    }
}

class AverageCounterThread extends Thread {
    private final CountDownLatch startLatch;
    private final List<Integer> numberArray;

    public AverageCounterThread(CountDownLatch s, List<Integer> numberArray) {
        startLatch = s;
        this.numberArray = numberArray;
        System.out.println( "Thread(AverageCounterThread): created");
    }

    public void run() {
        System.out.println( "Thread(AverageCounterThread): started");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startLatch.countDown();
        System.out.println( "Thread(AverageCounterThread): completed");
    }
}

public class CountDownLatchDemo {
    public static void main( String args[]) {
        int n = 2;
        System.out.println( "Main process started");
        CountDownLatch startLatch = new CountDownLatch(n);
        List<Integer> numberArray = Arrays.asList(1, 2, 3, 4, 5, 15); // sum = 30
        AverageCounterThread t = new AverageCounterThread(startLatch, numberArray);
        t.start();
        try {
            startLatch.await();
        }
        catch( InterruptedException e) {
            System.err.println(e);
        }
        System.out.println( "Main process stoped");
    }
}

