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




Type	          Checked?	    Typical Use
Error	            No	      JVM/system failures (should not handle)
Exception	        Yes	        Recoverable conditions (must handle or declare)
RuntimeException	No	        Programmer errors or bad logic (e.g., NPE)



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

Exception: Application-level recoverable conditions.

Error: Critical issues not intended to be caught (e.g., OutOfMemoryError).
