# Alien Dictionary

Words of a new alien language (using the Latin alphabet) are given sorted lexicographically according to the unknown letter order of that language. Derive a valid order of the letters, or return an empty string if no valid order exists.

## Algorithm Strategy (Topological Sort of Letter Order)

1. Create a node for every letter that appears in any word.
2. Compare each pair of adjacent words and find the first position where they differ: the letter of the first word comes before the letter of the second word, so add the edge `a -> b`. Only the first difference carries information.
3. If the second word is a proper prefix of the first one (such as `"abc"` before `"ab"`), the input is invalid: return `""`.
4. Run Kahn's topological sort. If every letter was output, the output is a valid order; if some letters remain, there is a cycle and the answer is `""`.

## Example walkthrough

- For `["wrt", "wrf", "er", "ett", "rftt"]`: the pairs give the edges t -> f, w -> e, r -> t and e -> r.
- The only topological order is `"wertf"`.

## Complexity Analysis

- **Time Complexity:** `O(C)` — `C` is the total number of characters in all words.
- **Space Complexity:** `O(1)` — At most 26 letters and their edges (plus the output).

## Source Code

### AlienDictionary.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AlienDictionary {

    public static void main(String[] args) {
        String[][] inputs = {
                {"wrt", "wrf", "er", "ett", "rftt"},
                {"z", "x"},
                {"z", "x", "z"},
                {"abc", "ab"}
        };
        for (String[] words : inputs) {
            System.out.println("Input  : words = " + Arrays.toString(words));
            System.out.println("Output : \"" + alienOrder(words) + "\"");
            System.out.println();
        }
    }

    public static String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new LinkedHashMap<>();
        Map<Character, Integer> inDegree = new LinkedHashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.putIfAbsent(c, new LinkedHashSet<>());
                inDegree.putIfAbsent(c, 0);
            }
        }

        for (int i = 0; i + 1 < words.length; i++) {
            String first = words[i];
            String second = words[i + 1];
            if (first.length() > second.length() && first.startsWith(second)) {
                return "";      // invalid: a longer word cannot come before its own prefix
            }
            for (int j = 0; j < Math.min(first.length(), second.length()); j++) {
                char from = first.charAt(j);
                char to = second.charAt(j);
                if (from != to) {
                    if (graph.get(from).add(to)) {
                        inDegree.merge(to, 1, Integer::sum);
                    }
                    break;
                }
            }
        }

        Deque<Character> queue = new ArrayDeque<>();
        for (Map.Entry<Character, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        StringBuilder order = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            order.append(c);
            for (char next : graph.get(c)) {
                if (inDegree.merge(next, -1, Integer::sum) == 0) {
                    queue.add(next);
                }
            }
        }
        return order.length() == graph.size() ? order.toString() : "";
    }
}
```
