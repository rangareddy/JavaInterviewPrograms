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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort by Frequency

Count the frequencies, sort the distinct numbers by frequency and take the first `k`.

```java
public static int[] topKFrequentSort(int[] nums, int k) {
    Map<Integer, Integer> frequency = new HashMap<>();
    for (int num : nums) frequency.merge(num, 1, Integer::sum);
    List<Integer> keys = new ArrayList<>(frequency.keySet());
    keys.sort((a, b) -> frequency.get(b) - frequency.get(a));
    int[] result = new int[k];
    for (int i = 0; i < k; i++) result[i] = keys.get(i);
    return result;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Min-Heap of Size k

Keep only the `k` most frequent numbers in a min-heap ordered by frequency: push every number and drop the least frequent one whenever the heap grows beyond `k`.

```java
public static int[] topKFrequentHeap(int[] nums, int k) {
    Map<Integer, Integer> frequency = new HashMap<>();
    for (int num : nums) frequency.merge(num, 1, Integer::sum);
    PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> frequency.get(a) - frequency.get(b));
    for (int num : frequency.keySet()) {
        heap.add(num);
        if (heap.size() > k) heap.poll();
    }
    int[] result = new int[k];
    for (int i = k - 1; i >= 0; i--) result[i] = heap.poll();
    return result;
}
```

- **Time Complexity:** O(n log k)
- **Space Complexity:** O(n)

### Approach 3: Quickselect on the Frequencies

Put the distinct numbers in an array and partition it by frequency (quickselect) until the `k` most frequent numbers occupy the first `k` positions. Expected linear time without sorting or a heap.

```java
public static int[] topKFrequentQuickselect(int[] nums, int k) {
    Map<Integer, Integer> frequency = new HashMap<>();
    for (int num : nums) frequency.merge(num, 1, Integer::sum);
    int[] unique = new int[frequency.size()];
    int index = 0;
    for (int key : frequency.keySet()) unique[index++] = key;
    int low = 0, high = unique.length - 1, target = k - 1;
    Random random = new Random(1);
    while (low < high) {
        int pivotIndex = low + random.nextInt(high - low + 1);
        int pivot = frequency.get(unique[pivotIndex]);
        swap(unique, pivotIndex, high);
        int store = low;
        for (int i = low; i < high; i++) {
            if (frequency.get(unique[i]) > pivot) swap(unique, i, store++);     // more frequent first
        }
        swap(unique, store, high);
        if (store == target) break;
        if (store < target) low = store + 1; else high = store - 1;
    }
    return Arrays.copyOf(unique, k);
}

private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
```

- **Time Complexity:** O(n) expected
- **Space Complexity:** O(n)

### Approach 4: TreeMap from Frequency to Numbers

Group the numbers by frequency in a `TreeMap` (sorted by frequency) and read it from the highest frequency down until `k` numbers are collected.

```java
public static int[] topKFrequentTreeMap(int[] nums, int k) {
    Map<Integer, Integer> frequency = new HashMap<>();
    for (int num : nums) frequency.merge(num, 1, Integer::sum);
    TreeMap<Integer, List<Integer>> byFrequency = new TreeMap<>(Comparator.reverseOrder());
    for (Map.Entry<Integer, Integer> e : frequency.entrySet()) {
        byFrequency.computeIfAbsent(e.getValue(), f -> new ArrayList<>()).add(e.getKey());
    }
    int[] result = new int[k];
    int index = 0;
    for (List<Integer> group : byFrequency.values()) {
        for (int num : group) { if (index == k) break; result[index++] = num; }
        if (index == k) break;
    }
    return result;
}
```

- **Time Complexity:** O(n + d log d) where d is the number of distinct frequencies
- **Space Complexity:** O(n)

### Approach 5: Streams

Group and count with `Collectors.groupingBy`, sort the entries by count in descending order, and take the first `k` keys. Short and declarative.

```java
public static int[] topKFrequentStream(int[] nums, int k) {
    Map<Integer, Long> counts = Arrays.stream(nums).boxed()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    return counts.entrySet().stream()
            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
            .limit(k)
            .mapToInt(Map.Entry::getKey)
            .toArray();
}
```

- **Time Complexity:** O(n + m log m) where m is the number of distinct numbers
- **Space Complexity:** O(n)

### Approach 6: Max-Heap of All Entries

Push every `(number, frequency)` pair into a max-heap ordered by frequency and remove the top `k` times. Easy to read; building the heap is linear, each removal `O(log m)`.

```java
public static int[] topKFrequentMaxHeap(int[] nums, int k) {
    Map<Integer, Integer> frequency = new HashMap<>();
    for (int num : nums) frequency.merge(num, 1, Integer::sum);
    PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
    heap.addAll(frequency.entrySet());
    int[] result = new int[k];
    for (int i = 0; i < k; i++) result[i] = heap.poll().getKey();
    return result;
}
```

- **Time Complexity:** O(n + k log m)
- **Space Complexity:** O(n)

### Approach 7: Bucket Sort by Frequency (Optimized)

A frequency is at most `n`, so group the numbers into `n + 1` buckets indexed by frequency and read the buckets from the highest to the lowest. This is the version implemented in the repository.

```java
public static int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> frequency = new HashMap<>();
    for (int num : nums) frequency.merge(num, 1, Integer::sum);
    @SuppressWarnings("unchecked")
    List<Integer>[] buckets = new List[nums.length + 1];
    for (Map.Entry<Integer, Integer> e : frequency.entrySet()) {
        if (buckets[e.getValue()] == null) buckets[e.getValue()] = new ArrayList<>();
        buckets[e.getValue()].add(e.getKey());
    }
    int[] result = new int[k];
    int index = 0;
    for (int count = buckets.length - 1; count >= 0 && index < k; count--) {
        if (buckets[count] == null) continue;
        for (int num : buckets[count]) { if (index == k) break; result[index++] = num; }
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort by Frequency | O(n log n) | O(n) |
| 2 | Min-Heap of Size k | O(n log k) | O(n) |
| 3 | Quickselect on the Frequencies | O(n) expected | O(n) |
| 4 | TreeMap from Frequency to Numbers | O(n + d log d) where d is the number of distinct frequencies | O(n) |
| 5 | Streams | O(n + m log m) where m is the number of distinct numbers | O(n) |
| 6 | Max-Heap of All Entries | O(n + k log m) | O(n) |
| 7 | Bucket Sort by Frequency (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
