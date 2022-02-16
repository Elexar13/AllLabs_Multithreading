package com.company.lab3;

import java.util.Random;

class TestThread extends Thread {
    StringCreator stringCreator;
    String threadName;
    int delay;

    TestThread( String name, int t, StringCreator stringCreator) {
        threadName = name;
        delay = t;
        this.stringCreator = stringCreator;
        System.out.println( threadName + " - Created");
    }

    public void run() {
        System.out.println( threadName + " - Start of Work");
        for (int i = 0; i < 10; ++i) {
            if ("ThreadNumber".equals(threadName)) {
                System.out.println( threadName + " - Add number to text " + stringCreator.addNumberToString());
            } else {
                System.out.println( threadName + " - Add char to text " + stringCreator.addCharToString());
            }
            try {
                Thread.sleep( (int)((double)delay * 10.0 * Math.random()));
            }
            catch (InterruptedException ie) {
                System.out.println(ie);
            }
        }
        System.out.println( threadName + " - End of Work");
    }
}

class Lock {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while(isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}

public class StringCreator {
    Lock lock = new Lock();
    String text = "";

    public String addNumberToString() {
        try {
            lock.lock();
            text = text.concat(String.valueOf(new Random().nextInt(10)));
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String addCharToString() {
        try {
            lock.lock();
            if (text.charAt(text.length()-1) % 2 == 0) {
                text = text.concat("A");
            }
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void main(String[] args) {
        StringCreator stringCreator = new StringCreator();
        TestThread[] testThread = new TestThread[2];
        testThread[0] = new TestThread("ThreadNumber", 1, stringCreator);
        testThread[1] = new TestThread("ThreadChar", 2, stringCreator);
        testThread[0].start();
        testThread[1].start();
    }
}
