package com.ranga.basic;

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
