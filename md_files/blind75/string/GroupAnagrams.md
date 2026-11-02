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
