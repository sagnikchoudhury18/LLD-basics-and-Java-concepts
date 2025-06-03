# Threads and Multithreading
- [Threads and Multithreading](#threads-and-multithreading)
  - [Threads](#threads)
    - [Thread vs Process](#thread-vs-process)
    - [Concurrency vs Parallelism](#concurrency-vs-parallelism)
    - [Using threads in Java](#using-threads-in-java)
      - [Number printer](#number-printer)
  - [Executor](#executor)
  - [Callable and Future](#callable-and-future)
  - [Assignment](#assignment)
  - [Reading List](#reading-list)

## Threads 

> A thread is a lightweight process. It is a unit of execution within a process. A process can have multiple threads. Each thread has its own program counter, stack, and registers. Threads share the same address space. This means that all threads in a process can access the same memory. This is different from processes where each process has its own address space.

Often, a process needs to perform multiple tasks at the same time. For example, a web browser needs to download a file and display a web page at the same time. Creating a new process for each task is expensive. This is because creating a new process requires a lot of resources.

Threads are used to solve this problem. Threads are used to perform multiple tasks within a process. This is done by sharing the same address space. This means that all threads in a process can access the same memory. This is different from processes where each process has its own address space.

Thread is a sequential flow of tasks within a process. Threads in OS can be of the same or different types. Threads are used to increase the performance of the applications.
Each thread has its own program counter, stack, and set of registers. But the threads of a single process might share the same code and data/file. Threads are also termed as lightweight processes as they share common resources.

![Threads](https://scaler.com/topics/images/what-is-thread-in-os.webp)


### Thread vs Process
| Process                                                                          | Thread                                                                                                     |
| -------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| Processes use more resources and hence they are termed as heavyweight processes. | Threads share resources and hence they are termed as lightweight processes.                                |
| Creation and termination times of processes are slower.                          | Creation and termination times of threads are faster compared to processes.                                |
| Processes have their own code and data/file.                                     | Threads share code and data/file within a process.                                                         |
| Communication between processes is slower.                                       | Communication between threads is faster.                                                                   |
| Context Switching in processes is slower.                                        | Context switching in threads is faster.                                                                    |
| Processes are independent of each other.                                         | Threads, on the other hand, are interdependent. (i.e they can read, write or change another thread’s data) |
| Eg: Opening two different browsers.                                              | Eg: Opening two tabs in the same browser.                                                                  |

![Threads vs Process](https://scaler.com/topics/images/process-vs-thread.webp)

### Concurrency vs Parallelism

* Concurrent - At the same time, but not necessarily at the same instant. It is possible for multiple threads to be at different stages of execution at the same time but not being processed together. A single core CPU can only execute one thread at a time. But it can switch between threads very quickly. This is called context switching. This is how concurrency is achieved. A single core CPU can have concurrency but not parallelism.
* Parallel - At the same time and at the same instant. It is possible for multiple threads to be at different stages of execution at the same time and being processed together. A single core CPU cannot achieve parallelism. It can only achieve concurrency. A multi-core CPU can achieve both concurrency and parallelism.

### Thread Lifecycle

🔄 Thread Lifecycle in Java
A thread in Java goes through several stages during its lifecycle. These stages are managed by the JVM and the Thread API:

✅ 1. New (Created)
A thread is in this state when it's created using:
```
Thread t = new Thread();
```

It is not yet started.

▶️ 2. Runnable
After calling .start() on the thread:

t.start();
The thread is ready to run, but not necessarily running immediately.

The thread scheduler decides when to move it to the running state.

🏃 3. Running
The thread is executing its run() method.

This is an active state.

😴 4. Blocked/Waiting/Sleeping
Thread temporarily not eligible for running due to:

Waiting for a monitor lock (synchronized)

Calling wait()

Calling sleep(time)

Calling join() on another thread

🔚 5. Terminated (Dead)
Thread completes execution of run(), or is stopped due to an exception.

Cannot be restarted.

🌙 Daemon Threads in Java
A daemon thread is a background thread that provides services to user (non-daemon) threads.

✅ Characteristics:
It runs in the background, e.g., Garbage Collector (GC).

JVM does not wait for daemon threads to finish before exiting.

If only daemon threads are left, JVM exits.

Should not be used for tasks requiring persistence or reliability.

✅ How to Create a Daemon Thread:

```
Thread t = new Thread(() -> {
    while (true) {
        System.out.println("Daemon running...");
    }
});
t.setDaemon(true); // Must be set before t.start()
t.start();
```

❗ Note:
Calling setDaemon(true) after start() throws IllegalThreadStateException.

🆚 User Thread vs Daemon Thread

| **Feature**     | **User Thread**                       | **Daemon Thread**                                    |
|-----------------|----------------------------------------|------------------------------------------------------|
| **Importance**  | Main application tasks                 | Background tasks                                     |
| **JVM waits?**  | Yes                                    | No                                                   |
| **Shutdown**    | JVM waits until completion             | JVM shuts down immediately if only daemons remain    |
| **Example**     | Main thread, app logic thread          | GC, monitoring/logging thread                        |




### Using threads in Java


In Java, we can create a thread by extending the Thread class or by implementing the Runnable interface. The Thread class is a subclass of the Object class. It implements the Runnable interface. The Runnable interface has a single method called run(). This method is called when the thread is started.

```java
class NewThread implements Runnable {
    @Override
    public void run() {
        // Code to be executed by the thread
    }
}
```

We can create a new thread by creating an object of the NewThread class and passing it to the Thread class constructor. The Thread class constructor takes a Runnable object as an argument. This Runnable object is the thread that we want to create.

```java
NewThread newThread = new NewThread();
Thread thread = new Thread(newThread);
```

To run the thread, we call the start() method on the Thread object. This method calls the run() method of the Runnable object. The run() method is executed by the thread.

```java
thread.start();
```

### The Task
The task refers to the work or logic that needs to be executed by the thread. This logic is defined in the run() method of the Runnable implementation.

### The Thread
The thread refers to the actual thread of execution provided by the Thread class. It is responsible for running the task defined in the run() method.

Thread thread = new Thread(newThread);

In this line:

1. A Thread object is created, wrapping around the NewThread task (the Runnable object).
2. The Thread class takes care of scheduling and executing the run() method in a separate thread of execution when thread.start() is called.

The Thread object internally calls the run() method of the Runnable object (newThread) and executes it in a separate thread of execution.



**Problem Statement 1**
* Create a new thread that prints the numbers from 1 to 10.

**Solution**
```java
class NumberPrinter implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
        }
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        NumberPrinter numberPrinter = new NumberPrinter();
        Thread thread = new Thread(numberPrinter);
        thread.start();
    }
}
```

<br>

<br>

<br>

**Problem Statement 2**
* Print the numbers from 1 to 10 where each number is printed by a different thread.

***Solution***
```java
class NumberPrinter implements Runnable {
    private int number;

    public NumberPrinter(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println(number);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            NumberPrinter numberPrinter = new NumberPrinter(i);
            Thread thread = new Thread(numberPrinter);
            thread.start();
        }
    }
}
```

Note:  the code will not guarantee that the numbers are printed in order (1 to 10). 

## How to Ensure Ordered Printing?

To ensure numbers are printed in order, you can:

### 1. Use a Single Thread: Execute the tasks sequentially without creating separate threads.
```java
public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
        }
    }
}
```

### 2. Use Thread Synchronization: There are several ways to ensure threads print numbers in order using thread synchronization techniques. Below are the most common approaches:

#### a. Using synchronized and a Shared Lock

```java
class NumberPrinter implements Runnable {
    private static final Object lock = new Object();
    private static int currentNumber = 1;
    private final int number;

    public NumberPrinter(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (number != currentNumber) { 
                try {
                    lock.wait(); // Wait for the turn
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(number);
            currentNumber++; // Increment the shared variable
            lock.notifyAll(); // Notify other waiting threads
        }
    }
}

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new NumberPrinter(i));
            thread.start();
        }
    }
}
```
How It Works:
- A shared currentNumber variable ensures only the thread with the matching number proceeds.
- Threads wait (lock.wait()) until their number matches currentNumber.
- The lock ensures only one thread can access the critical section at a time.


#### b. Using ReentrantLock and a Condition

A ReentrantLock with a Condition object provides fine-grained control over thread synchronization.

```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class NumberPrinter implements Runnable {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static int currentNumber = 1;
    private final int number;

    public NumberPrinter(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            while (number != currentNumber) {
                condition.await(); // Wait for the turn
            }
            System.out.println(number);
            currentNumber++;
            condition.signalAll(); // Notify other waiting threads
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new NumberPrinter(i));
            thread.start();
        }
    }
}
```

How It Works:
- The ReentrantLock ensures only one thread enters the critical section.
- The Condition object (condition.await() and condition.signalAll()) is used to control thread execution order.


#### c. Using a Semaphore

A Semaphore can be used to control access to resources, allowing threads to proceed one at a time in order.

```java
import java.util.concurrent.Semaphore;

class NumberPrinter implements Runnable {
    private static final Semaphore[] semaphores = new Semaphore[10];
    private final int number;

    static {
        for (int i = 0; i < semaphores.length; i++) {
            semaphores[i] = new Semaphore(i == 0 ? 1 : 0); // First thread starts with a permit
        }
    }

    public NumberPrinter(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        try {
            semaphores[number - 1].acquire(); // Wait for the turn
            System.out.println(number);
            if (number < semaphores.length) {
                semaphores[number].release(); // Allow the next thread to proceed
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new NumberPrinter(i));
            thread.start();
        }
    }
}
```

How It Works:
- Each thread waits on its own Semaphore.
- Only the current thread has a permit to execute.
- After finishing, the thread releases the permit for the next thread.

#### d. Using ExecutorService with Single Thread Executor

Instead of managing threads manually, you can use an ExecutorService to queue tasks in order.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NumberPrinter implements Runnable {
    private final int number;

    public NumberPrinter(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println(number);
    }
}

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 10; i++) {
            executor.execute(new NumberPrinter(i)); // Tasks are queued and executed sequentially
        }

        executor.shutdown();
    }
}
```

How It Works:
- A single-threaded executor ensures tasks are executed one at a time, in the order they are submitted.


Recommendation:
- Use ExecutorService if sequential execution is sufficient and no custom threading logic is needed.
- Use synchronized or ReentrantLock for full control and if task order needs strict enforcement.



<br>

<br>

<br>

**Problem Statement 3**
* Square the elements of an array and retrieve them in the same order using threads


#### a. Thread Pool with Task Distribution:

```java
import java.util.concurrent.*;

public class ThreadPoolSquaring {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ExecutorService executor = Executors.newFixedThreadPool(4);
        int chunkSize = arr.length / executor.getMaximumPoolSize();

        for (int i = 0; i < executor.getMaximumPoolSize(); i++) {
            int start = i * chunkSize;
            int end = (i + 1) * chunkSize;
            if (i == executor.getMaximumPoolSize() - 1) {
                end = arr.length;
            }
            executor.submit(() -> {
                for (int j = start; j < end; j++) {
                    arr[j] *= arr[j];
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the squared array
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
```

#### b. Thread-Per-Element Approach:

```java
public class ThreadPerElementSquaring {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Thread[] threads = new Thread[arr.length];
        for (int i = 0; i < arr.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                arr[index] *= arr[index];
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the squared array
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
```


#### c. Producer-Consumer Pattern:

```java
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerSquaring {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        BlockingQueue<Integer> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> outputQueue = new LinkedBlockingQueue<>();

        // Producer thread
        new Thread(() -> {
            for (int num : arr) {
                try {
                    inputQueue.put(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Consumer threads
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        int num = inputQueue.take();
                        outputQueue.put(num * num);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }).start();
        }

        // Consumer thread to collect results
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    arr[i++] = outputQueue.take();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();

        // Wait for all threads to finish
        // ... (Implement a mechanism to wait for all threads to finish)
    }
}
```



#### d. Using Future and Callable which works asynchronously but then here we are retrieving the results in order as the get() in futures is a blocking method:

```java
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class FutureCallableOrdered {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Integer>> futures = new ArrayList<>();

        // Submit tasks and store futures in order
        for (int i = 0; i < arr.length; i++) {
            int finalI = i;
            futures.add(executor.submit(() -> arr[finalI] * arr[finalI]));
        }

        executor.shutdown();

        // Retrieve results in order and assign to the array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = futures.get(i).get();
        }

        // Print the squared array
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
```





## Executor

The `Executor` interface is used to execute tasks. It is a generic interface that can be used to execute any kind of task. The `Executor` interface has only one method:

```java
public interface Executor {
    void execute(Runnable command);
}
```

The `execute` method takes a `Runnable` object as a parameter. The `Runnable` interface is a functional interface that has only one method. Executors internally use a thread pool to execute the tasks. The `execute` method is non-blocking. It returns immediately after submitting the task to the thread pool. The `execute` method is used to execute tasks that do not return a result. 

A thread pool is a collection of threads that are used to execute tasks.
Instead of creating a new thread for each task, a thread pool reuses the existing threads to execute the tasks. This improves the performance of the application.

The `Executor` interface has a method called `newCachedThreadPool` that returns an `ExecutorService` object. The `ExecutorService` interface extends the `Executor` interface. The `ExecutorService` interface has methods to execute tasks that return a result. The `ExecutorService` interface also has methods to shutdown the thread pool. 

To run a task using the `Executor` interface, we can use the `newCachedThreadPool` method to create an `ExecutorService` object. The `newCachedThreadPool` method returns an `ExecutorService` object that uses a thread pool with a variable number of threads. The `newCachedThreadPool` method creates a new thread for each task if there are no idle threads in the thread pool. If there is an idle thread in the thread pool, the `newCachedThreadPool` method reuses the idle thread to execute the task. The `newCachedThreadPool` method returns an `ExecutorService` object that uses a thread pool with a variable number of threads. 

```java
Executor executorService = Executors.newCachedThreadPool();
executorService.execute(() -> System.out.println("Hello World"));
```


## Executor vs ExecutorService

The Executor and ExecutorService are key interfaces in Java's java.util.concurrent package that help manage and control threads. Here's an explanation of their purpose and differences, with examples:

## Executor

#### Definition:
The Executor interface provides a simple way to decouple task submission from the mechanics of how each task will be run.
It is a functional interface with a single method:

```java
void execute(Runnable command);
```

Use Case:
- Best suited for cases where you simply want to submit tasks for asynchronous execution.
- It doesn’t provide advanced lifecycle management or scheduling features.


```java
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> System.out.println("Task executed by " + Thread.currentThread().getName());
        executor.execute(task);
    }
}
```

## ExecutorService

#### Definition:

- ExecutorService extends the Executor interface and adds methods for managing the lifecycle and controlling tasks.
- It provides more advanced functionalities such as task submission, result retrieval, and thread pool management.
- Important methods include:
  
```java
void shutdown();
<T> Future<T> submit(Callable<T> task);
List<Runnable> shutdownNow();
boolean isTerminated();
```

Use Case:
- Use ExecutorService when you need lifecycle management, thread pooling, or the ability to retrieve task results.


```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                System.out.println("Executing Task " + taskId + " on " + Thread.currentThread().getName());
            });
        }

        executorService.shutdown(); // Gracefully shuts down the executor
    }
}
```


## Callable and Future

Runnables do not return a result. If we want to execute a task that returns a result, we can use the `Callable` interface. The `Callable` interface is a functional interface that has only one method:

```java
public interface Callable<V> {
    V call() throws Exception;
}
```

The `call` method returns a result of type `V`. The `call` method can throw an exception. The `Callable` interface is used to execute tasks that return a result.
For instance we can use the `Callable` interface to execute a task that returns the sum of two numbers:

```java
Callable<Integer> sumTask = () -> 2 + 3;
```

In order to execute a task that returns a result, we can use the `submit` method of the `ExecutorService` interface. The `submit` method takes a `Callable` object as a parameter. The `submit` method returns a `Future` object. The `Future` interface has a method called `get` that returns the result of the task. The `get` method is a blocking method. It waits until the task is completed and then returns the result of the task. 

```java
ExecutorService executorService = Executors.newCachedThreadPool();
Future<Integer> future = executorService.submit(() -> 2 + 3);
Integer result = future.get();
```

OR

```java
ExecutorService executorService = Executors.newCachedThreadPool();
Future<Integer> future = executorService.submit(sumTask);
Integer result = future.get();
```

Futures can be used to cancel tasks. The `Future` interface has a method called `cancel` that can be used to cancel a task. The `cancel` method takes a boolean parameter. If the boolean parameter is `true`, the task is cancelled even if the task is already running. If the boolean parameter is `false`, the task is cancelled only if the task is not running. 

```java
ExecutorService executorService = Executors.newCachedThreadPool();
Future<Integer> future = executorService.submit(() -> 2 + 3);
future.cancel(false);
```

## Assignment
* Create a count class that has a count variable.
* Create two different classes `Adder` and `Subtractor`.
* Accept a count object in the constructor of both the classes.
* In `Adder`, iterate from 1 to 100 and increment the count variable by 1 on each iteration.
* In `Subtractor`, iterate from 1 to 100 and decrement the count variable by 1 on each iteration.
* Print the final value of the count variable.
* What would the ideal value of the count variable be?
* What is the actual value of the count variable?
* Try to add some delay in the `Adder` and `Subtractor` classes using inspiration from the code below. What is the value of the count variable now?

```java
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
```


##  Callable + Future  vs Completable Future
 
####  Callable + Future

Use this when:

- You need a simple, one-time background task that returns a result.
- You're okay with blocking the thread (e.g., using future.get()).
- You don't need to chain multiple tasks or handle results asynchronously.

Example use case:
Load data from a file or a database in the background.

Compute something and wait for the result before proceeding.

```
Callable<Integer> task = () -> heavyComputation();
Future<Integer> future = executor.submit(task);
Integer result = future.get(); // BLOCKS
```


#### CompletableFuture

Use this when:

- You want non-blocking, asynchronous programming.
- You need to chain multiple tasks (e.g., task1 → task2 → task3).
- You want better exception handling, timeouts, or parallel execution.

Example use case:
Make multiple API calls in parallel and process all responses together.

Create complex pipelines: fetch → transform → store → log.

```
CompletableFuture.supplyAsync(() -> fetchData())
    .thenApply(data -> transformData(data))
    .thenAccept(result -> storeResult(result))
    .exceptionally(ex -> { log(ex); return null; });
```

This executes without blocking the main thread and allows chaining and error handling.



Here’s an updated version of your CompletableFuture example that includes more intermediate tasks chained via .thenApply() and .thenCompose() to demonstrate transformation, validation, and enrichment steps in a web scraping pipeline:

```
import java.util.concurrent.CompletableFuture;

public class CompletableFutureWebScraperExample {
    public static void main(String[] args) {
        System.out.println("Main thread is starting the asynchronous web scraping task...");

        // Start the asynchronous task
        CompletableFuture<String> fetchData = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching data from the website...");
            try {
                Thread.sleep(2000); // Simulate network delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Website Data: {title: 'Welcome to Java!', content: 'Learn Java with examples.'}";
        });

        // Process fetched data: Step 1 - Convert to uppercase
        CompletableFuture<String> processData = fetchData.thenApply(data -> {
            System.out.println("Processing fetched data (toUpperCase)...");
            return data.toUpperCase();
        })

        // Step 2 - Append timestamp
        .thenApply(data -> {
            System.out.println("Appending timestamp...");
            return data + " | TIMESTAMP: " + System.currentTimeMillis();
        })

        // Step 3 - Simulate data validation
        .thenApply(data -> {
            System.out.println("Validating data...");
            if (!data.contains("JAVA")) {
                throw new RuntimeException("Validation failed: keyword 'JAVA' not found.");
            }
            return data;
        })

        // Step 4 - Enrich data
        .thenApply(data -> {
            System.out.println("Enriching data...");
            return data + " | STATUS: VALID";
        });

        // Final consumer to print the processed result
        processData.thenAccept(result -> {
            System.out.println("Processed Data: " + result);
        });

        System.out.println("Main thread continues to do other work...");

        // Wait for all tasks to complete
        processData.join();
        System.out.println("Program finished.");
    }
}
```


Goal / Scenario	Use
- Simple background task with result (sync/blocking)	Callable + Future
- Complex async flow with multiple steps	CompletableFuture
- Need chaining, combining results from tasks	CompletableFuture
- Just fire a task and wait for result later	Callable + Future
- Don't want to block on result	CompletableFuture



## Understanding volatile, synchronized, and Atomic Variables

📌 When to Use What?

| Situation                                            | Best Tool                                  | Reason                                  |
|------------------------------------------------------|--------------------------------------------|-----------------------------------------|
| Simple status flag across threads                    | volatile                                   | Provides visibility and lightweight     |
| Read-modify-write (e.g., count++)                    | synchronized / AtomicInteger               | volatile not enough — needs atomicity   |
| Complex block requiring mutual exclusion             | synchronized                               | Manages multiple statements atomically  |
| High-performance counter under contention            | AtomicInteger                              | Lock-free and thread-safe performance   |
| Wait/notify coordination                             | synchronized                               | Required by wait(), notify()            |



🔸 volatile
Guarantees:
Visibility: Changes made by one thread are immediately visible to others.

Prevents instruction reordering.

Lightweight — no locking overhead.

Limitations:
No atomicity for compound operations like x = x + 1.

Example:

```
volatile boolean running = true;

public void stop() {
    running = false;
}
```

🔸 synchronized
Guarantees:
Mutual exclusion (only one thread can execute the block at a time).

Visibility: Changes made within the block are visible to other threads once the lock is released.

Provides a happens-before relationship.

Limitations:
More expensive due to lock acquisition.

Risk of deadlocks if not managed carefully.

Example:
```
synchronized (lockObject) {
    // safely modify shared data
    counter++;
}
```

🔸 Atomic Variables (e.g., AtomicInteger, AtomicBoolean)
Guarantees:
Atomic operations using CAS (Compare-And-Swap) at the CPU level.

Visibility is ensured.

No locking needed = high performance.

Limitations:
Only works for single-variable operations.

Can't combine multiple variables atomically.

Example:
```
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet(); // thread-safe and atomic
```


Quick Decision

Are you modifying shared data?
   |
   +-- Only need visibility? --> Use volatile
   |
   +-- Need atomic read/write?
         |
         +-- Only 1 variable? --> Use AtomicXXX
         |
         +-- Multiple vars or complex logic? --> Use synchronized
         

## Reading List

* [Web Browser architecture](https://levelup.gitconnected.com/how-web-browsers-use-processes-and-threads-9f8f8fa23371)
