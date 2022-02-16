package com.company.lab1;

class TestThread extends Thread {
    String threadName;
    CommonVariable parentClass;

    TestThread( String name, CommonVariable c) {
        threadName = name;
        parentClass = c;
        System.out.println(threadName + " - Created");
    }

    public void run() {
        System.out.println( threadName + " - Start of Work");
        while (parentClass.state != true) {
            System.out.println( threadName + " - " + parentClass.state);
            if (threadName.equals("First")) {
                System.out.println("The first thread will terminate other threads!");
                parentClass.shutdownAllChildThreads = true;
            }
            if (parentClass.shutdownAllChildThreads) {
                parentClass.state = true;
            } else {
                try {
                    Thread.sleep( 10);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
        System.out.println( threadName + " - " + parentClass.state);
        System.out.println( threadName + " - End of Work");
    }
}

public class CommonVariable {
    boolean state = false;
    boolean shutdownAllChildThreads = false;

    CommonVariable() {
        state = false;
    }

    public static void main( String argc[]) {
        System.out.println( "Main process started");
        CommonVariable cv = new CommonVariable();
        TestThread t1 = new TestThread( "First", cv);
        TestThread t2 = new TestThread( "Second", cv);
        TestThread t3 = new TestThread( "Third", cv);

        t1.start();
        t2.start();
        t3.start();
        System.out.println( "Main process: " + cv.state);
        try {
            Thread.sleep( 60);
        }
        catch (InterruptedException ie) {
        }
        cv.state = true;
        System.out.println( "Main process: " + cv.state);
        System.out.println( "Main process ended");
    }
}