# Rank Teams by Votes

In a ranking system, every voter ranks all teams from first to last. Teams are ordered by the number of first-place votes; ties are broken by second-place votes, and so on. If teams are tied in every position, they are ordered alphabetically. Given an array of vote strings, return the final ranking as a string.

## Algorithm Strategy (Count Votes per Position and Sort)

1. For each team letter, keep an array `count[team][position]` of how many times it was placed in each position.
2. Fill it by walking every vote and every character position.
3. Sort the teams with a comparator that compares the counts from position 0 onward (more votes first), falling back to the letter.
4. Join the sorted letters.

## Example walkthrough

- For votes `ABC, ACB, ABC, ACB, ACB`: A is first 5 times; C is second 3 times and B second 2 times.
- Result: `ACB`.

## Complexity Analysis

- **Time Complexity:** `O(V * T + T^2 log T)` — `V` votes of `T` teams, then a sort comparing up to `T` positions.
- **Space Complexity:** `O(T^2)` — The count matrix.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort by Strings of Counts

Build a padded string of counts for each team and compare the strings.

```java
public static String strings(String[] votes) {
    int n = votes[0].length(); int[][] c = new int[26][n]; for (String v : votes) for (int i = 0; i < n; i++) c[v.charAt(i) - 'A'][i]++;
    java.util.List<Character> l = new java.util.ArrayList<>(); for (char ch : votes[0].toCharArray()) l.add(ch);
    l.sort((a, b) -> { StringBuilder x = new StringBuilder(), y = new StringBuilder(); for (int i = 0; i < n; i++) { x.append(String.format("%05d", 99999 - c[a - 'A'][i])); y.append(String.format("%05d", 99999 - c[b - 'A'][i])); } int d = x.toString().compareTo(y.toString()); return d != 0 ? d : a - b; });
    StringBuilder sb = new StringBuilder(); for (char ch : l) sb.append(ch); return sb.toString();
}
```

- **Time Complexity:** O(T^2 log T)
- **Space Complexity:** O(T^2)

### Approach 2: Position-Count Matrix with a Comparator (Optimized)

Compare the vote counts position by position. This is the version implemented in the repository.

```java
public static String matrix(String[] votes) {
    int n = votes[0].length(); int[][] c = new int[26][n]; for (String v : votes) for (int i = 0; i < n; i++) c[v.charAt(i) - 'A'][i]++;
    Character[] o = new Character[n]; for (int i = 0; i < n; i++) o[i] = votes[0].charAt(i);
    java.util.Arrays.sort(o, (a, b) -> { for (int i = 0; i < n; i++) { int d = c[b - 'A'][i] - c[a - 'A'][i]; if (d != 0) return d; } return a - b; });
    StringBuilder sb = new StringBuilder(); for (char ch : o) sb.append(ch); return sb.toString();
}
```

- **Time Complexity:** O(V * T + T^2 log T)
- **Space Complexity:** O(T^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort by Strings of Counts | O(T^2 log T) | O(T^2) |
| 2 | Position-Count Matrix with a Comparator (Optimized) | O(V * T + T^2 log T) | O(T^2) |

## Source Code

### RankTeamsByVotes.java

```java
import java.util.Arrays;

public class RankTeamsByVotes {

    public static void main(String[] args) {
        String[][] inputs = {{"ABC", "ACB", "ABC", "ACB", "ACB"}, {"WXYZ", "XYZW"}, {"ZMNAGUEDSJYLBOPHRQICWFXTVK"}, {"BCA", "CAB", "CBA", "ABC", "ACB", "BAC"}};
        for (String[] votes : inputs) {
            System.out.println("Input  : votes = " + Arrays.toString(votes));
            System.out.println("Output : " + rankTeams(votes));
            System.out.println();
        }
    }

    public static String rankTeams(String[] votes) {
        if (votes == null || votes.length == 0) {
            return "";
        }
        int teams = votes[0].length();
        int[][] count = new int[26][teams];
        for (String vote : votes) {
            for (int position = 0; position < teams; position++) {
                count[vote.charAt(position) - 'A'][position]++;
            }
        }
        Character[] order = new Character[teams];
        for (int i = 0; i < teams; i++) {
            order[i] = votes[0].charAt(i);
        }
        Arrays.sort(order, (a, b) -> {
            for (int position = 0; position < teams; position++) {
                int diff = count[b - 'A'][position] - count[a - 'A'][position];
                if (diff != 0) {
                    return diff;
                }
            }
            return a - b;
        });
        StringBuilder result = new StringBuilder();
        for (char team : order) {
            result.append(team);
        }
        return result.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the ranking rule is a lexicographic comparison of the vote counts position by position, so each team is described by a vector of counts and teams are sorted by their vectors (largest first). The letter breaks the remaining ties.

**Input:** `votes = [ABC, ACB, ABC, ACB, ACB]`.

| Team | 1st | 2nd | 3rd |
|------|-----|-----|-----|
| A | 5 | 0 | 0 |
| B | 0 | 2 | 3 |
| C | 0 | 3 | 2 |

A wins on first-place votes. B and C tie on first place, and C has more second-place votes.

**Output:** `ACB`.
