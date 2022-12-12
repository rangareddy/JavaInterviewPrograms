package com.ranga.threads;

/**
 * User: Ranga Reddy
 * Date: 11/15/2019
 * Time: 3:38 PM
 * Description:
 */

class EvenOrOdd implements Runnable {

    private final Printer printer;
    private final int max;
    private final boolean isEvenNumber;

    public EvenOrOdd(Printer printer, int max, boolean isEvenNumber) {
        this.printer = printer;
        this.max = max;
        this.isEvenNumber = isEvenNumber;
    }

    public void run() {
        int number = isEvenNumber ? 2 : 1;
        while (number <= max) {
            if (isEvenNumber) {
                printer.printEven(number);
            } else {
                printer.printOdd(number);
            }
            number += 2;
        }
    }
}

class Printer {

    private volatile boolean isOdd;

    synchronized void printEven(int number) {
        while (!isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        //System.out.println(Thread.currentThread().getName() + ":" + number);
        System.out.println(number);
        isOdd = false;
        notify();
    }

    synchronized void printOdd(int number) {
        while (isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        //System.out.println(Thread.currentThread().getName() + ":" + number);
        System.out.println(number);
        isOdd = true;
        notify();
    }
}

public class PrintEvenOddUsingThreads {
    public static void main(String[] args) {
        Printer printer = new Printer();
        int maxNum = 10;
        boolean isEvenNumber = true;
        Thread oddThread = new Thread(new EvenOrOdd(printer, maxNum, !isEvenNumber), "Odd");
        Thread evenThread = new Thread(new EvenOrOdd(printer, maxNum, isEvenNumber), "Even");
        oddThread.start();
        evenThread.start();
    }
}
