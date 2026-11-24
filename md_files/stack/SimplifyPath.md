# Simplify Path

Given an absolute Unix-style path, convert it to its canonical form: it starts with a single slash, directories are separated by one slash, there is no trailing slash, `.` is dropped, `..` goes up one directory (never above the root), and repeated slashes are treated as one.

## Algorithm Strategy (Segment Stack)

1. Split the path on `/` so that empty segments (from repeated slashes) and `.` can be ignored.
2. For a `..` segment, pop the stack if it is not empty. Any other non-empty segment is pushed.
3. After processing every segment, the stack holds the canonical directory names from the root down.
4. Join them with `/` behind a leading `/`; an empty stack is just `/`.

## Example walkthrough

- For `/a/./b/../../c/`: push a, ignore `.`, push b, pop b, pop a, push c.
- Result: `/c`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is processed once.
- **Space Complexity:** `O(n)` — The stack of directory names.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated String Replacement

Keep replacing `//`, `/./` and `dir/..` patterns until nothing changes. Hard to get right at the edges.

```java
public static String replaceLoop(String p) {
    String prev;
    p = p + "/";
    do {
        prev = p;
        p = p.replace("//", "/").replace("/./", "/").replaceAll("/[^/]+/\\.\\./", "/");
        while (p.startsWith("/../")) p = p.substring(3);
    } while (!p.equals(prev));
    if (p.length() > 1 && p.endsWith("/")) p = p.substring(0, p.length() - 1);
    return p;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Stack of Segments with split (Optimized)

Split on `/` and use a deque as a stack of directory names. This is the version implemented in the repository.

```java
public static String stackSplit(String p) {
    java.util.Deque<String> st = new java.util.ArrayDeque<>();
    for (String s : p.split("/")) {
        if (s.isEmpty() || s.equals(".")) continue;
        if (s.equals("..")) { if (!st.isEmpty()) st.removeLast(); } else st.addLast(s);
    }
    if (st.isEmpty()) return "/";
    StringBuilder sb = new StringBuilder();
    for (String d : st) sb.append('/').append(d);
    return sb.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Manual Scan without split

Scan the characters and cut the segments by hand.

```java
public static String manual(String p) {
    java.util.List<String> st = new java.util.ArrayList<>();
    int i = 0, n = p.length();
    while (i < n) {
        while (i < n && p.charAt(i) == '/') i++;
        int j = i; while (j < n && p.charAt(j) != '/') j++;
        String s = p.substring(i, j); i = j;
        if (s.isEmpty() || s.equals(".")) continue;
        if (s.equals("..")) { if (!st.isEmpty()) st.remove(st.size() - 1); } else st.add(s);
    }
    return "/" + String.join("/", st);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated String Replacement | O(n^2) | O(n) |
| 2 | Stack of Segments with split (Optimized) | O(n) | O(n) |
| 3 | Manual Scan without split | O(n) | O(n) |

## Source Code

### SimplifyPath.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class SimplifyPath {

    public static void main(String[] args) {
        String[] inputs = {"/home/", "/home//foo/", "/a/./b/../../c/", "/../", "/a/../../b/../c//.//", "/..."};
        for (String path : inputs) {
            System.out.println("Input  : path = \"" + path + "\"");
            System.out.println("Output : \"" + simplifyPath(path) + "\"");
            System.out.println();
        }
    }

    public static String simplifyPath(String path) {
        if (path == null || path.isEmpty()) {
            return "/";
        }
        Deque<String> stack = new ArrayDeque<>();
        for (String part : path.split("/")) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }
            if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.removeLast();
                }
            } else {
                stack.addLast(part);
            }
        }
        if (stack.isEmpty()) {
            return "/";
        }
        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append('/').append(dir);
        }
        return result.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: `..` undoes the last directory, which is exactly a stack pop. Empty segments and `.` change nothing and are skipped.

**Input:** `/a/./b/../../c/`.

| Segment | Action | Stack |
|---------|--------|-------|
| a | push | [a] |
| . | skip | [a] |
| b | push | [a, b] |
| .. | pop | [a] |
| .. | pop | [] |
| c | push | [c] |

**Output:** `"/c"`.
