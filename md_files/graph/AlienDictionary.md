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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force: Try Every Permutation of the Letters

Collect the distinct letters and test every ordering of them: an ordering is valid when it keeps every adjacent pair of words in order (and no word follows its own longer extension). Only practical for a handful of letters.

```java
public static String alienOrderPermutations(String[] words) {
    StringBuilder letters = new StringBuilder();
    for (String word : words) {
        for (char c : word.toCharArray()) if (letters.indexOf(String.valueOf(c)) < 0) letters.append(c);
    }
    return permute(letters.toString().toCharArray(), 0, words);
}

private static String permute(char[] letters, int index, String[] words) {
    if (index == letters.length) return isSorted(new String(letters), words) ? new String(letters) : null;
    for (int i = index; i < letters.length; i++) {
        char temp = letters[index]; letters[index] = letters[i]; letters[i] = temp;
        String found = permute(letters, index + 1, words);
        temp = letters[index]; letters[index] = letters[i]; letters[i] = temp;
        if (found != null) return found;
    }
    return index == 0 ? "" : null;
}

private static boolean isSorted(String order, String[] words) {
    for (int i = 0; i + 1 < words.length; i++) {
        String a = words[i], b = words[i + 1];
        int j = 0;
        while (j < Math.min(a.length(), b.length()) && a.charAt(j) == b.charAt(j)) j++;
        if (j == Math.min(a.length(), b.length())) {
            if (a.length() > b.length()) return false;
        } else if (order.indexOf(a.charAt(j)) > order.indexOf(b.charAt(j))) {
            return false;
        }
    }
    return true;
}
```

- **Time Complexity:** O(L! * C) for L distinct letters
- **Space Complexity:** O(L)

### Approach 2: DFS Topological Sort (Post-order)

Build the letter graph from adjacent words and run a DFS; a letter is appended after all letters that must come after it, so reversing the list gives a valid order. A letter reached while it is still being visited means a cycle.

```java
public static String alienOrderDfs(String[] words) {
    Map<Character, Set<Character>> graph = new LinkedHashMap<>();
    for (String word : words) for (char c : word.toCharArray()) graph.putIfAbsent(c, new LinkedHashSet<>());
    for (int i = 0; i + 1 < words.length; i++) {
        String a = words[i], b = words[i + 1];
        if (a.length() > b.length() && a.startsWith(b)) return "";
        for (int j = 0; j < Math.min(a.length(), b.length()); j++) {
            if (a.charAt(j) != b.charAt(j)) { graph.get(a.charAt(j)).add(b.charAt(j)); break; }
        }
    }
    Map<Character, Integer> state = new HashMap<>();       // 1 = visiting, 2 = done
    StringBuilder order = new StringBuilder();
    for (char c : graph.keySet()) {
        if (!visit(c, graph, state, order)) return "";
    }
    return order.reverse().toString();
}

private static boolean visit(char c, Map<Character, Set<Character>> graph, Map<Character, Integer> state, StringBuilder order) {
    Integer s = state.get(c);
    if (s != null) return s == 2;
    state.put(c, 1);
    for (char next : graph.get(c)) {
        if (!visit(next, graph, state, order)) return false;
    }
    state.put(c, 2);
    order.append(c);
    return true;
}
```

- **Time Complexity:** O(C)
- **Space Complexity:** O(1) (at most 26 letters)

### Approach 3: Iterative DFS Topological Sort

The same DFS topological sort as above but with an explicit stack instead of recursion. A node is marked "in progress" when first taken from the stack and "done" when taken again after all of its neighbours; reaching an in-progress node again means a cycle.

```java
public static String alienOrderIterativeDfs(String[] words) {
    boolean[][] edge = new boolean[26][26];
    boolean[] present = new boolean[26];
    for (String word : words) for (char c : word.toCharArray()) present[c - 'a'] = true;
    for (int i = 0; i + 1 < words.length; i++) {
        String a = words[i], b = words[i + 1];
        if (a.length() > b.length() && a.startsWith(b)) return "";
        for (int j = 0; j < Math.min(a.length(), b.length()); j++) {
            if (a.charAt(j) != b.charAt(j)) { edge[a.charAt(j) - 'a'][b.charAt(j) - 'a'] = true; break; }
        }
    }

    int[] state = new int[26];                      // 0 = new, 1 = in progress, 2 = done
    StringBuilder order = new StringBuilder();
    for (int start = 0; start < 26; start++) {
        if (!present[start] || state[start] != 0) continue;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int c = stack.peek();
            if (state[c] == 0) {
                state[c] = 1;
                for (int next = 0; next < 26; next++) {
                    if (!edge[c][next]) continue;
                    if (state[next] == 1) return "";        // back edge: cycle
                    if (state[next] == 0) stack.push(next);
                }
            } else {
                stack.pop();
                if (state[c] == 1) { state[c] = 2; order.append((char) ('a' + c)); }
            }
        }
    }
    return order.reverse().toString();
}
```

