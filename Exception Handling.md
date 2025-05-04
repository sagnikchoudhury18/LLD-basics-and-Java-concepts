```
java.lang.Object  
└── java.lang.Throwable  
    ├── java.lang.Error                   ← ❌ Not meant to be caught (Unrecoverable)
    │   ├── OutOfMemoryError
    │   ├── StackOverflowError
    │   ├── AssertionError
    │   └── VirtualMachineError
    │
    └── java.lang.Exception               ← ✅ Recoverable issues (Checked + Unchecked)
        ├── Checked Exceptions            ← ✅ Must handle or declare
        │   ├── IOException
        │   │   ├── FileNotFoundException
        │   │   ├── EOFException
        │   │   └── SocketException
        │   ├── SQLException
        │   ├── ParseException
        │   ├── InterruptedException
        │   ├── ClassNotFoundException
        │   ├── InvocationTargetException
        │   └── [YourCustomCheckedException]
        │
        └── Unchecked Exceptions          ← ❌ Runtime exceptions, optional to handle
            └── RuntimeException
                ├── ArithmeticException
                ├── ArrayIndexOutOfBoundsException
                ├── ClassCastException
                ├── IllegalArgumentException
                │   └── NumberFormatException
                ├── IllegalStateException
                ├── NullPointerException
                ├── IndexOutOfBoundsException
                └── UnsupportedOperationException
```


```
Type	          Checked?	    Typical Use
Error	            No	      JVM/system failures (should not handle)
Exception	        Yes	        Recoverable conditions (must handle or declare)
RuntimeException	No	        Programmer errors or bad logic (e.g., NPE)

```

 Tricky Interview Questions

Q1: Can a finally block suppress an exception?
Yes. If both catch and finally throw exceptions, the one in finally overrides the one in catch.

```
try {
    throw new RuntimeException("try");
} catch (Exception e) {
    throw new RuntimeException("catch");
} finally {
    throw new RuntimeException("finally"); // this one is thrown
}

```

Q2: What happens if you System.exit(0) in try?

The finally block won’t execute — JVM shuts down.


Q3. Why we use throws

We use the **throws** keyword in Java to declare that a method might throw one or more checked exceptions. This informs the caller of the method that they must handle or propagate those exceptions.

🔎 Why Use throws?
To delegate exception handling to the calling method.

Mandatory for checked exceptions if not caught with a try-catch block.

Keeps method implementation clean when exception logic is handled elsewhere.

```
public void readFile(String filename) throws IOException {
    FileReader fr = new FileReader(filename); // might throw IOException
}
```


Q4.  Error vs Exception vs Throwable

Throwable: Base class for all errors and exceptions.





Here are some tricky Java try-catch questions centered around the return statement. These test your understanding of control flow when return is used in try, catch, or finally blocks.



What will this code print?

```
public class Main {
    public static int test() {
        try {
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            return 3;
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
```

Explanation: Even though the try block returns 1, the finally block executes after the try but before the method returns, and its return overrides the one from try.



What will this code print?

```
public class Main {
    public static int test() {
        int x = 0;
        try {
            x = 1;
            return x;
        } finally {
            x = 3;
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
```

Explanation: The value of x is copied when return x is encountered, so changing x in finally doesn’t affect the returned value.



What will this code print?

```
public class Main {
    public static String test() {
        try {
            throw new RuntimeException("error");
        } catch (Exception e) {
            return "catch";
        } finally {
            System.out.println("finally");
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
```

Answer:

finally  
catch


Explanation: Even though there's a return in catch, the finally block executes before the return completes.


 What will this code print?

```
public class Main {
    public static int test() {
        try {
            return 1 / 0;
        } catch (ArithmeticException e) {
            return 2;
        } finally {
            System.out.println("In finally");
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
```

Answer:

In finally  
2

Explanation: The try causes a divide-by-zero exception. Control goes to catch, which returns 2, but not before finally executes.

 

What will this code print?

```
public class Main {
    public static int test() {
        try {
            return 10;
        } finally {
            throw new RuntimeException("Oops!");
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
```

Answer:
Exception in thread "main" java.lang.RuntimeException: Oops!
Explanation: finally throws an exception, so it overrides the return from try.



Error: Critical issues not intended to be caught (e.g., OutOfMemoryError).
