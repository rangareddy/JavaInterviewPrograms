# Move Zeros and Ones (Sort 0s, 1s, 2s)

Utility class to sort an array containing 0s, 1s, and 2s using Dijkstra's Dutch National Flag algorithm.

### Algorithm Strategy (Three-Way Partitioning)

- `low`: Boundary for 0s (everything strictly before `low` is 0)
- `mid`: Current element under evaluation
- `high`: Boundary for 2s (everything strictly after `high` is 2)

### Complexity Analysis

- **Time Complexity:** `O(n)` — Single pass linear scan where `n` is the array length.
- **Space Complexity:** `O(1)` — Operates entirely in-place with zero additional heap memory allocation.

### MoveZerosAndOnes.java

```java
import java.util.Arrays;

/**
 * Utility class to sort an array containing 0s, 1s, and 2s using Dijkstra's
 * Dutch National Flag algorithm.
 *
 * <p><b>Algorithm Strategy (Three-Way Partitioning):</b>
 * Maintains three pointers:
 * <ul>
 *   <li>{@code low}: Boundary for 0s (everything strictly before {@code low} is 0)</li>
 *   <li>{@code mid}: Current element under evaluation</li>
 *   <li>{@code high}: Boundary for 2s (everything strictly after {@code high} is 2)</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Single pass linear scan where {@code n} is the array length.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Operates entirely in-place with zero additional heap memory allocation.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MoveZerosAndOnes {

    public static void main(String[] args) {
        int[] input = {0, 1, 2, 0, 1, 0, 1, 2, 1, 0, 2};

        System.out.println("Original Array : " + Arrays.toString(input));
        sortColors(input);
        System.out.println("Sorted Array   : " + Arrays.toString(input));
    }

    /**
     * Sorts an array consisting of only 0s, 1s, and 2s in-place.
     *
     * <p>Note: Mutates the array directly. For standard API design, returning {@code void}
     * is preferred for in-place modifications.
     *
     * @param arr the input array containing elements of value 0, 1, or 2
     */
    public static void sortColors(int[] arr) {
        // Guard Clause: null or single-element inputs require no sorting
        if (arr == null || arr.length <= 1) {
            return;
        }

        int low = 0;
        int mid = 0;
        int high = arr.length - 1;

        while (mid <= high) {
            switch (arr[mid]) {
                case 0:
                    // Swap current 0 to the low pointer boundary
                    swap(arr, low, mid);
                    low++;
                    mid++;
                    break;

                case 1:
                    // 1 is in its correct relative middle position; move to next element
                    mid++;
                    break;

                case 2:
                    // Swap current 2 to the high pointer boundary.
                    // Do NOT increment mid yet because the swapped element from 'high'
                    // has not been evaluated.
                    swap(arr, mid, high);
                    high--;
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Array contains invalid element: " + arr[mid] + ". Only 0, 1, and 2 are supported."
                    );
            }
        }
    }

    /**
     * Helper method to swap two elements in an array in-place.
     *
     * @param arr the array where swap occurs
     * @param i   index of first element
     * @param j   index of second element
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```
