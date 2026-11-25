# Design a Text Editor

Design a text editor with a cursor. `addText(text)` appends text at the cursor; `deleteText(k)` deletes up to `k` characters to the left of the cursor and returns how many were deleted; `cursorLeft(k)` and `cursorRight(k)` move the cursor and return the last `min(10, len)` characters to the left of the cursor.

## Algorithm Strategy (Two Stacks: Left and Right of the Cursor)

1. Keep the characters to the left of the cursor in one stack (a `StringBuilder`) and the characters to the right in another (a `StringBuilder` read backward).
2. `addText` appends to the left stack.
3. `deleteText(k)` removes up to `k` characters from the end of the left stack and returns the number removed.
4. `cursorLeft(k)` moves characters from the left stack to the right stack, `cursorRight(k)` does the opposite; after each, return the last up to 10 characters of the left stack.

## Example walkthrough

- `addText("leetcode")` puts all eight characters left of the cursor.
- `deleteText(4)` returns 4 and leaves `leet`; then adding `practice` gives `leetpractice`.

## Complexity Analysis

- **Time Complexity:** `O(k) per operation` — At most `k` characters are moved or removed.
- **Space Complexity:** `O(n)` — The two stacks.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: One String and a Cursor Index

Keep the whole text in one `StringBuilder` and an index for the cursor; insertions and deletions shift the characters, costing O(n).

```java
public static class Single {
    private final StringBuilder text = new StringBuilder(); private int cur;
    public void addText(String s) { text.insert(cur, s); cur += s.length(); }
    public int deleteText(int k) { int d = Math.min(k, cur); text.delete(cur - d, cur); cur -= d; return d; }
    public String cursorLeft(int k) { cur = Math.max(0, cur - k); return text.substring(Math.max(0, cur - 10), cur); }
    public String cursorRight(int k) { cur = Math.min(text.length(), cur + k); return text.substring(Math.max(0, cur - 10), cur); }
}
```

- **Time Complexity:** O(n) per operation
- **Space Complexity:** O(n)

### Approach 2: Two Stacks around the Cursor (Optimized)

Left and right of the cursor are kept apart; moving the cursor moves characters between them. This is the version implemented in the repository.

```java
public static class TwoStacks {
    private final StringBuilder l = new StringBuilder(), r = new StringBuilder();
    public void addText(String s) { l.append(s); }
    public int deleteText(int k) { int d = Math.min(k, l.length()); l.setLength(l.length() - d); return d; }
    public String cursorLeft(int k) { int m = Math.min(k, l.length()); for (int i = 0; i < m; i++) { r.append(l.charAt(l.length() - 1)); l.setLength(l.length() - 1); } return l.substring(Math.max(0, l.length() - 10)); }
    public String cursorRight(int k) { int m = Math.min(k, r.length()); for (int i = 0; i < m; i++) { l.append(r.charAt(r.length() - 1)); r.setLength(r.length() - 1); } return l.substring(Math.max(0, l.length() - 10)); }
}
```

- **Time Complexity:** O(k) per operation
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | One String and a Cursor Index | O(n) per operation | O(n) |
| 2 | Two Stacks around the Cursor (Optimized) | O(k) per operation | O(n) |

## Source Code

### DesignTextEditor.java

```java
public class DesignTextEditor {

    public static void main(String[] args) {
        DesignTextEditor editor = new DesignTextEditor();
        editor.addText("leetcode");
        System.out.println("Input  : addText(\"leetcode\"), then cursorLeft(0)");
        System.out.println("Output : " + editor.cursorLeft(0));
        System.out.println();
        System.out.println("Input  : deleteText(4), addText(\"practice\"), cursorRight(3)");
        int deleted = editor.deleteText(4);
        editor.addText("practice");
        System.out.println("Output : " + deleted + ", " + editor.cursorRight(3));
        System.out.println();
        System.out.println("Input  : cursorLeft(8), deleteText(10), cursorLeft(2), cursorRight(6)");
        String a = editor.cursorLeft(8);
        int d = editor.deleteText(10);
        String b = editor.cursorLeft(2);
        String c = editor.cursorRight(6);
        System.out.println("Output : " + a + ", " + d + ", " + b + ", " + c);
    }

    private final StringBuilder left = new StringBuilder();
    private final StringBuilder right = new StringBuilder();

    public void addText(String text) {
        left.append(text);
    }

    public int deleteText(int k) {
        int deleted = Math.min(k, left.length());
        left.setLength(left.length() - deleted);
        return deleted;
    }

    public String cursorLeft(int k) {
        int moved = Math.min(k, left.length());
        for (int i = 0; i < moved; i++) {
            right.append(left.charAt(left.length() - 1));
            left.setLength(left.length() - 1);
        }
        return lastTen();
    }

    public String cursorRight(int k) {
        int moved = Math.min(k, right.length());
        for (int i = 0; i < moved; i++) {
            left.append(right.charAt(right.length() - 1));
            right.setLength(right.length() - 1);
        }
        return lastTen();
    }

    private String lastTen() {
        return left.substring(Math.max(0, left.length() - 10));
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every operation happens at the cursor, so split the text at the cursor. Characters to the left live in one stack and characters to the right in another, with the character nearest the cursor on top. Moving the cursor transfers characters between the stacks, and typing and deleting only touch the left stack.

**Sequence:**

| Call | Left (before cursor) | Right (after cursor) | Returns |
|------|----------------------|----------------------|---------|
| `addText("leetcode")` | leetcode | | |
| `cursorLeft(0)` | leetcode | | `leetcode` |
| `deleteText(4)` | leet | | 4 |
| `addText("practice")` | leetpractice | | |
| `cursorRight(3)` | leetpractice | | `etpractice` |

**First output in `main`:** `leetcode`.
