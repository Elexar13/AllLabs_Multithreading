package com.company.lab4;

class SendingThread extends Thread {
    BoundedSemaphore semaphore = null;
    String name;
    long procNum;

    SendingThread( String n, long p, BoundedSemaphore semaphore) {
        this.semaphore = semaphore;
        name = n;
        procNum = p;
        System.out.println( name + ": created");
    }

    public void run() {
        System.out.println( name + ": started");
        for (int i = 0; i < 4; ++i) {
            System.out.println( name + ": do(" + (i * procNum) + ")");
            try {
                this.semaphore.take( i * procNum);
            }
            catch (InterruptedException ie) {
            }
        }
        System.out.println( name + ": stoped");
    }
}

class ReceivingThread extends Thread {
    BoundedSemaphore semaphore = null;
    String name;
    int procNum;
    long sum = 0;

    ReceivingThread( String n, int p, BoundedSemaphore semaphore) {
        this.semaphore = semaphore;
        name = n;
        procNum = p;
        System.out.println( name + ": created");
    }

    public void run() {
        System.out.println( name + ": started");
        while(true) {
            try {
                long m = this.semaphore.release();
                sum += m;
                System.out.println( name + ": do(" + m + "), " + sum);
            }
            catch (InterruptedException ie) {
            }
        }
    }
}

class BoundedSemaphore {
    private int signals = 0;
    private int bound = 0;
    private long message = 0;

    public BoundedSemaphore( int upperBound) {
        this.bound = upperBound;
    }

    public synchronized void take( long m) throws InterruptedException {
        while(this.signals == bound) {
            wait();
        }
        this.signals++;
        System.out.println( "BoundedSemaphore(take): " + this.signals);
        message = m;
        this.notify();
    }

    public synchronized long release() throws InterruptedException {
        while(this.signals == 0) {
            wait();
        }
        this.signals--;
        System.out.println( "BoundedSemaphore(release): " + this.signals);
        long m = message;
        message = 0;
        this.notify();
        return( m);
    }
}

public class BoundedSemaphoreDemo {
    public static void main( String argc[]) {
        System.out.println( "Main process started");
        BoundedSemaphore semaphore = new BoundedSemaphore( 3);
        SendingThread sender1 = new SendingThread( "Sender 1", 1, semaphore);
        SendingThread sender2 = new SendingThread( "Sender 2", 10, semaphore);
        SendingThread sender3 = new SendingThread( "Sender 3", 100, semaphore);
        SendingThread sender4 = new SendingThread( "Sender 4", 1000, semaphore);
        SendingThread sender5 = new SendingThread( "Sender 5", 10000, semaphore);
        SendingThread sender6 = new SendingThread( "Sender 6", 100000, semaphore);
        ReceivingThread receiver = new ReceivingThread( "Receiver", 1, semaphore);

        receiver.start();
        sender1.start();
        sender2.start();
        sender3.start();
        sender4.start();
        sender5.start();
        sender6.start();
        System.out.println( "Main process ended");
    }
}
