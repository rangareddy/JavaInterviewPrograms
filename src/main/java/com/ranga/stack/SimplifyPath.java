package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an absolute Unix-style path, convert it to its canonical form: it starts with a single slash, directories are separated by one slash, there is no trailing slash, {@code .} is dropped, {@code ..} goes up one directory (never above the root), and repeated slashes are treated as one.
 *
 * <p><b>Algorithm Strategy (Segment Stack):</b>
 * <ol>
 *   <li>Split the path on {@code /} so that empty segments (from repeated slashes) and {@code .} can be ignored.</li>
 *   <li>For a {@code ..} segment, pop the stack if it is not empty. Any other non-empty segment is pushed.</li>
 *   <li>After processing every segment, the stack holds the canonical directory names from the root down.</li>
 *   <li>Join them with {@code /} behind a leading {@code /}; an empty stack is just {@code /}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code /a/./b/../../c/}: push a, ignore {@code .}, push b, pop b, pop a, push c.</li>
 *   <li>Result: {@code /c}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is processed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack of directory names.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
