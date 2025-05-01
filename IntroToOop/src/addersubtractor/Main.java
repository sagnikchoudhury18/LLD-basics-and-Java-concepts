package addersubtractor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Lock lock = new ReentrantLock();
        Adder adder = new Adder(counter, lock);
        Subtractor subtractor = new Subtractor(counter, lock);

        Thread t1 = new Thread(adder);
        Thread t2 = new Thread(subtractor);

        t1.start();
        t2.start();

        System.out.println("Threads started....");

        // Blocking the main thread and making it wait for the other
        // threads to complete
        t1.join();
        t2.join();

        System.out.println("Value of the i in counter: " + counter.i);

/*
t1.join();
t2.join();
are used to block the main thread (the thread executing the main() method) until both t1 and t2 have finished executing.

Why is this important?
Without join(), the main thread could reach this line:

System.out.println("Value of the i in counter: " + counter.i);
before t1 and t2 finish their execution. This would mean you're printing the value of counter.i before the threads finish modifying it, giving you an incomplete or incorrect result.
    */

    }
}
