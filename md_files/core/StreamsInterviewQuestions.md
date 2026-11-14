# Java 8 Streams Interview Questions

Solve the classic Java 8 stream questions on a list of employees: the second highest salary, the average salary per department, the highest paid employee of each department, the names grouped by department, and the first non-repeating character of a string.

## Algorithm Strategy (Collectors: groupingBy, averagingInt, maxBy and sorted/skip)

1. Second highest salary: take the distinct salaries, sort them in descending order, skip the first one and take the next (`distinct().sorted(reverseOrder()).skip(1).findFirst()`).
2. Average salary per department: `groupingBy(department, averagingInt(salary))`.
3. Highest paid employee per department: `groupingBy(department, maxBy(comparingInt(salary)))`, unwrapping the `Optional`.
4. Names per department: `groupingBy(department, mapping(name, toList()))`. First non-repeating character: collect the counts into a `LinkedHashMap` and take the first entry with count 1.

## Example walkthrough

- For the sample employees the second highest salary is `90000` (the highest is `100000`).
- Grouping by department gives for example `Engineering -> [Asha, Ravi, Meena]` and the average of the Engineering salaries is `88333.33`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting the distinct salaries dominates; the groupings are O(n).
- **Space Complexity:** `O(n)` — The grouped collections.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Imperative Loops

The pre-Java 8 style: explicit loops and maps. Longer, but easy to step through.

```java
public static OptionalInt secondHighestSalaryLoop(List<com.ranga.core.StreamsInterviewQuestions.Employee> employees) {
    Set<Integer> distinct = new TreeSet<>(Comparator.reverseOrder());
    for (com.ranga.core.StreamsInterviewQuestions.Employee e : employees) distinct.add(e.salary);
    Iterator<Integer> it = distinct.iterator();
    if (!it.hasNext()) return OptionalInt.empty();
    it.next();
    return it.hasNext() ? OptionalInt.of(it.next()) : OptionalInt.empty();
}

public static Map<String, Double> averageSalaryByDepartmentLoop(List<com.ranga.core.StreamsInterviewQuestions.Employee> employees) {
    Map<String, long[]> sums = new TreeMap<>();
    for (com.ranga.core.StreamsInterviewQuestions.Employee e : employees) {
        long[] sum = sums.computeIfAbsent(e.department, k -> new long[2]);
        sum[0] += e.salary;
        sum[1]++;
    }
    Map<String, Double> result = new TreeMap<>();
    for (Map.Entry<String, long[]> entry : sums.entrySet()) result.put(entry.getKey(), (double) entry.getValue()[0] / entry.getValue()[1]);
    return result;
}

public static Map<String, List<String>> namesByDepartmentLoop(List<com.ranga.core.StreamsInterviewQuestions.Employee> employees) {
    Map<String, List<String>> result = new TreeMap<>();
    for (com.ranga.core.StreamsInterviewQuestions.Employee e : employees)
        result.computeIfAbsent(e.department, k -> new ArrayList<>()).add(e.name);
    return result;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Sort the Whole List

Sort the employees by salary and walk from the top; simple for the "second highest" question.

```java
public static OptionalInt secondHighestSalarySort(List<com.ranga.core.StreamsInterviewQuestions.Employee> employees) {
    List<com.ranga.core.StreamsInterviewQuestions.Employee> sorted = new ArrayList<>(employees);
    sorted.sort((a, b) -> Integer.compare(b.salary, a.salary));
    for (int i = 1; i < sorted.size(); i++)
        if (sorted.get(i).salary != sorted.get(0).salary) return OptionalInt.of(sorted.get(i).salary);
    return OptionalInt.empty();
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Streams with Collectors (Optimized)

Declarative pipelines with `groupingBy`, `averagingInt`, `maxBy`, `mapping` and `sorted/skip`. This is the version implemented in the repository.

```java
public static OptionalInt secondHighestSalaryStream(List<com.ranga.core.StreamsInterviewQuestions.Employee> employees) {
    return employees.stream().mapToInt(e -> e.salary).boxed().distinct()
            .sorted(Comparator.reverseOrder()).skip(1).mapToInt(Integer::intValue).findFirst();
}

public static Map<String, Double> averageSalaryByDepartmentStream(List<com.ranga.core.StreamsInterviewQuestions.Employee> employees) {
    return employees.stream().collect(Collectors.groupingBy(e -> e.department, TreeMap::new, Collectors.averagingInt(e -> e.salary)));
}

public static Map<String, List<String>> namesByDepartmentStream(List<com.ranga.core.StreamsInterviewQuestions.Employee> employees) {
    return employees.stream().collect(Collectors.groupingBy(e -> e.department, TreeMap::new, Collectors.mapping(e -> e.name, Collectors.toList())));
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Imperative Loops | O(n log n) | O(n) |
| 2 | Sort the Whole List | O(n log n) | O(n) |
| 3 | Streams with Collectors (Optimized) | O(n log n) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### StreamsInterviewQuestions.java

```java
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamsInterviewQuestions {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Asha", "Engineering", 100000),
                new Employee("Ravi", "Engineering", 90000),
                new Employee("Meena", "Engineering", 75000),
                new Employee("John", "Sales", 60000),
                new Employee("Priya", "Sales", 65000),
                new Employee("Kiran", "HR", 55000));

        System.out.println("Input  : employees = " + employees);
        System.out.println("Output : second highest salary = " + secondHighestSalary(employees).orElse(-1));
        System.out.println("Output : average salary by department = " + averageSalaryByDepartment(employees));
        System.out.println("Output : highest paid by department = " + highestPaidByDepartment(employees));
        System.out.println("Output : names by department = " + namesByDepartment(employees));
        System.out.println();

        System.out.println("Input  : text = \"swiss\"");
        System.out.println("Output : first non-repeating character = " + firstNonRepeating("swiss").orElse('?'));
    }

    public static OptionalInt secondHighestSalary(List<Employee> employees) {
        return employees.stream()
                .mapToInt(e -> e.salary)
                .boxed()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .mapToInt(Integer::intValue)
                .findFirst();
    }

    public static Map<String, Double> averageSalaryByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(e -> e.department, TreeMap::new, Collectors.averagingInt(e -> e.salary)));
    }

    public static Map<String, String> highestPaidByDepartment(List<Employee> employees) {
        Map<String, String> result = new TreeMap<>();
        employees.stream()
                .collect(Collectors.groupingBy(e -> e.department, Collectors.maxBy(Comparator.comparingInt(e -> e.salary))))
                .forEach((department, employee) -> result.put(department, employee.get().name));
        return result;
    }

    public static Map<String, List<String>> namesByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(e -> e.department, TreeMap::new, Collectors.mapping(e -> e.name, Collectors.toList())));
    }

    public static Optional<Character> firstNonRepeating(String text) {
        return text.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    /** A small immutable employee used by the examples. */
    public static final class Employee {
        public final String name;
        public final String department;
        public final int salary;

        public Employee(String name, String department, int salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return name + "(" + department + ", " + salary + ")";
        }
    }
}
```
