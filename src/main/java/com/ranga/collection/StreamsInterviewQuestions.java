package com.ranga.collection;

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

/**
 * Solve the classic Java 8 stream questions on a list of employees: the second highest salary, the average salary per department, the highest paid employee of each department, the names grouped by department, and the first non-repeating character of a string.
 *
 * <p><b>Algorithm Strategy (Collectors: groupingBy, averagingInt, maxBy and sorted/skip):</b>
 * <ol>
 *   <li>Second highest salary: take the distinct salaries, sort them in descending order, skip the first one and take the next ({@code distinct().sorted(reverseOrder()).skip(1).findFirst()}).</li>
 *   <li>Average salary per department: {@code groupingBy(department, averagingInt(salary))}.</li>
 *   <li>Highest paid employee per department: {@code groupingBy(department, maxBy(comparingInt(salary)))}, unwrapping the {@code Optional}.</li>
 *   <li>Names per department: {@code groupingBy(department, mapping(name, toList()))}. First non-repeating character: collect the counts into a {@code LinkedHashMap} and take the first entry with count 1.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the sample employees the second highest salary is {@code 90000} (the highest is {@code 100000}).</li>
 *   <li>Grouping by department gives for example {@code Engineering -> [Asha, Ravi, Meena]} and the average of the Engineering salaries is {@code 88333.33}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting the distinct salaries dominates; the groupings are O(n).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The grouped collections.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
