# Edit Distance

Given two strings `word1` and `word2`, return the minimum number of operations needed to convert `word1` into `word2`. The allowed operations are inserting a character, deleting a character and replacing a character.

## Algorithm Strategy (DP over Prefixes (Levenshtein Distance))

1. Let `dp[i][j]` be the edit distance between the first `i` characters of `word1` and the first `j` characters of `word2`.
2. Base cases: converting a prefix to the empty string needs `i` deletions, and building a prefix from the empty string needs `j` insertions.
3. If the last characters are equal, no operation is needed for them: `dp[i][j] = dp[i-1][j-1]`.
4. Otherwise take the cheapest of replace (`dp[i-1][j-1]`), delete (`dp[i-1][j]`) and insert (`dp[i][j-1]`) plus 1. Only the previous row is needed, so two arrays are enough.

## Example walkthrough

- For `"horse"` to `"ros"`: replace h with r, delete r, delete e.
- Result: `3`. For `"intention"` to `"execution"` the distance is `5`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every pair of prefixes is examined once.
- **Space Complexity:** `O(n)` — Two rows of the table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Compare the last characters: equal ones are free, otherwise try replace, delete and insert.

```java
public static int minDistanceRecursive(String a, String b) {
    return go(a, b, a.length(), b.length());
}

private static int go(String a, String b, int i, int j) {
    if (i == 0) return j;
    if (j == 0) return i;
    if (a.charAt(i - 1) == b.charAt(j - 1)) return go(a, b, i - 1, j - 1);
    return 1 + Math.min(go(a, b, i - 1, j - 1), Math.min(go(a, b, i - 1, j), go(a, b, i, j - 1)));
}
```

- **Time Complexity:** O(3^(m + n))
- **Space Complexity:** O(m + n)

### Approach 2: Top-Down Memoization

Cache the distance of each pair of prefixes.

```java
public static int minDistanceMemo(String a, String b) {
    int[][] memo = new int[a.length() + 1][b.length() + 1];
    for (int[] row : memo) Arrays.fill(row, -1);
    return goMemo(a, b, a.length(), b.length(), memo);
}

private static int goMemo(String a, String b, int i, int j, int[][] memo) {
    if (i == 0) return j;
    if (j == 0) return i;
    if (memo[i][j] >= 0) return memo[i][j];
    int result;
    if (a.charAt(i - 1) == b.charAt(j - 1)) result = goMemo(a, b, i - 1, j - 1, memo);
    else result = 1 + Math.min(goMemo(a, b, i - 1, j - 1, memo), Math.min(goMemo(a, b, i - 1, j, memo), goMemo(a, b, i, j - 1, memo)));
    return memo[i][j] = result;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Full 2D Table

Fill the whole Levenshtein table bottom-up.

```java
public static int minDistanceTable(String a, String b) {
    int m = a.length(), n = b.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? dp[i - 1][j - 1]
                    : 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
    return dp[m][n];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 4: Two Rows (Optimized)

Row `i` only needs row `i - 1`, so two arrays are enough. This is the version implemented in the repository.

```java
public static int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[] previous = new int[n + 1], current = new int[n + 1];
    for (int j = 0; j <= n; j++) previous[j] = j;
    for (int i = 1; i <= m; i++) {
        current[0] = i;
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i - 1) == word2.charAt(j - 1)) current[j] = previous[j - 1];
            else current[j] = 1 + Math.min(previous[j - 1], Math.min(previous[j], current[j - 1]));
        }
        int[] temp = previous; previous = current; current = temp;
    }
    return previous[n];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(3^(m + n)) | O(m + n) |
| 2 | Top-Down Memoization | O(m * n) | O(m * n) |
| 3 | Full 2D Table | O(m * n) | O(m * n) |
| 4 | Two Rows (Optimized) | O(m * n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### EditDistance.java

```java
public class EditDistance {

    public static void main(String[] args) {
        String[][] inputs = {{"horse", "ros"}, {"intention", "execution"}, {"", "abc"}, {"abc", ""}, {"same", "same"}};
        for (String[] input : inputs) {
            System.out.println("Input  : word1 = \"" + input[0] + "\", word2 = \"" + input[1] + "\"");
            System.out.println("Output : " + minDistance(input[0], input[1]));
            System.out.println();
        }
    }

    public static int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[] previous = new int[n + 1];
        int[] current = new int[n + 1];
        for (int j = 0; j <= n; j++) {
            previous[j] = j;                        // insert j characters
        }

        for (int i = 1; i <= m; i++) {
            current[0] = i;                         // delete i characters
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {
                    current[j] = 1 + Math.min(previous[j - 1], Math.min(previous[j], current[j - 1]));
                }
            }
            int[] temp = previous;
            previous = current;
            current = temp;
        }
        return previous[n];
    }
}
```
