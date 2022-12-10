package com.ranga.dp;

public class Fibonacci {
    public static void main(String[] args) {
        int n = 20;
        printFibonacciNumber(n);
    }

    private static void printFibonacciNumber(int n) {
        int fib_dp[] = new int[n];
        fib_dp[0] = 0;
        fib_dp[1] = 1;
        for(int i=2; i<n; i++) {
            fib_dp[i] = fib_dp[i-1] + fib_dp[i-2];
        }
        for(int fib : fib_dp) {
            System.out.print(fib +" ");
        }
    }
}
