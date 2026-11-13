package com.ranga.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Two classic thread coordination puzzles. Print in Order: three threads call {@code first}, {@code second} and {@code third} in any start order, yet the output must be {@code firstsecondthird}. FooBar: one thread prints {@code foo} and another prints {@code bar}, {@code n} times each, and the output must alternate as {@code foobarfoobar...}.
 *
 * <p><b>Algorithm Strategy (Semaphores as Turn Tokens):</b>
 * <ol>
 *   <li>A semaphore with zero permits is a gate: the thread that must wait calls {@code acquire()}, and the thread that goes before it calls {@code release()} when it is done.</li>
 *   <li>Print in Order: {@code second} acquires {@code afterFirst}, which {@code first} releases after printing; {@code third} acquires {@code afterSecond}, which {@code second} releases. Whatever order the threads are started in, the output order is forced by the permits.</li>
 *   <li>FooBar: two semaphores pass a single turn back and forth. {@code foo} starts with the permit on {@code fooTurn}; each loop iteration acquires {@code fooTurn}, prints {@code foo} and releases {@code barTurn}. {@code bar} does the mirror image.</li>
 *   <li>No flag is polled and no thread burns CPU while it waits; a permit also cannot be lost, because a release before the acquire is remembered by the semaphore (unlike a {@code notify} sent before the {@code wait}).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Print in Order started as {@code third}, {@code first}, {@code second}: {@code third} blocks on {@code afterSecond}, {@code first} prints and releases {@code afterFirst}, {@code second} wakes, prints and releases {@code afterSecond}, {@code third} wakes and prints. Output: {@code firstsecondthird}.</li>
 *   <li>FooBar with {@code n = 2}: {@code foo} prints (turn passes to bar), {@code bar} prints (turn passes to foo), then the same again. Output: {@code foobarfoobar}, whichever thread is scheduled first.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each puzzle performs {@code n} handovers (three for Print in Order).</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few semaphores.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ConcurrencyCoordinationPuzzles {

    public static void main(String[] args) throws InterruptedException {
        int[][] orders = {{1, 2, 3}, {3, 2, 1}, {2, 3, 1}};
        for (int[] order : orders) {
            System.out.println("Input  : threads started in the order " + Arrays.toString(order));
            System.out.println("Output : " + printInOrder(order));
            System.out.println();
        }
        System.out.println("Input  : FooBar, n = 3");
        System.out.println("Output : " + fooBar(3));
    }

    static final class Ordered {
        private final Semaphore afterFirst = new Semaphore(0);
        private final Semaphore afterSecond = new Semaphore(0);

        void first(Runnable print) {
            print.run();
            afterFirst.release();
        }

        void second(Runnable print) throws InterruptedException {
            afterFirst.acquire();
            print.run();
            afterSecond.release();
        }

        void third(Runnable print) throws InterruptedException {
            afterSecond.acquire();
            print.run();
        }
    }

    static final class FooBar {
        private final int n;
        private final Semaphore fooTurn = new Semaphore(1);
        private final Semaphore barTurn = new Semaphore(0);

        FooBar(int n) {
            this.n = n;
        }

        void foo(Runnable print) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                fooTurn.acquire();
                print.run();
                barTurn.release();
            }
        }

        void bar(Runnable print) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                barTurn.acquire();
                print.run();
                fooTurn.release();
            }
        }
    }

    public static String printInOrder(int[] startOrder) throws InterruptedException {
        Ordered ordered = new Ordered();
        StringBuffer out = new StringBuffer();
        List<Thread> threads = new ArrayList<>();
        for (int id : startOrder) {
            threads.add(new Thread(() -> {
                try {
                    if (id == 1) {
                        ordered.first(() -> out.append("first"));
                    } else if (id == 2) {
                        ordered.second(() -> out.append("second"));
                    } else {
                        ordered.third(() -> out.append("third"));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        return out.toString();
    }

    public static String fooBar(int n) throws InterruptedException {
        FooBar fooBar = new FooBar(n);
        StringBuffer out = new StringBuffer();
        Thread foo = new Thread(() -> {
            try {
                fooBar.foo(() -> out.append("foo"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread bar = new Thread(() -> {
            try {
                fooBar.bar(() -> out.append("bar"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        bar.start();
        foo.start();
        foo.join();
        bar.join();
        return out.toString();
    }
}
