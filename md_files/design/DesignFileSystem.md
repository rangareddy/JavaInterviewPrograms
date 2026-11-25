# Design File System

Design a file system that lets you create new paths and associate them with values. `createPath(path, value)` creates a path and stores its value, returning `true`; it returns `false` if the path already exists or if its parent path does not exist. `get(path)` returns the value of a path, or `-1` if it does not exist. A path is a string like `/a` or `/a/b`.

## Algorithm Strategy (Map from Full Path to Value)

1. Store every created path in a `HashMap<String, Integer>`.
2. In `createPath`, reject empty or root paths and paths that already exist.
3. Compute the parent path as the substring before the last `/`; the parent must exist unless it is the root (the empty string).
4. Insert the path and return `true`. `get` is a plain map lookup with a default of -1.

## Example walkthrough

- `createPath("/a", 1)` succeeds. `createPath("/a/b", 2)` succeeds because `/a` exists.
- `createPath("/c/d", 1)` fails because `/c` does not exist.

## Complexity Analysis

- **Time Complexity:** `O(L)` — Where `L` is the length of the path (hashing and the parent lookup).
- **Space Complexity:** `O(total path length)` — The map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Trie of Path Segments

Walk a tree of segments; each node stores its value. More structure, same behaviour.

```java
public static class Trie {
    private static class N { java.util.Map<String, N> kids = new java.util.HashMap<>(); int val = -1; boolean has; }
    private final N root = new N();
    public boolean createPath(String path, int value) {
        String[] parts = path.split("/"); N n = root;
        for (int i = 1; i < parts.length - 1; i++) { n = n.kids.get(parts[i]); if (n == null || !n.has) return false; }
        String last = parts[parts.length - 1]; if (n.kids.containsKey(last) && n.kids.get(last).has) return false;
        N c = n.kids.computeIfAbsent(last, k -> new N()); c.has = true; c.val = value; return true;
    }
    public int get(String path) { N n = root; for (String p : path.split("/")) { if (p.isEmpty()) continue; n = n.kids.get(p); if (n == null || !n.has) return -1; } return n.val; }
}
```

- **Time Complexity:** O(depth)
- **Space Complexity:** O(total segments)

### Approach 2: HashMap of Full Paths (Optimized)

Store each full path as a key. This is the version implemented in the repository.

```java
public static class Flat {
    private final java.util.Map<String, Integer> m = new java.util.HashMap<>();
    public boolean createPath(String p, int v) {
        if (p.length() < 2 || m.containsKey(p)) return false; String parent = p.substring(0, p.lastIndexOf('/'));
        if (!parent.isEmpty() && !m.containsKey(parent)) return false; m.put(p, v); return true;
    }
    public int get(String p) { return m.getOrDefault(p, -1); }
}
```

- **Time Complexity:** O(L)
- **Space Complexity:** O(total path length)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Trie of Path Segments | O(depth) | O(total segments) |
| 2 | HashMap of Full Paths (Optimized) | O(L) | O(total path length) |

## Source Code

### DesignFileSystem.java

```java
import java.util.HashMap;
import java.util.Map;

public class DesignFileSystem {

    public static void main(String[] args) {
        DesignFileSystem fs = new DesignFileSystem();
        System.out.println("Input  : createPath(\"/a\", 1)");
        System.out.println("Output : " + fs.createPath("/a", 1));
        System.out.println();
        System.out.println("Input  : createPath(\"/a/b\", 2), get(\"/a/b\")");
        System.out.println("Output : " + fs.createPath("/a/b", 2) + ", " + fs.get("/a/b"));
        System.out.println();
        System.out.println("Input  : createPath(\"/c/d\", 1), createPath(\"/a\", 5), get(\"/c\")");
        System.out.println("Output : " + fs.createPath("/c/d", 1) + ", " + fs.createPath("/a", 5) + ", " + fs.get("/c"));
    }

    private final Map<String, Integer> paths = new HashMap<>();

    public boolean createPath(String path, int value) {
        if (path == null || path.length() < 2 || path.charAt(0) != '/' || paths.containsKey(path)) {
            return false;
        }
        int last = path.lastIndexOf('/');
        String parent = path.substring(0, last);
        if (!parent.isEmpty() && !paths.containsKey(parent)) {
            return false;
        }
        paths.put(path, value);
        return true;
    }

    public int get(String path) {
        return paths.getOrDefault(path, -1);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: because a path's validity depends only on its parent existing, a flat map keyed by the full path is enough. The parent is just the text before the last `/`.

**Sequence:**

| Call | Parent | Result |
|------|--------|--------|
| `createPath("/a", 1)` | (root) | true |
| `createPath("/a/b", 2)` | `/a` exists | true |
| `get("/a/b")` | | 2 |
| `createPath("/c/d", 1)` | `/c` missing | false |
| `createPath("/a", 5)` | already exists | false |
| `get("/c")` | | -1 |

**First output in `main`:** `true`.
