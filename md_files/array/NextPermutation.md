# Next Permutation

Rearrange the numbers of `nums` into the next lexicographically greater permutation. If no such permutation exists (the array is in descending order), rearrange it into the lowest possible order (ascending). The change must be done in place with constant extra memory.

## Algorithm Strategy (Find the Pivot, Swap, Reverse the Suffix)

1. Scan from the right to find the first index `i` with `nums[i] < nums[i + 1]` (the pivot). The part after it is in descending order and cannot be made bigger by itself.
2. If no pivot exists, the whole array is the last permutation: reverse it to get the first one.
3. Otherwise find the rightmost element `nums[j]` that is greater than the pivot and swap the two.
4. Reverse the suffix after position `i` so it becomes the smallest possible (ascending) arrangement.

## Example walkthrough

- For `[1, 3, 2]`: pivot is 1 (index 0), the rightmost larger element is 2; after the swap `[2, 3, 1]` and reversing the suffix gives `[2, 1, 3]`.
- For `[3, 2, 1]` there is no pivot, so the array is reversed to `[1, 2, 3]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — At most three linear scans.
- **Space Complexity:** `O(1)` — In place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Generate All Permutations

Generate all permutations in lexicographic order and take the one after the input. Only useful for tiny inputs.

```java
public static void nextPermutationBrute(int[] nums) {
    List<List<Integer>> all = new ArrayList<>();
    permute(nums.clone(), 0, all);
    Collections.sort(all, (a, b) -> {
        for (int i = 0; i < a.size(); i++) if (!a.get(i).equals(b.get(i))) return a.get(i) - b.get(i);
        return 0;
    });
    List<Integer> current = new ArrayList<>();
    for (int n : nums) current.add(n);
    int index = -1;
    for (int i = 0; i < all.size(); i++) if (all.get(i).equals(current)) index = i;     // last equal one handles duplicates
    List<Integer> next = all.get((index + 1) % all.size());
    // with duplicates the next distinct permutation is the first one that differs
    for (int i = index + 1; i < all.size() && next.equals(current); i++) next = all.get(i);
    if (next.equals(current)) next = all.get(0);
    for (int i = 0; i < nums.length; i++) nums[i] = next.get(i);
}

private static void permute(int[] arr, int k, List<List<Integer>> out) {
    if (k == arr.length) { List<Integer> p = new ArrayList<>(); for (int v : arr) p.add(v); out.add(p); return; }
    for (int i = k; i < arr.length; i++) {
        int t = arr[k]; arr[k] = arr[i]; arr[i] = t;
        permute(arr, k + 1, out);
        t = arr[k]; arr[k] = arr[i]; arr[i] = t;
    }
}
```

- **Time Complexity:** O(n! * n)
- **Space Complexity:** O(n! * n)

### Approach 2: Sort the Suffix Instead of Reversing

Find the pivot and the swap partner as before, but restore the suffix by sorting it.

```java
public static void nextPermutationSort(int[] nums) {
    int i = nums.length - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) i--;
    if (i >= 0) {
        int j = nums.length - 1;
        while (nums[j] <= nums[i]) j--;
        int t = nums[i]; nums[i] = nums[j]; nums[j] = t;
    }
    Arrays.sort(nums, i + 1, nums.length);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Approach 3: Pivot, Swap, Reverse (Optimized)

The suffix after the pivot is already descending, so a reversal sorts it in `O(n)`. This is the version implemented in the repository.

```java
public static void nextPermutation(int[] nums) {
    int i = nums.length - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) i--;
    if (i >= 0) {
        int j = nums.length - 1;
        while (nums[j] <= nums[i]) j--;
        swap(nums, i, j);
    }
    reverse(nums, i + 1, nums.length - 1);
}

private static void reverse(int[] nums, int left, int right) {
    while (left < right) swap(nums, left++, right--);
}

private static void swap(int[] nums, int i, int j) {
    int temp = nums[i]; nums[i] = nums[j]; nums[j] = temp;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Generate All Permutations | O(n! * n) | O(n! * n) |
| 2 | Sort the Suffix Instead of Reversing | O(n log n) | O(1) |
| 3 | Pivot, Swap, Reverse (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### NextPermutation.java

```java
import java.util.Arrays;

public class NextPermutation {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {3, 2, 1}, {1, 1, 5}, {1, 3, 2}, {2, 3, 1}, {5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            nextPermutation(nums);
            System.out.println("Output : " + Arrays.toString(nums));
            System.out.println();
        }
    }

    public static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1, nums.length - 1);
    }

    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left++, right--);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```
