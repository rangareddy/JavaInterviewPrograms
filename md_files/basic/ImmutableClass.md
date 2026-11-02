# Immutable Class

**Definition:**
Immutable class are the classes, whose object is created once, we cannot modify its content, it means any modification on immutable object 
will result in another immutable object.
In Java, String class and all the wrapper classes (like Integer, Boolean, Byte, Short) are immutable. 

**Advantages:**
1. Thread-safe
2. Caching

**Dis-Advantages:**
1. Consumes more memory

## Notes While Creating Immutable class:
1. Declare the class as final so it can’t be extended.
2. Make all fields private so that direct access is not allowed.
3. Don’t provide setter methods for variables
4. Make all mutable fields final so that it’s value can be assigned only once.
5. Initialize all the fields via a constructor performing deep copy.
6. Perform cloning of objects in the getter methods to return a copy rather than returning the actual object reference.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Constructing and reading the object copies a constant number of fields.
- **Space Complexity:** `O(1)` — One extra `Date` copy per construction/read.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Mutable Class (What to Avoid)

A class with public setters or that leaks its mutable `Date` can be changed after construction, so it is not thread-safe and cannot be shared or cached safely.

```java
static class MutablePerson {
    private int id;
    private Date dob;
    MutablePerson(int id, Date dob) { this.id = id; this.dob = dob; }   // keeps the caller's reference
    public void setId(int id) { this.id = id; }
    public Date getDob() { return dob; }                                // leaks the internal reference
}
```

- **Time Complexity:** n/a
- **Space Complexity:** n/a

### Approach 2: Final Class with Defensive Copies

Make the class `final`, all fields `private final`, no setters, and copy mutable fields (`Date`) both in the constructor and in the getter. This is the version implemented in the repository.

```java
static final class ImmutablePerson {
    private final int id;
    private final String name;
    private final Date dob;

    ImmutablePerson(int id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = new Date(dob.getTime());      // defensive copy in
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Date getDob() { return new Date(dob.getTime()); }   // defensive copy out
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 3: Immutable Types Only (java.time) (Optimized)

Prefer fields whose type is already immutable (`String`, `LocalDate`, `Integer`...). Then no defensive copying is needed at all, and `java.util.Date` (itself mutable and legacy) disappears from the API.

```java
static final class Person {
    private final int id;
    private final String name;
    private final LocalDate dob;

    Person(int id, String name, LocalDate dob) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.dob = Objects.requireNonNull(dob);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDob() { return dob; }                  // safe: LocalDate is immutable

    // "Wither": a change produces a new object instead of modifying this one
    public Person withName(String newName) { return new Person(id, newName, dob); }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 4: Record (Java 16+)

A `record` is shallowly immutable by design and generates the constructor, accessors, `equals`, `hashCode` and `toString`. Use immutable component types (such as `LocalDate`) to make it deeply immutable.

```java
record PersonRecord(int id, String name, LocalDate dob) {
    PersonRecord {
        Objects.requireNonNull(name);
        Objects.requireNonNull(dob);
    }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Mutable Class (What to Avoid) | n/a | n/a |
| 2 | Final Class with Defensive Copies | O(1) | O(1) |
| 3 | Immutable Types Only (java.time) (Optimized) | O(1) | O(1) |
| 4 | Record (Java 16+) | O(1) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above. The version implemented in this repository is Approach 2.

## Source Code

### ImmutableClassDemo.java

```java
import java.io.Serializable;
import java.util.Date;

public class ImmutableClassDemo {

    public static void main(String[] args) {
        Date dob = new Date(0L);
        ImmutablePerson person = new ImmutablePerson(123, "Ranga", dob);
        System.out.println("Original : " + person);

        // Try to change the state through the getter's copy and through the caller's own reference
        person.getDob().setTime(System.currentTimeMillis());
        dob.setTime(System.currentTimeMillis());

        System.out.println("After modification attempts : " + person);
    }
}

final class ImmutablePerson implements Serializable {
    private final int id;
    private final String name;
    private final Date dob;

    public ImmutablePerson(int id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = new Date(dob.getTime()); // defensive copy
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return new Date(dob.getTime()); // return a copy, not the internal reference
    }

    @Override
    public String toString() {
        return "ImmutablePerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                '}';
    }
}
```
