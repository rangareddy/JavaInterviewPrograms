package com.ranga.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Three threads must print the numbers from 1 to {@code n} in order, taking turns: thread 1 prints 1, thread 2 prints 2, thread 3 prints 3, thread 1 prints 4 and so on.
 *
 * <p><b>Algorithm Strategy (Turn Variable with wait / notifyAll):</b>
 * <ol>
 *   <li>Keep a shared counter {@code next} (the number to print next) and a shared lock object.</li>
 *   <li>Thread {@code t} (0, 1 or 2) is responsible for the numbers where {@code (number - 1) % 3 == t}.</li>
 *   <li>In a loop, a thread holds the lock and calls {@code wait()} while it is not its turn ({@code (next - 1) % 3 != t}) and the work is not finished.</li>
 *   <li>When it is its turn, it prints {@code next}, increments it and calls {@code notifyAll()} so the next thread can run. Stop when {@code next > n}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 7}: the threads print {@code T1:1, T2:2, T3:3, T1:4, T2:5, T3:6, T1:7}.</li>
 *   <li>No matter how the scheduler interleaves the threads, a thread waiting for its turn can never print out of order.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each number is printed once; each print wakes all waiting threads.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A counter and a lock.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PrintNumbersWithThreeThreads {

    private final int limit;
    private final int threadCount;
    private final List<String> printed = new ArrayList<>();
    private int next = 1;

    public PrintNumbersWithThreeThreads(int limit, int threadCount) {
        this.limit = limit;
        this.threadCount = threadCount;
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        PrintNumbersWithThreeThreads printer = new PrintNumbersWithThreeThreads(n, 3);
        System.out.println("Input  : n = " + n + ", threads = 3");
        System.out.println("Output : " + printer.run());
    }

    /** Runs the threads and returns the printed sequence such as {@code [T1:1, T2:2, T3:3]}. */
    public List<String> run() throws InterruptedException {
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            final int id = i;
            threads[i] = new Thread(() -> work(id), "T" + (id + 1));
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return printed;
    }

    private void work(int id) {
        synchronized (this) {
            while (next <= limit) {
                if ((next - 1) % threadCount == id) {
                    printed.add("T" + (id + 1) + ":" + next);
                    next++;
                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            notifyAll();
        }
    }
}
