# Java 8 Streams Interview Questions

Solve the classic Java 8 stream questions on a list of employees: the second highest salary, the average salary per department, the highest paid employee of each department, the names grouped by department, and the first non-repeating character of a string.

## Algorithm Strategy (Collectors: groupingBy, averagingInt, maxBy and sorted/skip)

1. Safeguard first: a `null` list, a `null` employee or an employee without a name or department is skipped by one shared helper, `validEmployees`, so no pipeline can fail with a `NullPointerException`.
2. Second highest salary: take the distinct salaries, sort them in descending order, skip the first one and take the next (`distinct().sorted(reverseOrder()).skip(1).findFirst()`).
3. Average salary per department: `groupingBy(department, averagingInt(salary))`.
4. Highest paid employee per department: `groupingBy(department, maxBy(comparingInt(salary)))`, with the `Optional` of each group resolved inside the collector (`collectingAndThen`).
5. Names per department: `groupingBy(department, mapping(name, toList()))`. First non-repeating character: collect the counts into a `LinkedHashMap` and take the first entry with count 1.

## Return convention and safeguards

Every method returns a plain value and none returns an `Optional`, so the five questions behave the same way:

| Question | Returns | When there is no answer or the input is unusable |
|----------|---------|--------------------------------------------------|
| Second highest salary | `Integer` | `null` (fewer than two distinct salaries, or `null` / empty list) |
| Average salary per department | `Map<String, Double>` | empty map `{}` |
| Highest paid employee per department | `Map<String, String>` | empty map `{}` |
| Names per department | `Map<String, List<String>>` | empty map `{}` |
| First non-repeating character | `Character` | `null` (`null`, empty or all-repeating text) |

The streams use `Optional` internally (`findFirst`, `maxBy`) and resolve it before returning (`orElse(null)`, `collectingAndThen`). The safeguards are the same in `main()`, the class and the optimized approach: `null` and empty lists return the empty result, and a `null` employee or an employee with a `null` name or department is skipped instead of causing a `NullPointerException` in `groupingBy`.

## Example walkthrough

- For the sample employees the second highest salary is `90000` (the highest is `100000`).
- Grouping by department gives for example `Engineering -> [Asha, Ravi, Meena]` and the average of the Engineering salaries is `88333.33`.
- For an empty or `null` list the second highest salary is `null` and every grouping is `{}`; for `"swiss"` the first non-repeating character is `'w'`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting the distinct salaries dominates; the groupings are O(n).
- **Space Complexity:** `O(n)` — The grouped collections.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice. All approaches follow the same return convention and safeguards, and the snippets assume `import com.ranga.core.StreamsInterviewQuestions.Employee;` plus the usual `java.util` imports.

### Approach 1: Imperative Loops

The pre-Java 8 style: explicit loops and maps. Longer, but easy to step through.

