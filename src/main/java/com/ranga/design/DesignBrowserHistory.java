package com.ranga.design;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement a browser history that starts on a homepage. {@code visit(url)} opens a new page and clears all forward history, {@code back(steps)} moves back at most {@code steps} pages and returns the current page, and {@code forward(steps)} moves forward at most {@code steps} pages and returns the current page.
 *
 * <p><b>Algorithm Strategy (List with a Current Pointer):</b>
 * <ol>
 *   <li>Keep a list of visited pages and an index {@code current} for the page being shown.</li>
 *   <li>{@code visit} truncates everything after {@code current}, appends the new page and moves {@code current} to it.</li>
 *   <li>{@code back} and {@code forward} only move the index, clamped to the bounds of the list.</li>
 *   <li>Each operation costs O(1), apart from the truncation in {@code visit}, which can be avoided by tracking an end index instead of removing entries.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Visit google.com, facebook.com, youtube.com from leetcode.com, go back one page (facebook.com), back two (leetcode.com) and forward one (google.com).</li>
 *   <li>Visiting linkedin.com then discards the forward history.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) per operation} — With an end index, no elements are removed.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The visited pages.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignBrowserHistory {

    public static void main(String[] args) {
        DesignBrowserHistory history = new DesignBrowserHistory("leetcode.com");
        history.visit("google.com");
        history.visit("facebook.com");
        history.visit("youtube.com");
        System.out.println("Input  : visit google.com, facebook.com, youtube.com after the homepage leetcode.com");
        System.out.println("Output : " + history.pages());
        System.out.println();
        System.out.println("Input  : back(1), back(1), forward(1)");
        System.out.println("Output : " + history.back(1) + ", " + history.back(1) + ", " + history.forward(1));
        System.out.println();
        history.visit("linkedin.com");
        System.out.println("Input  : visit linkedin.com, forward(2), back(2), back(7)");
        System.out.println("Output : " + history.forward(2) + ", " + history.back(2) + ", " + history.back(7));
    }

    private final List<String> pages = new ArrayList<>();
    private int current;
    private int end;

    public DesignBrowserHistory(String homepage) {
        pages.add(homepage);
    }

    public void visit(String url) {
        current++;
        if (current < pages.size()) {
            pages.set(current, url);
        } else {
            pages.add(url);
        }
        end = current;
    }

    public String back(int steps) {
        current = Math.max(0, current - steps);
        return pages.get(current);
    }

    public String forward(int steps) {
        current = Math.min(end, current + steps);
        return pages.get(current);
    }

    /** The pages that can currently be reached, for printing. */
    public List<String> pages() {
        return new ArrayList<>(pages.subList(0, end + 1));
    }
}
