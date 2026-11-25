package com.ranga.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Design an in-memory file system with {@code ls(path)} (if the path is a file, return a list with just its name; if a directory, return the names of its files and directories in lexicographic order), {@code mkdir(path)} (create a directory, with any missing intermediate directories), {@code addContentToFile(filePath, content)} (create the file if needed and append the content) and {@code readContentFromFile(filePath)} (return the file content).
 *
 * <p><b>Algorithm Strategy (Trie of Path Segments):</b>
 * <ol>
 *   <li>Model the file system as a tree: each node has a name, a map of children (a {@code TreeMap} keeps names sorted) and, for files, a {@code StringBuilder} of content.</li>
 *   <li>A helper walks a path from the root, creating directories when asked.</li>
 *   <li>{@code ls} returns the single file name if the last node is a file, otherwise the sorted keys of its children.</li>
 *   <li>{@code addContentToFile} creates the intermediate directories and the file, then appends; {@code readContentFromFile} returns the content.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code mkdir("/a/b/c")}, {@code addContentToFile("/a/b/c/d", "hello")}: {@code ls("/")} is {@code [a]}, and reading {@code /a/b/c/d} gives {@code hello}.</li>
 *   <li>{@code ls("/a/b/c/d")} on the file returns {@code [d]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(path length + children)} — One walk down the tree per operation; {@code ls} sorts via the {@code TreeMap}.</li>
 *   <li><b>Space Complexity:</b> {@code O(total characters)} — The tree and the file contents.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
