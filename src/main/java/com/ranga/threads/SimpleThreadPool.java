package com.ranga.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implement a simple thread pool: a fixed number of worker threads that pull tasks from a shared queue, run them, and stop cleanly after {@code shutdown()} once every queued task has been executed.
 *
 * <p><b>Algorithm Strategy (Fixed Workers and a Task Queue with Poison Pills):</b>
 * <ol>
 *   <li>Start {@code n} worker threads in the constructor. Each worker loops on {@code tasks.take()}, which blocks while the queue is empty, and runs the task it receives.</li>
 *   <li>{@code execute(task)} adds the task to an unbounded {@code LinkedBlockingQueue}, so submitters never block and the same few threads are reused instead of creating one thread per task.</li>
 *   <li>{@code shutdown()} marks the pool as closed and adds one special "poison pill" task per worker. Because the queue is FIFO, a worker only sees a pill after all earlier tasks were taken, so queued work is finished first.</li>
 *   <li>A worker that takes a pill returns and its thread ends. {@code execute} and {@code shutdown} are {@code synchronized}, so no task can slip in behind the pills and stay in the queue forever.</li>
 *   <li>A task that throws a {@code RuntimeException} is caught so that the worker survives and keeps serving the queue. {@code awaitTermination()} joins all workers.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Pool of 3 workers and ten tasks adding {@code 1..10} to a shared counter: the ten tasks are queued, the three workers take them in turns.</li>
 *   <li>{@code shutdown()} queues three pills behind the tasks. Each worker finishes its current task, takes tasks until it meets a pill and exits.</li>
 *   <li>{@code awaitTermination()} returns once all workers ended; the counter is {@code 55}. Calling {@code execute} afterwards throws {@code IllegalStateException}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Per {@code execute}; a task starts as soon as a worker is free.</li>
 *   <li><b>Space Complexity:</b> {@code O(n + q)} — {@code n} worker threads plus {@code q} queued tasks.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SimpleThreadPool {

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool pool = new SimpleThreadPool(3);
        AtomicInteger sum = new AtomicInteger();
        for (int i = 1; i <= 10; i++) {
            final int value = i;
            pool.execute(() -> sum.addAndGet(value));
        }
        pool.shutdown();
        pool.awaitTermination();

        System.out.println("Input  : 3 workers, 10 tasks that add 1..10 to a counter");
        System.out.println("Output : sum = " + sum.get());
        try {
            pool.execute(() -> { });
        } catch (IllegalStateException e) {
            System.out.println("After shutdown : " + e.getMessage());
        }
    }

    private static final Runnable POISON_PILL = () -> { };

    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final List<Thread> workers = new ArrayList<>();
    private boolean shutdown;

    public SimpleThreadPool(int threads) {
        if (threads <= 0) {
            throw new IllegalArgumentException("threads must be positive");
        }
        for (int i = 0; i < threads; i++) {
            Thread worker = new Thread(this::runWorker, "pool-worker-" + i);
            workers.add(worker);
            worker.start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (shutdown) {
            throw new IllegalStateException("Pool is shut down");
        }
        tasks.add(task);
    }

    public synchronized void shutdown() {
        if (shutdown) {
            return;
        }
        shutdown = true;
        for (int i = 0; i < workers.size(); i++) {
            tasks.add(POISON_PILL);
        }
    }

    public void awaitTermination() throws InterruptedException {
        for (Thread worker : workers) {
            worker.join();
        }
    }

    private void runWorker() {
        try {
            while (true) {
                Runnable task = tasks.take();
                if (task == POISON_PILL) {
                    return;
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.err.println(Thread.currentThread().getName() + " task failed: " + e);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
