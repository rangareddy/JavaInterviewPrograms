package com.ranga.design;

/**
 * Design a text editor with a cursor. {@code addText(text)} appends text at the cursor; {@code deleteText(k)} deletes up to {@code k} characters to the left of the cursor and returns how many were deleted; {@code cursorLeft(k)} and {@code cursorRight(k)} move the cursor and return the last {@code min(10, len)} characters to the left of the cursor.
 *
 * <p><b>Algorithm Strategy (Two Stacks: Left and Right of the Cursor):</b>
 * <ol>
 *   <li>Keep the characters to the left of the cursor in one stack (a {@code StringBuilder}) and the characters to the right in another (a {@code StringBuilder} read backward).</li>
 *   <li>{@code addText} appends to the left stack.</li>
 *   <li>{@code deleteText(k)} removes up to {@code k} characters from the end of the left stack and returns the number removed.</li>
 *   <li>{@code cursorLeft(k)} moves characters from the left stack to the right stack, {@code cursorRight(k)} does the opposite; after each, return the last up to 10 characters of the left stack.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code addText("leetcode")} puts all eight characters left of the cursor.</li>
 *   <li>{@code deleteText(4)} returns 4 and leaves {@code leet}; then adding {@code practice} gives {@code leetpractice}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(k) per operation} — At most {@code k} characters are moved or removed.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The two stacks.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
