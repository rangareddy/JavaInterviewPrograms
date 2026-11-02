# Binary Search

Utility class to search an element in a sorted array using Binary Search.

### Algorithm Strategy (Halve the Search Range)

1. Keep a `low` and `high` index around the range that can still contain the element.
2. Compare the middle element with the target: return its index if equal, otherwise discard the half that cannot contain the target.
3. Stop when `low > high`; the element is not present, so return `-1`.

### Time Complexity

- Best: O(1)
- Average: O(log n)
- Worst: O(log n)

### Space Complexity

- O(1) (Iterative implementation with a few index variables.)

### BinarySearch.java

```java
import java.util.Arrays;

/**
 * Utility class to search an element in a sorted array using Binary Search.
 *
 * <p><b>Algorithm Strategy (Halve the Search Range):</b>
 * <ol>
 *   <li>Keep a {@code low} and {@code high} index around the range that can still contain the element.</li>
 *   <li>Compare the middle element with the target: return its index if equal, otherwise discard the half that cannot contain the target.</li>
 *   <li>Stop when {@code low > high}; the element is not present, so return {@code -1}.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(1)} — The target is the middle element.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(log n)} — The range is halved on every step.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(log n)} — The target is absent or found in the last step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Iterative implementation with a few index variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinarySearch {
    public static void main(String[] args) {

        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        Arrays.sort(arr);

        System.out.println("Array elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        System.out.println();

        int[] elements = {20, 16, 9, 4, 8};
        for (int element : elements) {
            int index = binarySearch(arr, element);
            if (index != -1) {
                System.out.println(element + " found at index " + index);
            } else {
                System.out.println(element + " is not found");
            }
        }

    }

    /**
     * Searches for an element in a sorted array.
     *
     * @param arr the array sorted in ascending order
     * @param element the element to find
     * @return the index of the element, or {@code -1} if it is not present
     */
    public static int binarySearch(int[] arr, int element) {
        int n = arr.length;
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int val = arr[mid];
            if (val == element) {
                return mid;
            } else if (val < element) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
```
