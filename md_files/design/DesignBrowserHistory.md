# Design Browser History

Implement a browser history that starts on a homepage. `visit(url)` opens a new page and clears all forward history, `back(steps)` moves back at most `steps` pages and returns the current page, and `forward(steps)` moves forward at most `steps` pages and returns the current page.

## Algorithm Strategy (List with a Current Pointer)

1. Keep a list of visited pages and an index `current` for the page being shown.
2. `visit` truncates everything after `current`, appends the new page and moves `current` to it.
3. `back` and `forward` only move the index, clamped to the bounds of the list.
4. Each operation costs O(1), apart from the truncation in `visit`, which can be avoided by tracking an end index instead of removing entries.

## Example walkthrough

- Visit google.com, facebook.com, youtube.com from leetcode.com, go back one page (facebook.com), back two (leetcode.com) and forward one (google.com).
- Visiting linkedin.com then discards the forward history.

## Complexity Analysis

- **Time Complexity:** `O(1) per operation` — With an end index, no elements are removed.
- **Space Complexity:** `O(n)` — The visited pages.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Two Stacks

A back stack and a forward stack; `visit` clears the forward stack. Moving `k` steps costs O(k).

```java
public static class TwoStacks {
    private final java.util.Deque<String> back = new java.util.ArrayDeque<>(), fwd = new java.util.ArrayDeque<>(); private String cur;
    public TwoStacks(String home) { cur = home; }
    public void visit(String u) { back.push(cur); cur = u; fwd.clear(); }
    public String back(int s) { while (s-- > 0 && !back.isEmpty()) { fwd.push(cur); cur = back.pop(); } return cur; }
    public String forward(int s) { while (s-- > 0 && !fwd.isEmpty()) { back.push(cur); cur = fwd.pop(); } return cur; }
}
```

- **Time Complexity:** O(k) per move
- **Space Complexity:** O(n)

### Approach 2: Array with a Cursor (Optimized)

Keep the pages in a list with a current index and an end index. This is the version implemented in the repository.

```java
public static class Cursor {
    private final java.util.List<String> p = new java.util.ArrayList<>(); private int cur, end;
    public Cursor(String home) { p.add(home); }
    public void visit(String u) { cur++; if (cur < p.size()) p.set(cur, u); else p.add(u); end = cur; }
    public String back(int s) { cur = Math.max(0, cur - s); return p.get(cur); }
    public String forward(int s) { cur = Math.min(end, cur + s); return p.get(cur); }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Two Stacks | O(k) per move | O(n) |
| 2 | Array with a Cursor (Optimized) | O(1) per operation | O(n) |

## Source Code

### DesignBrowserHistory.java

```java
import java.util.ArrayList;
import java.util.List;

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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a browser's history is a line of pages with a cursor on one of them. Back and forward move the cursor, and visiting a new page writes at the next slot and sets the end of the history there, which discards the old forward pages without deleting anything.

**Sequence:** start at `leetcode.com`, visit `google.com`, `facebook.com`, `youtube.com`.

| Operation | `current` | `end` | Returns |
|-----------|-----------|-------|---------|
| visit google.com | 1 | 1 | |
| visit facebook.com | 2 | 2 | |
| visit youtube.com | 3 | 3 | |
| back(1) | 2 | 3 | facebook.com |
| back(1) | 1 | 3 | google.com |
| forward(1) | 2 | 3 | facebook.com |
| visit linkedin.com | 3 | 3 | (overwrites youtube.com) |

**Output of the three moves in `main`:** `facebook.com, google.com, facebook.com`.
