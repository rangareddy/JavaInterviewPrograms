# Merge Sort

```java
import java.util.Arrays;

/**
 * User: Ranga Reddy
 * Date: 11/15/2019
 * Time: 5:12 PM
 * Description:
 */
public class MergeSort {
    public static void main(String[] args) {
        int arr[] = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Array elements before sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        arr = mergeSort(arr);
        System.out.println("\nArray elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
    }

    private static int[] mergeSort(int[] arr) {
        int n = arr.length;
        if (n <= 1) {
            return arr;
        }

        int mid = n / 2;
        int left[] = new int[mid];
        int right[] = new int[n - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[mid + i];
        }

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int lengthL = left.length;
        int rightN = right.length;

        int result[] = new int[lengthL + rightN];
        int k = 0, i = 0, j = 0;

        while (i < lengthL || j < rightN) {
            if (i < lengthL && j < rightN) {
                if (left[i] < right[j]) {
                    result[k++] = left[i++];
                } else {
                    result[k++] = right[j++];
                }
            } else if (i < lengthL) {
                result[k++] = left[i++];
            } else if (j < rightN) {
                result[k++] = right[j++];
            }
        }
        return result;
    }
}
```
