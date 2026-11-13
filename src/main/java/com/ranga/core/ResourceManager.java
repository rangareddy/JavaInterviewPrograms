package com.ranga.core;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implement a {@code ResourceManager} that implements {@code AutoCloseable}, collects several cleanup actions and closes all of them, in reverse order of registration, when it is used in a try-with-resources block. A failing cleanup must not stop the others, and no exception may be lost.
 *
 * <p><b>Algorithm Strategy (Reverse-Order Close with Suppressed Exceptions):</b>
 * <ol>
 *   <li>Keep the registered {@code Closer} actions on a stack ({@code ArrayDeque} with {@code push}), so the last resource opened is the first one closed, which is the order dependent resources need.</li>
 *   <li>{@code close()} pops and runs every closer, even when one throws. The first exception becomes the primary exception; every later one is attached to it with {@code addSuppressed}.</li>
 *   <li>After all closers have run, {@code close()} throws the primary exception, so the caller sees one exception that still carries all the others as {@code getSuppressed()}.</li>
 *   <li>In try-with-resources, if the body also throws, the body exception stays the primary one and the exception from {@code close()} is added to it as suppressed. Nothing is lost, which a hand-written {@code try/finally} cannot guarantee: an exception thrown in {@code finally} replaces the one from the body.</li>
 *   <li>The manager is idempotent (a second {@code close()} finds an empty stack and does nothing) and refuses new registrations after it was closed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Register {@code connection}, {@code statement} (its close throws "statement close failed") and {@code cursor} (throws "cursor close failed"), then throw "query failed" from the body.</li>
 *   <li>{@code close()} runs {@code cursor} first: its exception becomes the primary. {@code statement} fails next and is added as suppressed on it. {@code connection} closes normally.</li>
 *   <li>The caller catches "query failed"; its suppressed list holds "cursor close failed", which in turn has "statement close failed" suppressed.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(r)} — One call per registered resource, {@code r} being the number of resources.</li>
 *   <li><b>Space Complexity:</b> {@code O(r)} — The stack of registered closers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ResourceManager implements AutoCloseable {

    public static void main(String[] args) {
        System.out.println("Input  : 3 resources, two of them fail to close, and the body throws as well");
        try (ResourceManager manager = new ResourceManager()) {
            manager.register(() -> System.out.println("closed connection"));
            manager.register(() -> {
                throw new IllegalStateException("statement close failed");
            });
            manager.register(() -> {
                throw new IllegalArgumentException("cursor close failed");
            });
            throw new RuntimeException("query failed");
        } catch (Exception e) {
            System.out.println("Output : caught " + e.getMessage());
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("         suppressed " + suppressed.getMessage());
                for (Throwable nested : suppressed.getSuppressed()) {
                    System.out.println("           suppressed " + nested.getMessage());
                }
            }
        }
    }

    @FunctionalInterface
    public interface Closer {
        void close() throws Exception;
    }

    private final Deque<Closer> closers = new ArrayDeque<>();
    private boolean closed;

    public void register(Closer closer) {
        if (closed) {
            throw new IllegalStateException("ResourceManager is already closed");
        }
        closers.push(closer);
    }

    @Override
    public void close() throws Exception {
        closed = true;
        Exception primary = null;
        while (!closers.isEmpty()) {
            Closer closer = closers.pop();
            try {
                closer.close();
            } catch (Exception e) {
                if (primary == null) {
                    primary = e;
                } else {
                    primary.addSuppressed(e);
                }
            }
        }
        if (primary != null) {
            throw primary;
        }
    }
}
