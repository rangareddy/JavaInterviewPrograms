package com.ranga.design;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a file system that lets you create new paths and associate them with values. {@code createPath(path, value)} creates a path and stores its value, returning {@code true}; it returns {@code false} if the path already exists or if its parent path does not exist. {@code get(path)} returns the value of a path, or {@code -1} if it does not exist. A path is a string like {@code /a} or {@code /a/b}.
 *
 * <p><b>Algorithm Strategy (Map from Full Path to Value):</b>
 * <ol>
 *   <li>Store every created path in a {@code HashMap<String, Integer>}.</li>
 *   <li>In {@code createPath}, reject empty or root paths and paths that already exist.</li>
 *   <li>Compute the parent path as the substring before the last {@code /}; the parent must exist unless it is the root (the empty string).</li>
 *   <li>Insert the path and return {@code true}. {@code get} is a plain map lookup with a default of -1.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code createPath("/a", 1)} succeeds. {@code createPath("/a/b", 2)} succeeds because {@code /a} exists.</li>
 *   <li>{@code createPath("/c/d", 1)} fails because {@code /c} does not exist.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(L)} — Where {@code L} is the length of the path (hashing and the parent lookup).</li>
 *   <li><b>Space Complexity:</b> {@code O(total path length)} — The map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
