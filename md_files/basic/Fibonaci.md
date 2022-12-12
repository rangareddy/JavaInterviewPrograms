# Fibonacci

**Definition:** 

The Fibonacci numbers is a series of numbers in which each number ( Fibonacci number ) is the sum of the two preceding numbers. \

Let the first two numbers in the series be taken as 0 and 1. By adding 0 and 1, we get the third number as 1. \
Then by adding the second and the third number (i.e) 1 and 1, we get the fourth number as 2, and similarly, the process goes on.

**Example:** 

`0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, .... ∞`

**Formula:**

The sequence of Fibonacci numbers can be defined as:

`Fn = Fn-1 + Fn-2`

Where Fn is the nth term or number

Fn-1 is the (n-1)th term
Fn-2 is the (n-2)th term

## Print the Fibonacci Numbers

**Fibonacci.java**

```java
public class Fibonacci {
    public static void main(String[] args) {
        int n = 10;
        printFibonacciNumber(n);
    }

    private static void printFibonacciNumber(int n) {
        int first = 0, second = 1;
        System.out.print(first + " " + second + " ");
        int third;
        for (int i = 2; i < n; i++) {
            third = first + second;
            System.out.print(third + " ");
            first = second;
            second = third;
        }
    }
}
```

## Print the Fibonacci Numbers using Dynamic Programming

```java
public class Fibonacci {
    public static void main(String[] args) {
        int n = 10;
        printFibonacciNumber(n);
    }

    private static void printFibonacciNumber(int n) {
        int[] fib_dp = new int[n];
        fib_dp[0] = 0;
        fib_dp[1] = 1;
        for (int i = 2; i < n; i++) {
            fib_dp[i] = fib_dp[i - 1] + fib_dp[i - 2];
        }
        for (int fib : fib_dp) {
            System.out.print(fib + " ");
        }
    }
}
```


