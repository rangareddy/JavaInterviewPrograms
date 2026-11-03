package com.ranga.basic;

import java.io.Serializable;
import java.util.Date;

/**
 * Demonstrates how to create an immutable class in Java.
 *
 * <p>An immutable object cannot be modified after it is created; any "modification" produces a new object.
 * {@code String} and the wrapper classes ({@code Integer}, {@code Boolean}, ...) are immutable.
 * Advantages: thread-safe without synchronization and safely cacheable. Disadvantage: more objects are created.
 *
 * <p><b>Algorithm Strategy (Steps to Make a Class Immutable):</b>
 * <ol>
 *   <li>Declare the class {@code final} so it cannot be extended (a subclass could otherwise add mutable state).</li>
 *   <li>Make all fields {@code private} and {@code final} so they are assigned exactly once.</li>
 *   <li>Do not provide setter methods.</li>
 *   <li>Initialize all fields in the constructor; for mutable fields (such as {@link Date}) store a defensive
 *       copy, so later changes to the caller's object do not affect this one.</li>
 *   <li>In getters of mutable fields return a copy, never the internal reference.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Creating {@code ImmutablePerson(123, "Ranga", dob)} stores a copy of {@code dob}.</li>
 *   <li>Calling {@code person.getDob().setTime(...)} changes only the copy returned by the getter, and {@code dob.setTime(...)} changes only the caller's object, so printing the person afterwards shows the original date.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Constructing and reading the object copies a constant number of fields.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — One extra {@code Date} copy per construction/read.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
