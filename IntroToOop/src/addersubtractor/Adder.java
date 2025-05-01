package addersubtractor;

import java.util.concurrent.locks.Lock;

/*

 By implementing the Runnable interface, you're defining the task (i.e., the code that will run in a separate thread) inside the run() method.

*/
public class Adder implements Runnable{
    private Counter counter;
    private Lock lock;

    public Adder(Counter counter, Lock lock) {
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10000; i++) {
            System.out.println("Adding ith time: " + i);
            // If you want to move forward get the shared resource lock.
            lock.lock();
            System.out.println("Inside lock - Adder - "  + i);
            counter.i += 1;
            lock.unlock();
        }
    }
}