- **Time Complexity:** O(C + 26^2)
- **Space Complexity:** O(26^2)

### Approach 4: Adjacency Matrix + Kahn with a Min-Heap

Store the letter graph in a `boolean[26][26]` matrix and run Kahn's algorithm with a priority queue, so among several valid orders the alphabetically smallest is returned. The result is deterministic.

```java
public static String alienOrderMatrix(String[] words) {
    boolean[][] edge = new boolean[26][26];
    boolean[] present = new boolean[26];
    int[] inDegree = new int[26];
    for (String word : words) for (char c : word.toCharArray()) present[c - 'a'] = true;

    for (int i = 0; i + 1 < words.length; i++) {
        String a = words[i], b = words[i + 1];
        if (a.length() > b.length() && a.startsWith(b)) return "";
        for (int j = 0; j < Math.min(a.length(), b.length()); j++) {
            int from = a.charAt(j) - 'a', to = b.charAt(j) - 'a';
            if (from != to) {
                if (!edge[from][to]) { edge[from][to] = true; inDegree[to]++; }
                break;
            }
        }
    }
    PriorityQueue<Integer> ready = new PriorityQueue<>();
    int letters = 0;
    for (int c = 0; c < 26; c++) {
        if (present[c]) { letters++; if (inDegree[c] == 0) ready.add(c); }
    }
    StringBuilder order = new StringBuilder();
    while (!ready.isEmpty()) {
        int c = ready.poll();
        order.append((char) ('a' + c));
        for (int next = 0; next < 26; next++) {
            if (edge[c][next] && --inDegree[next] == 0) ready.add(next);
        }
    }
    return order.length() == letters ? order.toString() : "";
}
```

- **Time Complexity:** O(C + 26^2 + 26 log 26)
- **Space Complexity:** O(26^2)

### Approach 5: Kahn's Topological Sort (Optimized)

Repeatedly output letters that no remaining letter has to precede. If some letters are never output, there is a cycle. This is the version implemented in the repository.

```java
public static String alienOrder(String[] words) {
    Map<Character, Set<Character>> graph = new LinkedHashMap<>();
    Map<Character, Integer> inDegree = new LinkedHashMap<>();
    for (String word : words) {
        for (char c : word.toCharArray()) { graph.putIfAbsent(c, new LinkedHashSet<>()); inDegree.putIfAbsent(c, 0); }
    }
    for (int i = 0; i + 1 < words.length; i++) {
        String a = words[i], b = words[i + 1];
        if (a.length() > b.length() && a.startsWith(b)) return "";
        for (int j = 0; j < Math.min(a.length(), b.length()); j++) {
            if (a.charAt(j) != b.charAt(j)) {
                if (graph.get(a.charAt(j)).add(b.charAt(j))) inDegree.merge(b.charAt(j), 1, Integer::sum);
                break;
            }
        }
    }
    Deque<Character> queue = new ArrayDeque<>();
    for (Map.Entry<Character, Integer> e : inDegree.entrySet()) if (e.getValue() == 0) queue.add(e.getKey());
    StringBuilder order = new StringBuilder();
    while (!queue.isEmpty()) {
        char c = queue.poll();
        order.append(c);
        for (char next : graph.get(c)) if (inDegree.merge(next, -1, Integer::sum) == 0) queue.add(next);
    }
    return order.length() == graph.size() ? order.toString() : "";
}
```

- **Time Complexity:** O(C)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force: Try Every Permutation of the Letters | O(L! * C) for L distinct letters | O(L) |
| 2 | DFS Topological Sort (Post-order) | O(C) | O(1) (at most 26 letters) |
| 3 | Iterative DFS Topological Sort | O(C + 26^2) | O(26^2) |
| 4 | Adjacency Matrix + Kahn with a Min-Heap | O(C + 26^2 + 26 log 26) | O(26^2) |
| 5 | Kahn's Topological Sort (Optimized) | O(C) | O(1) |

### Optimized Solution

The optimized solution is Approach 5 above, which is also the version implemented in this repository.

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
