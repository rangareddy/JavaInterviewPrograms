# Isomorphic Strings

Two strings `s` and `t` are isomorphic if the characters of `s` can be replaced to get `t`, where every character maps to exactly one character and no two characters map to the same one (the order is preserved). Return `true` if they are isomorphic.

## Algorithm Strategy (Two-Way Character Mapping)

1. If the lengths differ the strings cannot be isomorphic.
2. Keep two maps (arrays work for ASCII): `s -> t` and `t -> s`.
3. For every position `i`, look at `a = s[i]` and `b = t[i]`. If `a` is already mapped to something other than `b`, or `b` is already the image of something other than `a`, return `false`.
4. Otherwise record both mappings. Reaching the end means the strings are isomorphic.

## Example walkthrough

- `"egg"` / `"add"`: `e->a`, `g->d`, `g->d` (consistent): `true`.
- `"foo"` / `"bar"`: `o` would have to map to both `a` and `r`: `false`; `"badc"` / `"baba"`: `b` and `d` would both map to `b`: `false`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two arrays of fixed size (256 characters).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Pattern Normalisation

Replace every character by the index of its first occurrence; isomorphic strings have the same pattern.

```java
public static boolean isIsomorphicPattern(String s, String t) {
    return pattern(s).equals(pattern(t));
}

private static List<Integer> pattern(String s) {
    List<Integer> result = new ArrayList<>();
    Map<Character, Integer> firstSeen = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
        firstSeen.putIfAbsent(s.charAt(i), i);
        result.add(firstSeen.get(s.charAt(i)));
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Map plus Set of Used Targets

One map from `s` to `t` and a set of the characters of `t` that are already taken.

```java
public static boolean isIsomorphicMapSet(String s, String t) {
    if (s.length() != t.length()) return false;
    Map<Character, Character> map = new HashMap<>();
    Set<Character> used = new HashSet<>();
    for (int i = 0; i < s.length(); i++) {
        char a = s.charAt(i), b = t.charAt(i);
        if (map.containsKey(a)) {
            if (map.get(a) != b) return false;
        } else {
            if (!used.add(b)) return false;
            map.put(a, b);
        }
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(k)

### Approach 3: Last-Seen Index Arrays

Two characters are consistent only if they were last seen at the same position.

```java
public static boolean isIsomorphicLastSeen(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] lastS = new int[256], lastT = new int[256];
    for (int i = 0; i < s.length(); i++) {
        if (lastS[s.charAt(i)] != lastT[t.charAt(i)]) return false;
        lastS[s.charAt(i)] = i + 1;
        lastT[t.charAt(i)] = i + 1;
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Two-Way Mapping Arrays (Optimized)

Maintain `s -> t` and `t -> s` and fail on any conflict. This is the version implemented in the repository.

```java
public static boolean isIsomorphic(String s, String t) {
    if (s.length() != t.length()) return false;
    char[] sToT = new char[256], tToS = new char[256];
    for (int i = 0; i < s.length(); i++) {
        char a = s.charAt(i), b = t.charAt(i);
        if ((sToT[a] != 0 && sToT[a] != b) || (tToS[b] != 0 && tToS[b] != a)) return false;
        sToT[a] = b;
        tToS[b] = a;
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Pattern Normalisation | O(n) | O(n) |
| 2 | Map plus Set of Used Targets | O(n) | O(k) |
| 3 | Last-Seen Index Arrays | O(n) | O(1) |
| 4 | Two-Way Mapping Arrays (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### IsomorphicStrings.java

```java
public class IsomorphicStrings {

    public static void main(String[] args) {
        String[][] inputs = {{"egg", "add"}, {"foo", "bar"}, {"paper", "title"}, {"badc", "baba"}, {"", ""}, {"ab", "aa"}};
        for (String[] pair : inputs) {
            System.out.println("Input  : s = \"" + pair[0] + "\", t = \"" + pair[1] + "\"");
            System.out.println("Output : " + isIsomorphic(pair[0], pair[1]));
            System.out.println();
        }
    }

    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] sToT = new char[256];
        char[] tToS = new char[256];
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);
            if ((sToT[a] != 0 && sToT[a] != b) || (tToS[b] != 0 && tToS[b] != a)) {
                return false;
            }
            sToT[a] = b;
            tToS[b] = a;
        }
        return true;
    }
}
```
