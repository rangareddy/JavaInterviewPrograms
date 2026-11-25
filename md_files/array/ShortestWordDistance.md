# Shortest Word Distance

Given an array of strings `wordsDict` and two different strings `word1` and `word2` that already exist in the array, return the shortest distance between the two words in the list.

## Algorithm Strategy (One Pass with the Last Seen Indexes)

1. Keep `index1` and `index2`, the last positions where `word1` and `word2` were seen (start at -1).
2. Walk the array; whenever a word matches, update its index.
3. If both indexes are set, the distance `|index1 - index2|` is a candidate for the answer.
4. Return the smallest candidate.

## Example walkthrough

- For `[practice, makes, perfect, coding, makes]`, `word1 = coding`, `word2 = practice`: `practice` at 0 and `coding` at 3.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Compare Every Pair of Positions

Collect all positions of both words and compare every pair.

```java
public static int pairs(String[] w, String a, String b) { int best = Integer.MAX_VALUE; for (int i = 0; i < w.length; i++) if (w[i].equals(a)) for (int j = 0; j < w.length; j++) if (w[j].equals(b)) best = Math.min(best, Math.abs(i - j)); return best == Integer.MAX_VALUE ? -1 : best; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Last Seen Indexes (Optimized)

Update the last position of each word and compare. This is the version implemented in the repository.

```java
public static int last(String[] w, String a, String b) { int i1 = -1, i2 = -1, best = Integer.MAX_VALUE; for (int i = 0; i < w.length; i++) { if (w[i].equals(a)) i1 = i; else if (w[i].equals(b)) i2 = i; if (i1 != -1 && i2 != -1) best = Math.min(best, Math.abs(i1 - i2)); } return best == Integer.MAX_VALUE ? -1 : best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Compare Every Pair of Positions | O(n^2) | O(1) |
| 2 | Last Seen Indexes (Optimized) | O(n) | O(1) |

## Source Code

### ShortestWordDistance.java

```java
import java.util.Arrays;

public class ShortestWordDistance {

    public static void main(String[] args) {
        String[][] words = {{"practice", "makes", "perfect", "coding", "makes"}, {"practice", "makes", "perfect", "coding", "makes"}, {"a", "b"}};
        String[] first = {"coding", "makes", "a"};
        String[] second = {"practice", "coding", "b"};
        for (int i = 0; i < words.length; i++) {
            System.out.println("Input  : wordsDict = " + Arrays.toString(words[i]) + ", word1 = \"" + first[i] + "\", word2 = \"" + second[i] + "\"");
            System.out.println("Output : " + shortestDistance(words[i], first[i], second[i]));
            System.out.println();
        }
    }

    public static int shortestDistance(String[] wordsDict, String word1, String word2) {
        if (wordsDict == null || word1 == null || word2 == null) {
            return -1;
        }
        int index1 = -1;
        int index2 = -1;
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < wordsDict.length; i++) {
            if (wordsDict[i].equals(word1)) {
                index1 = i;
            } else if (wordsDict[i].equals(word2)) {
                index2 = i;
            }
            if (index1 != -1 && index2 != -1) {
                best = Math.min(best, Math.abs(index1 - index2));
            }
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the closest pair of occurrences always involves, for one of the words, its most recent occurrence at the time the other word is seen. So remembering only the last index of each word is enough, and every pair worth comparing is checked in one pass.

**Input:** `wordsDict = [practice, makes, perfect, coding, makes]`, `word1 = coding`, `word2 = practice`.

| `i` | Word | `index1` (coding) | `index2` (practice) | Candidate |
|-----|------|-------------------|---------------------|-----------|
| 0 | practice | -1 | 0 | |
| 3 | coding | 3 | 0 | 3 |

**Output:** `3`.
