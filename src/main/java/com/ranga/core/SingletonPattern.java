package com.ranga.core;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demonstrate how to build a class that has exactly one instance for the whole application, and the common implementations: eager initialisation, double-checked locking, the initialisation-on-demand holder and an enum singleton.
 *
 * <p><b>Algorithm Strategy (Private Constructor with a Controlled Access Point):</b>
 * <ol>
 *   <li>Make the constructor {@code private} so no other class can create instances, and expose the instance through a {@code static} method.</li>
 *   <li>Eager: create the instance when the class is loaded ({@code static final}). Simple and thread-safe, but created even if never used.</li>
 *   <li>Double-checked locking: a {@code volatile} field is checked once without a lock and again inside {@code synchronized}; {@code volatile} prevents another thread from seeing a half-constructed object.</li>
 *   <li>Holder idiom: the instance lives in a nested class that is loaded (and initialised, thread-safely) only on first access. The enum singleton is the simplest and also survives serialization and reflection.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Two calls to {@code getInstance()} return the same object, so {@code ==} is {@code true} for all four implementations.</li>
 *   <li>Eight threads asking for the lazy instance at the same time still end up with a single object.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Access to the instance is a field read.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — One instance.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SingletonPattern {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Input  : getInstance() called twice for each implementation");
        System.out.println("Output : eager = " + (EagerSingleton.getInstance() == EagerSingleton.getInstance())
                + ", double-checked = " + (DoubleCheckedSingleton.getInstance() == DoubleCheckedSingleton.getInstance())
                + ", holder = " + (HolderSingleton.getInstance() == HolderSingleton.getInstance())
                + ", enum = " + (EnumSingleton.INSTANCE == EnumSingleton.valueOf("INSTANCE")));
        System.out.println();

        Set<DoubleCheckedSingleton> instances = ConcurrentHashMap.newKeySet();
        Thread[] threads = new Thread[8];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> instances.add(DoubleCheckedSingleton.getInstance()));
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Input  : 8 threads call DoubleCheckedSingleton.getInstance()");
        System.out.println("Output : distinct instances = " + instances.size());
    }

    /** Eager initialisation: created when the class is loaded. */
    static final class EagerSingleton {
        private static final EagerSingleton INSTANCE = new EagerSingleton();

        private EagerSingleton() {
        }

        static EagerSingleton getInstance() {
            return INSTANCE;
        }
    }

    /** Lazy initialisation with double-checked locking. */
    static final class DoubleCheckedSingleton {
        private static volatile DoubleCheckedSingleton instance;

        private DoubleCheckedSingleton() {
        }

        static DoubleCheckedSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckedSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleCheckedSingleton();
                    }
                }
            }
            return instance;
        }
    }

    /** Initialisation-on-demand holder: the JVM loads Holder (and creates the instance) on first use. */
    static final class HolderSingleton {
        private HolderSingleton() {
        }

        private static class Holder {
            private static final HolderSingleton INSTANCE = new HolderSingleton();
        }

        static HolderSingleton getInstance() {
            return Holder.INSTANCE;
        }
    }

    /** Enum singleton: thread-safe, serialization-safe and reflection-safe. */
    enum EnumSingleton {
        INSTANCE
    }
}
