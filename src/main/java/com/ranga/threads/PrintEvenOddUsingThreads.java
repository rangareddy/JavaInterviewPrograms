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

/**
 * Utility class to print numbers in order with one thread printing odd and another printing even numbers.
 *
 * <p><b>Algorithm Strategy (Inter-thread Communication with wait/notify):</b>
 * <ol>
 *   <li>Both threads share a single {@code Printer} whose methods are {@code synchronized}.</li>
 *   <li>A {@code volatile} flag records whose turn it is; a thread that finds it is not its turn calls {@code wait()}.</li>
 *   <li>After printing, a thread flips the flag and calls {@code notify()} to wake the other thread.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each number from 1 to {@code max} is printed exactly once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only the shared flag and the two threads.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
