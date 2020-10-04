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

## ImmutableClassDemo.java
```java
import java.io.Serializable;
import java.util.Date;

/**
 * User: Ranga Reddy
 * Date: 11/15/2019
 * Time: 12:12 PM
 * Description:
 */

final class ImmutablePerson implements Serializable {
    private final int id;
    private final String name;
    private final Date dob;

    public ImmutablePerson(int id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return (Date) dob.clone();
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

public class ImmutableClassDemo {
    public static void main(String[] args) {
        ImmutablePerson i = new ImmutablePerson(123, "Ranga", new Date(88, 6, 1));
        System.out.println(i);
        i.getDob().setTime(System.currentTimeMillis());
        System.out.println(i);
    }
}
```
