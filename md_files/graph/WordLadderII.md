# Word Ladder II

A transformation sequence from `beginWord` to `endWord` using a dictionary `wordList` is a sequence of words where consecutive words differ by exactly one letter and every word after `beginWord` is in the dictionary. Return all the shortest transformation sequences from `beginWord` to `endWord`, or an empty list if there is none.

## Algorithm Strategy (BFS Levels then Path Reconstruction)

1. Run a BFS from `beginWord`, changing one letter at a time to words in the dictionary, recording each word's level (distance from the start) and, for every word, the list of parents on shortest paths.
2. Only add a parent edge when the neighbour is on the next level (`level[next] == level[word] + 1`).
3. Stop the BFS once the level containing `endWord` is finished.
4. Run a DFS from `endWord` back to `beginWord` through the parent lists, reverse each path, and return them all.

## Example walkthrough

- For `hit` to `cog` with `[hot, dot, dog, lot, log, cog]`: two shortest paths of five words exist.
- Result: `[[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]`.

## Complexity Analysis

- **Time Complexity:** `O(n * L * 26 + paths)` — BFS over the dictionary plus the size of the output.
- **Space Complexity:** `O(n * L)` — Levels and parent lists.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS over All Paths then Keep the Shortest

Enumerate every transformation path and keep only the shortest ones. Exponential.

```java
public static java.util.List<java.util.List<String>> allPaths(String b, String e, java.util.List<String> list) {
    java.util.List<java.util.List<String>> out = new java.util.ArrayList<>(); java.util.Set<String> w = new java.util.HashSet<>(list); if (!w.contains(e)) return out; java.util.List<String> path = new java.util.ArrayList<>(); path.add(b); go(b, e, w, path, out, new java.util.HashSet<>(java.util.Collections.singleton(b)));
    int min = Integer.MAX_VALUE; for (java.util.List<String> p : out) min = Math.min(min, p.size()); java.util.List<java.util.List<String>> res = new java.util.ArrayList<>(); for (java.util.List<String> p : out) if (p.size() == min) res.add(p); return res;
}
private static void go(String cur, String e, java.util.Set<String> w, java.util.List<String> path, java.util.List<java.util.List<String>> out, java.util.Set<String> seen) {
    if (cur.equals(e)) { out.add(new java.util.ArrayList<>(path)); return; }
    char[] ch = cur.toCharArray(); for (int i = 0; i < ch.length; i++) { char o = ch[i]; for (char c = 'a'; c <= 'z'; c++) { if (c == o) continue; ch[i] = c; String n = new String(ch); if (w.contains(n) && seen.add(n)) { path.add(n); go(n, e, w, path, out, seen); path.remove(path.size() - 1); seen.remove(n); } } ch[i] = o; }
}
```

- **Time Complexity:** exponential
- **Space Complexity:** O(n)

### Approach 2: BFS for Parents then DFS Back (Optimized)

BFS records shortest-path parents; a DFS from the end word rebuilds every path. This is the version implemented in the repository.

```java
public static java.util.List<java.util.List<String>> bfsDfs(String b, String e, java.util.List<String> list) {
    java.util.List<java.util.List<String>> out = new java.util.ArrayList<>(); java.util.Set<String> w = new java.util.HashSet<>(list); if (!w.contains(e)) return out;
    java.util.Map<String, Integer> lvl = new java.util.HashMap<>(); java.util.Map<String, java.util.List<String>> par = new java.util.HashMap<>(); java.util.Queue<String> q = new java.util.ArrayDeque<>(); q.add(b); lvl.put(b, 0); boolean found = false;
    while (!q.isEmpty() && !found) { for (int z = q.size(); z > 0; z--) { String x = q.poll(); char[] ch = x.toCharArray(); for (int i = 0; i < ch.length; i++) { char o = ch[i]; for (char c = 'a'; c <= 'z'; c++) { if (c == o) continue; ch[i] = c; String n = new String(ch); if (!w.contains(n)) continue; int nl = lvl.get(x) + 1; if (!lvl.containsKey(n)) { lvl.put(n, nl); q.add(n); } if (lvl.get(n) == nl) par.computeIfAbsent(n, k -> new java.util.ArrayList<>()).add(x); if (n.equals(e)) found = true; } ch[i] = o; } } }
    if (found) { java.util.LinkedList<String> p = new java.util.LinkedList<>(); p.add(e); back(e, b, par, p, out); } return out;
}
private static void back(String w, String b, java.util.Map<String, java.util.List<String>> par, java.util.LinkedList<String> p, java.util.List<java.util.List<String>> out) {
    if (w.equals(b)) { out.add(new java.util.ArrayList<>(p)); return; } for (String x : par.getOrDefault(w, new java.util.ArrayList<>())) { p.addFirst(x); back(x, b, par, p, out); p.removeFirst(); }
}
```