```java
public static Integer secondHighestSalaryLoop(List<Employee> employees) {
    if (employees == null) return null;
    Set<Integer> distinct = new TreeSet<>(Comparator.reverseOrder());
    for (Employee e : employees) {
        if (e == null || e.name == null || e.department == null) continue;
        distinct.add(e.salary);
    }
    Iterator<Integer> it = distinct.iterator();
    if (!it.hasNext()) return null;
    it.next();
    return it.hasNext() ? it.next() : null;
}

public static Map<String, Double> averageSalaryByDepartmentLoop(List<Employee> employees) {
    Map<String, long[]> sums = new TreeMap<>();
    if (employees != null) {
        for (Employee e : employees) {
            if (e == null || e.name == null || e.department == null) continue;
            long[] sum = sums.computeIfAbsent(e.department, k -> new long[2]);
            sum[0] += e.salary;
            sum[1]++;
        }
    }
    Map<String, Double> result = new TreeMap<>();
    for (Map.Entry<String, long[]> entry : sums.entrySet()) result.put(entry.getKey(), (double) entry.getValue()[0] / entry.getValue()[1]);
    return result;
}

public static Map<String, String> highestPaidByDepartmentLoop(List<Employee> employees) {
    Map<String, Employee> best = new TreeMap<>();
    if (employees != null) {
        for (Employee e : employees) {
            if (e == null || e.name == null || e.department == null) continue;
            Employee current = best.get(e.department);
            if (current == null || e.salary > current.salary) best.put(e.department, e);
        }
    }
    Map<String, String> result = new TreeMap<>();
    for (Map.Entry<String, Employee> entry : best.entrySet()) result.put(entry.getKey(), entry.getValue().name);
    return result;
}

public static Map<String, List<String>> namesByDepartmentLoop(List<Employee> employees) {
    Map<String, List<String>> result = new TreeMap<>();
    if (employees == null) return result;
    for (Employee e : employees) {
        if (e == null || e.name == null || e.department == null) continue;
        result.computeIfAbsent(e.department, k -> new ArrayList<>()).add(e.name);
    }
    return result;
}

public static Character firstNonRepeatingLoop(String text) {
    if (text == null || text.isEmpty()) return null;
    Map<Character, Integer> counts = new LinkedHashMap<>();
    for (char c : text.toCharArray()) counts.merge(c, 1, Integer::sum);
    for (Map.Entry<Character, Integer> entry : counts.entrySet()) if (entry.getValue() == 1) return entry.getKey();
    return null;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Sort the Whole List

Sort the employees by salary and walk from the top; simple for the "second highest" question.

```java
public static Integer secondHighestSalarySort(List<Employee> employees) {
    if (employees == null) return null;
    List<Employee> sorted = new ArrayList<>();
    for (Employee e : employees) if (e != null && e.name != null && e.department != null) sorted.add(e);
    if (sorted.isEmpty()) return null;
    sorted.sort((a, b) -> Integer.compare(b.salary, a.salary));
    for (int i = 1; i < sorted.size(); i++)
        if (sorted.get(i).salary != sorted.get(0).salary) return sorted.get(i).salary;
    return null;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Streams with Collectors (Optimized)

Declarative pipelines with `groupingBy`, `averagingInt`, `maxBy`, `mapping` and `sorted/skip`, all built on one safeguard helper. This is the version implemented in the repository.

```java
private static Stream<Employee> validEmployees(List<Employee> employees) {
    if (employees == null || employees.isEmpty()) return Stream.empty();
    return employees.stream().filter(e -> e != null && e.name != null && e.department != null);
}

public static Integer secondHighestSalaryStream(List<Employee> employees) {
    return validEmployees(employees).map(e -> e.salary).distinct()
            .sorted(Comparator.reverseOrder()).skip(1).findFirst().orElse(null);
}

public static Map<String, Double> averageSalaryByDepartmentStream(List<Employee> employees) {
    return validEmployees(employees).collect(Collectors.groupingBy(e -> e.department, TreeMap::new, Collectors.averagingInt(e -> e.salary)));
}

public static Map<String, String> highestPaidByDepartmentStream(List<Employee> employees) {
    return validEmployees(employees).collect(Collectors.groupingBy(e -> e.department, TreeMap::new,
            Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt((Employee e) -> e.salary)),
                    best -> best.map(e -> e.name).orElse(null))));
}

public static Map<String, List<String>> namesByDepartmentStream(List<Employee> employees) {
    return validEmployees(employees).collect(Collectors.groupingBy(e -> e.department, TreeMap::new, Collectors.mapping(e -> e.name, Collectors.toList())));
}

public static Character firstNonRepeatingStream(String text) {
    if (text == null || text.isEmpty()) return null;
    return text.chars().mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
            .entrySet().stream().filter(entry -> entry.getValue() == 1).map(Map.Entry::getKey).findFirst().orElse(null);
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

The optimized solution is Approach 3 above, which is also the version implemented in this repository. The walkthrough below runs each pipeline on the sample input and shows what every step produces.

**Input** (the six employees in `main()`):

| Name | Department | Salary |
|------|------------|--------|
| Asha | Engineering | 100000 |
| Ravi | Engineering | 90000 |
| Meena | Engineering | 75000 |
| John | Sales | 60000 |
| Priya | Sales | 65000 |
| Kiran | HR | 55000 |

**Step 0, the safeguard (`validEmployees`).** The list is not `null` and not empty, so its stream is used. The filter keeps an employee only when the employee, its name and its department are all non-`null`. All six pass, so the six employees flow into every pipeline below.

#### 1. Second highest salary

| Step | Operation | Result |
|------|-----------|--------|
| 1 | `map(e -> e.salary)` | `100000, 90000, 75000, 60000, 65000, 55000` |
| 2 | `distinct()` | no duplicates, so the same six values |
| 3 | `sorted(reverseOrder())` | `100000, 90000, 75000, 65000, 60000, 55000` |
| 4 | `skip(1)` drops the highest | `90000, 75000, 65000, 60000, 55000` |
| 5 | `findFirst()` takes the first value | `Optional[90000]` |
| 6 | `orElse(null)` resolves the `Optional` | `90000` |

With only one distinct salary (or no employees) step 4 leaves nothing, `findFirst()` is empty and `orElse(null)` returns `null`.

#### 2. Average salary per department

| Step | Operation | Result |
|------|-----------|--------|
| 1 | `groupingBy(department, TreeMap::new, ...)` creates one bucket per department, in sorted key order | `Engineering`, `HR`, `Sales` |
| 2 | each employee is added to its bucket | `Engineering: 100000, 90000, 75000`; `HR: 55000`; `Sales: 60000, 65000` |
| 3 | `averagingInt(salary)` sums and divides per bucket | `Engineering: 265000 / 3`; `HR: 55000 / 1`; `Sales: 125000 / 2` |
| 4 | the result map | `{Engineering=88333.33333333333, HR=55000.0, Sales=62500.0}` |

#### 3. Highest paid employee per department

| Step | Operation | Result |
|------|-----------|--------|
| 1 | `groupingBy(department, TreeMap::new, ...)` | the same three buckets as above |
| 2 | `maxBy(comparingInt(salary))` keeps the best employee of each bucket (an `Optional`, never empty because a bucket has at least one employee) | `Engineering: Asha`; `HR: Kiran`; `Sales: Priya (65000 beats John 60000)` |
| 3 | `collectingAndThen(..., best -> best.map(e -> e.name).orElse(null))` turns each `Optional` into the name | `Asha`, `Kiran`, `Priya` |
| 4 | the result map | `{Engineering=Asha, HR=Kiran, Sales=Priya}` |

#### 4. Names per department

| Step | Operation | Result |
|------|-----------|--------|
| 1 | `groupingBy(department, TreeMap::new, ...)` | the same three buckets |
| 2 | `mapping(e -> e.name, toList())` collects the names in input order | `Engineering: [Asha, Ravi, Meena]`; `HR: [Kiran]`; `Sales: [John, Priya]` |
| 3 | the result map | `{Engineering=[Asha, Ravi, Meena], HR=[Kiran], Sales=[John, Priya]}` |

#### 5. First non-repeating character, input `"swiss"`

| Step | Operation | Result |
|------|-----------|--------|
| 0 | safeguard: `null` or empty text returns `null` | `"swiss"` is neither, so continue |
| 1 | `chars().mapToObj(c -> (char) c)` | `s, w, i, s, s` |
| 2 | `groupingBy(identity, LinkedHashMap::new, counting())` counts and keeps first-seen order | `{s=3, w=1, i=1}` |
| 3 | `filter(count == 1)` | `w=1, i=1` |
| 4 | `map(Map.Entry::getKey)` | `w, i` |
| 5 | `findFirst()` then `orElse(null)` | `'w'` |

#### Safeguards at work

| Input | Second highest | Maps | First non-repeating |
|-------|----------------|------|---------------------|
| `[null, Zoe(no department), Sam(Sales, 70000)]` | `null` (only one valid salary) | `{Sales=...}` built from Sam alone | n/a |
| `[]` (empty list) | `null` | `{}` | n/a |
| `null` list | `null` | `{}` | n/a |
| text `"aabb"` | n/a | n/a | `null` (every character repeats) |
| text `null` | n/a | n/a | `null` |

## Source Code

### StreamsInterviewQuestions.java

```java
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        printAll(employees);
        System.out.println();

        System.out.println("Input  : text = \"swiss\"");
        System.out.println("Output : first non-repeating character = " + firstNonRepeating("swiss"));
        System.out.println();

        List<Employee> unusable = Arrays.asList(null, new Employee("Zoe", null, 50000), new Employee("Sam", "Sales", 70000));
        System.out.println("Input  : employees = [null, Zoe(no department), Sam(Sales, 70000)]   (null and incomplete entries are skipped)");
        printAll(unusable);
        System.out.println();

        System.out.println("Input  : employees = [] (empty list)");
        printAll(Collections.<Employee>emptyList());
        System.out.println();

        System.out.println("Input  : employees = null");
        printAll(null);
        System.out.println();

        System.out.println("Input  : text = \"aabb\" (every character repeats)");
        System.out.println("Output : first non-repeating character = " + firstNonRepeating("aabb"));
        System.out.println("Input  : text = null");
        System.out.println("Output : first non-repeating character = " + firstNonRepeating(null));
    }

    private static void printAll(List<Employee> employees) {
        System.out.println("Output : second highest salary = " + secondHighestSalary(employees));
        System.out.println("Output : average salary by department = " + averageSalaryByDepartment(employees));
        System.out.println("Output : highest paid by department = " + highestPaidByDepartment(employees));
        System.out.println("Output : names by department = " + namesByDepartment(employees));
    }

    /** The employees every question works on: a null list or a null/incomplete employee is skipped. */
    private static Stream<Employee> validEmployees(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return Stream.empty();
        }
        return employees.stream().filter(e -> e != null && e.name != null && e.department != null);
    }

    /** Second highest distinct salary, or {@code null} when there are fewer than two distinct salaries. */
    public static Integer secondHighestSalary(List<Employee> employees) {
        return validEmployees(employees)
                .map(e -> e.salary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElse(null);
    }

    public static Map<String, Double> averageSalaryByDepartment(List<Employee> employees) {
        return validEmployees(employees)
                .collect(Collectors.groupingBy(e -> e.department, TreeMap::new, Collectors.averagingInt(e -> e.salary)));
    }

    public static Map<String, String> highestPaidByDepartment(List<Employee> employees) {
        return validEmployees(employees)
                .collect(Collectors.groupingBy(e -> e.department, TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt((Employee e) -> e.salary)),
                                best -> best.map(e -> e.name).orElse(null))));
    }

    public static Map<String, List<String>> namesByDepartment(List<Employee> employees) {
        return validEmployees(employees)
                .collect(Collectors.groupingBy(e -> e.department, TreeMap::new, Collectors.mapping(e -> e.name, Collectors.toList())));
    }

    /** First character that appears exactly once, or {@code null} for a null, empty or all-repeating text. */
    public static Character firstNonRepeating(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        return text.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
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
