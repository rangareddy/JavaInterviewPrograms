package com.ranga.threads;

/**
 * Utility class to print numbers in order with one thread printing the odd numbers and another printing the even numbers.
 *
 * <p><b>Algorithm Strategy (Inter-thread Communication with wait/notify):</b>
 * <ol>
 *   <li>Both threads share a single {@code Printer} object, which acts as the monitor (lock) they coordinate on.</li>
 *   <li>The {@code Printer} keeps a {@code volatile boolean isOdd} flag that records whose turn it is:
 *       {@code false} means an odd number must be printed next, {@code true} means an even number.</li>
 *   <li>{@code printOdd} and {@code printEven} are {@code synchronized}, so only one thread runs inside them at a time.</li>
 *   <li>A thread that finds it is not its turn calls {@code wait()} inside a {@code while} loop (the loop protects
 *       against spurious wake-ups) and releases the lock.</li>
 *   <li>When it is its turn, the thread prints its number, flips the flag and calls {@code notify()} to wake the other thread.</li>
 *   <li>The odd thread prints {@code 1, 3, 5, ...} and the even thread prints {@code 2, 4, 6, ...} until {@code max}.
 *       Because the turns strictly alternate, the output is {@code 1 2 3 4 ...}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code max = 4}:
 * <ul>
 *   <li>Even thread starts first, finds it is not its turn and waits.</li>
 *   <li>Odd thread prints {@code 1}, flips the flag, notifies; its next call ({@code 3}) now waits.</li>
 *   <li>Even thread wakes, prints {@code 2}, flips the flag, notifies; and so on until {@code 1 2 3 4} is printed.</li>
 * </ul>
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

    public static void main(String[] args) throws InterruptedException {
        Printer printer = new Printer();
        int maxNum = 10;
        boolean isEvenNumber = true;
        Thread oddThread = new Thread(new EvenOrOdd(printer, maxNum, !isEvenNumber), "Odd");
        Thread evenThread = new Thread(new EvenOrOdd(printer, maxNum, isEvenNumber), "Even");

        System.out.println("Input  : max = " + maxNum);
        System.out.print("Output : ");
        oddThread.start();
        evenThread.start();

        // Wait for both threads so the output line is complete before the program ends
        oddThread.join();
        evenThread.join();
        System.out.println();
    }
}

/**
 * Task run by each thread: prints either the odd or the even numbers up to {@code max} through the shared {@link Printer}.
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

    @Override
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

/**
 * Shared monitor that makes the odd and even threads take turns.
 */
class Printer {

    // false -> odd number is printed next, true -> even number is printed next
    private volatile boolean isOdd;

    synchronized void printEven(int number) {
        while (!isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.print(number + " ");
        isOdd = false;
        notify();
    }

    synchronized void printOdd(int number) {
        while (isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.print(number + " ");
        isOdd = true;
        notify();
    }
}
