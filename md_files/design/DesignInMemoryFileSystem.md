# Design In-Memory File System

Design an in-memory file system with `ls(path)` (if the path is a file, return a list with just its name; if a directory, return the names of its files and directories in lexicographic order), `mkdir(path)` (create a directory, with any missing intermediate directories), `addContentToFile(filePath, content)` (create the file if needed and append the content) and `readContentFromFile(filePath)` (return the file content).

## Algorithm Strategy (Trie of Path Segments)

1. Model the file system as a tree: each node has a name, a map of children (a `TreeMap` keeps names sorted) and, for files, a `StringBuilder` of content.
2. A helper walks a path from the root, creating directories when asked.
3. `ls` returns the single file name if the last node is a file, otherwise the sorted keys of its children.
4. `addContentToFile` creates the intermediate directories and the file, then appends; `readContentFromFile` returns the content.

## Example walkthrough

- `mkdir("/a/b/c")`, `addContentToFile("/a/b/c/d", "hello")`: `ls("/")` is `[a]`, and reading `/a/b/c/d` gives `hello`.
- `ls("/a/b/c/d")` on the file returns `[d]`.

## Complexity Analysis

- **Time Complexity:** `O(path length + children)` — One walk down the tree per operation; `ls` sorts via the `TreeMap`.
- **Space Complexity:** `O(total characters)` — The tree and the file contents.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Flat Map of Full Paths

Store every directory and file under its full path string. Listing a directory needs a scan of all keys.

```java
public static class Flat {
    private final java.util.Set<String> dirs = new java.util.TreeSet<>(); private final java.util.Map<String, StringBuilder> files = new java.util.HashMap<>(); public Flat() { dirs.add("/"); }
    private void ensure(String p) { String[] parts = p.split("/"); StringBuilder cur = new StringBuilder(); for (int i = 1; i < parts.length; i++) { cur.append("/").append(parts[i]); dirs.add(cur.toString()); } }
    public java.util.List<String> ls(String p) { java.util.List<String> out = new java.util.ArrayList<>(); if (files.containsKey(p)) { String[] a = p.split("/"); out.add(a[a.length - 1]); return out; }
        String prefix = p.equals("/") ? "/" : p + "/"; java.util.Set<String> names = new java.util.TreeSet<>(); for (String d : dirs) if (!d.equals(p) && d.startsWith(prefix)) names.add(d.substring(prefix.length()).split("/")[0]); for (String f : files.keySet()) if (f.startsWith(prefix)) names.add(f.substring(prefix.length()).split("/")[0]); out.addAll(names); return out; }
    public void mkdir(String p) { ensure(p); }
    public void add(String p, String c) { String[] parts = p.split("/"); StringBuilder dir = new StringBuilder(); for (int i = 1; i < parts.length - 1; i++) dir.append("/").append(parts[i]); if (dir.length() > 0) ensure(dir.toString()); files.computeIfAbsent(p, k -> new StringBuilder()).append(c); }
    public String read(String p) { StringBuilder b = files.get(p); return b == null ? "" : b.toString(); }
}
```

- **Time Complexity:** O(entries) per ls
- **Space Complexity:** O(entries)

### Approach 2: Trie of Path Segments (Optimized)

A tree of nodes with sorted children. This is the version implemented in the repository.

```java
public static class Tree {
    private static class N { java.util.Map<String, N> k = new java.util.TreeMap<>(); StringBuilder c; } private final N root = new N();
    private N walk(String p, boolean create) { N n = root; for (String s : p.split("/")) { if (s.isEmpty()) continue; N x = n.k.get(s); if (x == null) { if (!create) return new N(); x = new N(); n.k.put(s, x); } n = x; } return n; }
    public java.util.List<String> ls(String p) { N n = walk(p, false); java.util.List<String> out = new java.util.ArrayList<>(); if (n.c != null) { String[] a = p.split("/"); out.add(a[a.length - 1]); } else out.addAll(n.k.keySet()); return out; }
    public void mkdir(String p) { walk(p, true); }
    public void add(String p, String c) { N f = walk(p, true); if (f.c == null) f.c = new StringBuilder(); f.c.append(c); }
    public String read(String p) { N f = walk(p, false); return f.c == null ? "" : f.c.toString(); }
}
```

- **Time Complexity:** O(path length + children)
- **Space Complexity:** O(total characters)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Flat Map of Full Paths | O(entries) per ls | O(entries) |
| 2 | Trie of Path Segments (Optimized) | O(path length + children) | O(total characters) |

## Source Code

### DesignInMemoryFileSystem.java

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DesignInMemoryFileSystem {

    public static void main(String[] args) {
        DesignInMemoryFileSystem fs = new DesignInMemoryFileSystem();
        System.out.println("Input  : ls(\"/\")");
        System.out.println("Output : " + fs.ls("/"));
        System.out.println();
        fs.mkdir("/a/b/c");
        fs.addContentToFile("/a/b/c/d", "hello");
        System.out.println("Input  : mkdir(\"/a/b/c\"), addContentToFile(\"/a/b/c/d\", \"hello\"), ls(\"/\"), readContentFromFile(\"/a/b/c/d\")");
        System.out.println("Output : " + fs.ls("/") + ", " + fs.readContentFromFile("/a/b/c/d"));
        System.out.println();
        fs.addContentToFile("/a/b/c/d", " world");
        System.out.println("Input  : addContentToFile(\"/a/b/c/d\", \" world\"), ls(\"/a/b/c\"), ls(\"/a/b/c/d\"), readContentFromFile(\"/a/b/c/d\")");
        System.out.println("Output : " + fs.ls("/a/b/c") + ", " + fs.ls("/a/b/c/d") + ", " + fs.readContentFromFile("/a/b/c/d"));
    }

    private static class Node {
        private final Map<String, Node> children = new TreeMap<>();
        private StringBuilder content;
    }

    private final Node root = new Node();

    public List<String> ls(String path) {
        Node node = walk(path, false);
        List<String> result = new ArrayList<>();
        if (node.content != null) {
            String[] parts = path.split("/");
            result.add(parts[parts.length - 1]);
        } else {
            result.addAll(node.children.keySet());
        }
        return result;
    }

    public void mkdir(String path) {
        walk(path, true);
    }

    public void addContentToFile(String filePath, String content) {
        Node file = walk(filePath, true);
        if (file.content == null) {
            file.content = new StringBuilder();
        }
        file.content.append(content);
    }

    public String readContentFromFile(String filePath) {
        Node file = walk(filePath, false);
        return file.content == null ? "" : file.content.toString();
    }

    private Node walk(String path, boolean create) {
        Node node = root;
        for (String part : path.split("/")) {
            if (part.isEmpty()) {
                continue;
            }
            Node next = node.children.get(part);
            if (next == null) {
                if (!create) {
                    return new Node();
                }
                next = new Node();
                node.children.put(part, next);
            }
            node = next;
        }
        return node;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: paths are hierarchical, so the natural structure is a tree with one node per directory or file. Keeping the children in a sorted map makes `ls` return names in lexicographic order for free.

**Sequence:**

| Call | Tree afterwards | Returns |
|------|-----------------|---------|
| `ls("/")` | (empty root) | `[]` |
| `mkdir("/a/b/c")` | a -> b -> c | |
| `addContentToFile("/a/b/c/d", "hello")` | c holds file d = "hello" | |
| `ls("/")` | | `[a]` |
| `readContentFromFile("/a/b/c/d")` | | `hello` |
| `ls("/a/b/c/d")` (a file) | | `[d]` |

**First output in `main`:** `[]`.
