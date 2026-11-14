package com.ranga.core;

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

/**
 * Solve the classic Java 8 stream questions on a list of employees: the second highest salary, the average salary per department, the highest paid employee of each department, the names grouped by department, and the first non-repeating character of a string.
 *
 * <p><b>Algorithm Strategy (Collectors: groupingBy, averagingInt, maxBy and sorted/skip):</b>
 * <ol>
 *   <li>Safeguard first: a {@code null} list, a {@code null} employee or an employee without a name or department is skipped by one shared helper, {@code validEmployees}, so no pipeline can fail with a {@code NullPointerException}.</li>
 *   <li>Second highest salary: take the distinct salaries, sort them in descending order, skip the first one and take the next ({@code distinct().sorted(reverseOrder()).skip(1).findFirst()}).</li>
 *   <li>Average salary per department: {@code groupingBy(department, averagingInt(salary))}.</li>
 *   <li>Highest paid employee per department: {@code groupingBy(department, maxBy(comparingInt(salary)))}, with the {@code Optional} of each group resolved inside the collector ({@code collectingAndThen}).</li>
 *   <li>Names per department: {@code groupingBy(department, mapping(name, toList()))}. First non-repeating character: collect the counts into a {@code LinkedHashMap} and take the first entry with count 1.</li>
 * </ol>
 *
 * <p><b>Return convention:</b> every method returns a plain value and none returns an {@code Optional}. A question about a missing answer (second highest salary, first non-repeating character) returns {@code null} when there is no answer; the grouping questions return an empty map. A {@code null} or empty input is never an error.
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the sample employees the second highest salary is {@code 90000} (the highest is {@code 100000}).</li>
 *   <li>Grouping by department gives for example {@code Engineering -> [Asha, Ravi, Meena]} and the average of the Engineering salaries is {@code 88333.33}.</li>
 *   <li>For an empty or {@code null} list the second highest salary is {@code null} and every grouping is {@code {}}; for {@code "swiss"} the first non-repeating character is {@code 'w'}.</li>
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
