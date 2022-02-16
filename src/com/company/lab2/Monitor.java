package com.company.lab2;

class TestThread extends Thread {
    String threadName;
    Monitor m;

    TestThread(String name, Monitor mm) {
        threadName = name;
        m = mm;
        System.out.println( threadName + " - Created");
    }

    public void run() {
        m.procIncrement();
        System.out.println(threadName + " - Start of Work");
        try {
            Thread.sleep( 100);
        }
        catch (InterruptedException ie) {
        }
        m.procDecrement();
        synchronized(m) {
            m.notify();
            System.out.println( threadName + " - Signal sended");
        }
        System.out.println( threadName + " - End of Work");
    }
}

public class Monitor implements Runnable {
    int procNum = 0;

    Monitor(int procNumber) {
        TestThread[] t = new TestThread[procNumber];
        for (int i = 0; i < procNumber; ++i) {
            t[i] = new TestThread( "Proc:" + i, this);
            t[i].start();
        }
    }

    public void run() {
        System.out.println("Monitor - Started: " + getProcNum());
        try {
            while (getProcNum() != 0) {
                synchronized(this) {
                    System.out.println( "Monitor - Waiting: " + getProcNum());
                    wait();
                    if (getProcNum() == 3) {
                        printMyName();
                    }
                    System.out.println("Monitor - Signal received: " + getProcNum());
                }
            }
        }
        catch (InterruptedException ee) {
            System.out.println( "Monitor - Interrupted Exception: " + ee.toString());
        }
        System.out.println( "Monitor - Ended: " + getProcNum());
    }

    public synchronized void procIncrement() {
        ++procNum;
    }

    public synchronized void procDecrement() {
        --procNum;
    }

    public synchronized int getProcNum() {
        return(procNum);
    }

    public void printMyName() {
        System.out.println("\u001B[42m" + "Бочков Максим 402 група." + "\u001B[0m");
    }

    public static void main(String argc[]) {
        System.out.println("Main process started");
        Monitor m = new Monitor(5);
        new Thread(m).start();
        System.out.println("Main process ended");
    }
}