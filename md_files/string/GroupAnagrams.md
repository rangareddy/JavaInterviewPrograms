# Group Anagrams

Given an array of strings, group the anagrams together. The groups can be returned in any order.

## Algorithm Strategy (Hash Map Keyed by the Sorted Letters)

1. Two words are anagrams exactly when their sorted letters are equal, so the sorted word is a canonical key for the group.
2. For every word, build the key by sorting its characters.
3. Use `Map<String, List<String>>`: append the word to the list stored under its key.
4. Return all the lists of the map.

## Example walkthrough

- For `["eat", "tea", "tan", "ate", "nat", "bat"]`: the keys are `aet`, `aet`, `ant`, `aet`, `ant`, `abt`.
- Groups: `[eat, tea, ate]`, `[tan, nat]`, `[bat]`.

## Complexity Analysis

- **Time Complexity:** `O(n * k log k)` — `n` words, each of length at most `k`, sorted once.
- **Space Complexity:** `O(n * k)` — The map stores all words.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Pairwise Comparison)

Put each word into the first existing group whose representative is an anagram of it (checked by sorting), otherwise start a new group.

```java
public static List<List<String>> groupAnagramsPairwise(String[] strs) {
    List<List<String>> groups = new ArrayList<>();
    for (String word : strs) {
        boolean placed = false;
        for (List<String> group : groups) {
            if (sorted(group.get(0)).equals(sorted(word))) {
                group.add(word);
                placed = true;
                break;
            }
        }
        if (!placed) groups.add(new ArrayList<>(Arrays.asList(word)));
    }
    return groups;
}

private static String sorted(String word) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    return new String(chars);
}
```

- **Time Complexity:** O(n^2 * k log k)
- **Space Complexity:** O(n * k)

### Approach 2: Hash Map Keyed by the Sorted Word

Anagrams have the same sorted letters, so the sorted word is a canonical key. This is the version implemented in the repository.

```java
public static List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new LinkedHashMap<>();
    for (String word : strs) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        groups.computeIfAbsent(new String(chars), key -> new ArrayList<>()).add(word);
    }
    return new ArrayList<>(groups.values());
}
```

- **Time Complexity:** O(n * k log k)
- **Space Complexity:** O(n * k)

### Approach 3: Prime-Product Key

Assign a distinct prime to each letter; the product of the primes of a word is the same for anagrams and different for words that are not anagrams (unique factorization). A `BigInteger` keeps the key exact for long words.

```java
public static List<List<String>> groupAnagramsPrimes(String[] strs) {
    int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
    Map<BigInteger, List<String>> groups = new LinkedHashMap<>();
    for (String word : strs) {
        BigInteger key = BigInteger.ONE;
        for (char c : word.toCharArray()) key = key.multiply(BigInteger.valueOf(primes[c - 'a']));
        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
    }
    return new ArrayList<>(groups.values());
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(n * k)

### Approach 4: Sort by Signature, Then Read the Runs

Pair every word with its sorted letters, sort the pairs by that signature and read off the groups as runs of equal signatures. No hash map is needed.

```java
public static List<List<String>> groupAnagramsSortedRuns(String[] strs) {
    List<String[]> pairs = new ArrayList<>();
    for (String word : strs) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        pairs.add(new String[]{new String(chars), word});
    }
    pairs.sort(Comparator.comparing((String[] p) -> p[0]));
    List<List<String>> groups = new ArrayList<>();
    String previous = null;
    for (String[] pair : pairs) {
        if (!pair[0].equals(previous)) groups.add(new ArrayList<>());
        groups.get(groups.size() - 1).add(pair[1]);
        previous = pair[0];
    }
    return groups;
}
```

- **Time Complexity:** O(n * k log k + n log n * k)
- **Space Complexity:** O(n * k)

### Approach 5: Streams groupingBy

Let `Collectors.groupingBy` build the groups from the sorted-letters key.

```java
public static List<List<String>> groupAnagramsStream(String[] strs) {
    return new ArrayList<>(Arrays.stream(strs).collect(Collectors.groupingBy(word -> {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }, LinkedHashMap::new, Collectors.toList())).values());
}
```

- **Time Complexity:** O(n * k log k)
- **Space Complexity:** O(n * k)

### Approach 6: Compact Letter-Count Signature

Use a short signature such as `a1e1t1` (only letters that occur, followed by their counts) as the key. It is built in `O(k)` without sorting and is shorter than printing all 26 counters, and unlike the prime product it never overflows.

```java
public static List<List<String>> groupAnagramsSignature(String[] strs) {
    Map<String, List<String>> groups = new LinkedHashMap<>();
    for (String word : strs) {
        int[] count = new int[26];
        for (char c : word.toCharArray()) count[c - 'a']++;
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 26; i++) if (count[i] > 0) key.append((char) ('a' + i)).append(count[i]);
        groups.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(word);
    }
    return new ArrayList<>(groups.values());
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(n * k)

### Approach 7: Hash Map Keyed by the Letter Counts (Optimized)

Use the 26 letter counts as the key instead of sorting each word, which removes the `log k` factor (for lowercase words).

```java
public static List<List<String>> groupAnagramsCounts(String[] strs) {
    Map<String, List<String>> groups = new LinkedHashMap<>();
    for (String word : strs) {
        int[] count = new int[26];
        for (char c : word.toCharArray()) count[c - 'a']++;
        groups.computeIfAbsent(Arrays.toString(count), key -> new ArrayList<>()).add(word);
    }
    return new ArrayList<>(groups.values());
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(n * k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Pairwise Comparison) | O(n^2 * k log k) | O(n * k) |
| 2 | Hash Map Keyed by the Sorted Word | O(n * k log k) | O(n * k) |
| 3 | Prime-Product Key | O(n * k) | O(n * k) |
| 4 | Sort by Signature, Then Read the Runs | O(n * k log k + n log n * k) | O(n * k) |
| 5 | Streams groupingBy | O(n * k log k) | O(n * k) |
| 6 | Compact Letter-Count Signature | O(n * k) | O(n * k) |
| 7 | Hash Map Keyed by the Letter Counts (Optimized) | O(n * k) | O(n * k) |

### Optimized Solution

The optimized solution is Approach 7 above. The version implemented in this repository is Approach 2.

## Source Code

### GroupAnagrams.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams {

    public static void main(String[] args) {
        String[][] inputs = {{"eat", "tea", "tan", "ate", "nat", "bat"}, {""}, {"a"}};
        for (String[] strs : inputs) {
            System.out.println("Input  : strs = " + Arrays.toString(strs));
            System.out.println("Output : " + groupAnagrams(strs));
            System.out.println();
        }
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null) {
            return new ArrayList<>();
        }

        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (String word : strs) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            groups.computeIfAbsent(new String(chars), key -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }
}
```
