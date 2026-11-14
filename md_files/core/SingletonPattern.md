# Singleton Pattern

Demonstrate how to build a class that has exactly one instance for the whole application, and the common implementations: eager initialisation, double-checked locking, the initialisation-on-demand holder and an enum singleton.

## Algorithm Strategy (Private Constructor with a Controlled Access Point)

1. Make the constructor `private` so no other class can create instances, and expose the instance through a `static` method.
2. Eager: create the instance when the class is loaded (`static final`). Simple and thread-safe, but created even if never used.
3. Double-checked locking: a `volatile` field is checked once without a lock and again inside `synchronized`; `volatile` prevents another thread from seeing a half-constructed object.
4. Holder idiom: the instance lives in a nested class that is loaded (and initialised, thread-safely) only on first access. The enum singleton is the simplest and also survives serialization and reflection.

## Example walkthrough

- Two calls to `getInstance()` return the same object, so `==` is `true` for all four implementations.
- Eight threads asking for the lazy instance at the same time still end up with a single object.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Access to the instance is a field read.
- **Space Complexity:** `O(1)` — One instance.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Eager Initialisation

The instance is created when the class loads. Simple and thread-safe, but created even when unused.

```java
static final class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();
    private EagerSingleton() { }
    static EagerSingleton getInstance() { return INSTANCE; }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 2: Synchronized Method

Lazy creation guarded by a lock on every call. Correct but every access pays for synchronization.

```java
static final class SynchronizedSingleton {
    private static SynchronizedSingleton instance;
    private SynchronizedSingleton() { }
    static synchronized SynchronizedSingleton getInstance() {
        if (instance == null) instance = new SynchronizedSingleton();
        return instance;
    }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 3: Double-Checked Locking (Optimized)

A `volatile` field checked before and inside the lock, so the lock is used only during creation. This is one of the versions implemented in the repository.

```java
static final class DoubleCheckedSingleton {
    private static volatile DoubleCheckedSingleton instance;
    private DoubleCheckedSingleton() { }
    static DoubleCheckedSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedSingleton.class) {
                if (instance == null) instance = new DoubleCheckedSingleton();
            }
        }
        return instance;
    }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 4: Initialisation-on-Demand Holder

The JVM initialises the nested holder class lazily and thread-safely. No explicit locking is needed.

```java
static final class HolderSingleton {
    private HolderSingleton() { }
    private static class Holder { private static final HolderSingleton INSTANCE = new HolderSingleton(); }
    static HolderSingleton getInstance() { return Holder.INSTANCE; }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 5: Enum Singleton

The simplest form, which is also safe against serialization and reflection attacks (Joshua Bloch's recommendation).

```java
enum EnumSingleton { INSTANCE }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Eager Initialisation | O(1) | O(1) |
| 2 | Synchronized Method | O(1) | O(1) |
| 3 | Double-Checked Locking (Optimized) | O(1) | O(1) |
| 4 | Initialisation-on-Demand Holder | O(1) | O(1) |
| 5 | Enum Singleton | O(1) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### SingletonPattern.java

```java
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
```