- **Time Complexity:** O(n * L * 26 + paths)
- **Space Complexity:** O(n * L)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS over All Paths then Keep the Shortest | exponential | O(n) |
| 2 | BFS for Parents then DFS Back (Optimized) | O(n * L * 26 + paths) | O(n * L) |

## Source Code

### WordLadderII.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordLadderII {

    public static void main(String[] args) {
        String[] begins = {"hit", "hit", "a"};
        String[] ends = {"cog", "cog", "c"};
        String[][] lists = {{"hot", "dot", "dog", "lot", "log", "cog"}, {"hot", "dot", "dog", "lot", "log"}, {"a", "b", "c"}};
        for (int i = 0; i < begins.length; i++) {
            List<List<String>> result = findLadders(begins[i], ends[i], Arrays.asList(lists[i]));
            for (List<String> path : result) {
                Collections.sort(path.subList(0, 0));
            }
            result.sort((a, b) -> a.toString().compareTo(b.toString()));
            System.out.println("Input  : beginWord = \"" + begins[i] + "\", endWord = \"" + ends[i] + "\", wordList = " + Arrays.toString(lists[i]));
            System.out.println("Output : " + result);
            System.out.println();
        }
    }

    public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> words = new HashSet<>(wordList);
        if (!words.contains(endWord)) {
            return result;
        }
        Map<String, Integer> level = new HashMap<>();
        Map<String, List<String>> parents = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        level.put(beginWord, 0);
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            for (int size = queue.size(); size > 0; size--) {
                String word = queue.poll();
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) {
                            continue;
                        }
                        chars[i] = c;
                        String next = new String(chars);
                        if (!words.contains(next)) {
                            continue;
                        }
                        int nextLevel = level.get(word) + 1;
                        if (!level.containsKey(next)) {
                            level.put(next, nextLevel);
                            queue.add(next);
                        }
                        if (level.get(next) == nextLevel) {
                            parents.computeIfAbsent(next, k -> new ArrayList<>()).add(word);
                        }
                        if (next.equals(endWord)) {
                            found = true;
                        }
                    }
                    chars[i] = original;
                }
            }
        }
        if (found) {
            LinkedList<String> path = new LinkedList<>();
            path.add(endWord);
            collect(endWord, beginWord, parents, path, result);
        }
        return result;
    }

    private static void collect(String word, String begin, Map<String, List<String>> parents, LinkedList<String> path, List<List<String>> result) {
        if (word.equals(begin)) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (String parent : parents.getOrDefault(word, new ArrayList<>())) {
            path.addFirst(parent);
            collect(parent, begin, parents, path, result);
            path.removeFirst();
        }
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a plain DFS would explore far too many paths. A BFS gives every word its shortest distance and records only the parent edges that lie on shortest paths, so rebuilding the paths from the end word backward visits only useful words.

**Input:** `beginWord = "hit"`, `endWord = "cog"`, `wordList = [hot, dot, dog, lot, log, cog]`.

| Level | Words | Parents recorded |
|-------|-------|-------------------|
| 0 | hit | |
| 1 | hot | hot <- hit |
| 2 | dot, lot | dot <- hot, lot <- hot |
| 3 | dog, log | dog <- dot, log <- lot |
| 4 | cog | cog <- dog, cog <- log |

Walking back from `cog` through the parents gives the two paths.

**Output:** `[[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]`.
