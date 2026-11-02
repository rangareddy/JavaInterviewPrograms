# Top K Frequent Elements

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements in any order.

## Algorithm Strategy (Frequency Map + Bucket Sort)

1. Count the frequency of each number with a `HashMap`.
2. Create buckets where index `f` holds the numbers that occur exactly `f` times; a frequency can never exceed `n`, so `n + 1` buckets are enough.
3. Walk the buckets from the highest frequency down and collect numbers until `k` of them have been taken.
4. This avoids sorting or a heap and runs in linear time. (Alternative: a min-heap of size `k` gives `O(n log k)`.)

## Example walkthrough

- For `[1, 1, 1, 2, 2, 3]` and `k = 2`: counts are 1 -> 3, 2 -> 2, 3 -> 1; buckets are [3: {1}], [2: {2}], [1: {3}].
- Taking from the top gives `[1, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Counting plus one pass over the buckets.
- **Space Complexity:** `O(n)` — The map and the buckets.

## Source Code

### TopKFrequentElements.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopKFrequentElements {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 1, 2, 2, 3}, {1}, {4, 4, 4, 5, 5, 6, 6, 6, 6}};
        int[] ks = {2, 1, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + Arrays.toString(topKFrequent(inputs[i], ks[i])));
            System.out.println();
        }
    }

    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.merge(num, 1, Integer::sum);
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            int count = entry.getValue();
            if (buckets[count] == null) {
                buckets[count] = new ArrayList<>();
            }
            buckets[count].add(entry.getKey());
        }

        int[] result = new int[k];
        int index = 0;
        for (int count = buckets.length - 1; count >= 0 && index < k; count--) {
            if (buckets[count] == null) {
                continue;
            }
            for (int num : buckets[count]) {
                if (index == k) {
                    break;
                }
                result[index++] = num;
            }
        }
        return result;
    }
}
```
