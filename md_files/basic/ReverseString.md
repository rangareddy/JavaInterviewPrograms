# Reverse a String

Reverse the characters of a string, and reverse the order of the words in a sentence (collapsing extra spaces).

## Algorithm Strategy (Two Pointers Swap on a Character Array)

1. Characters: copy the string into a `char[]`, put one pointer at each end and swap the two characters while `left < right`.
2. Words: split the text on one or more spaces (`trim()` first, then `split("\\s+")`).
3. Walk through the words from the last one to the first and join them with a single space.
4. Strings are immutable in Java, so each result is a new string.

## Example walkthrough

- `"hello"`: swap `h`/`o`, then `e`/`l` to get `"olleh"`.
- `" the sky is blue "` gives the words `[the, sky, is, blue]`; reversed and joined: `"blue is sky the"`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every character is visited once.
- **Space Complexity:** `O(n)` — The character array or the word list.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: StringBuilder.reverse

Let the library do it. It is the idiomatic one-liner for characters.

```java
public static String reverseBuilder(String text) {
    return new StringBuilder(text).reverse().toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Prepend Characters (Inefficient)

Build the result by putting each character in front; every concatenation copies the string, so the total work is quadratic.

```java
public static String reverseConcat(String text) {
    String result = "";
    for (int i = 0; i < text.length(); i++) result = text.charAt(i) + result;
    return result;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 3: Recursion

Reverse the rest of the string and append the first character.

```java
public static String reverseRecursive(String text) {
    if (text.length() <= 1) return text;
    return reverseRecursive(text.substring(1)) + text.charAt(0);
}
```

- **Time Complexity:** O(n^2) because of `substring` copies
- **Space Complexity:** O(n^2)

### Approach 4: Two Pointers Swap (Optimized)

Swap the characters of a `char[]` from both ends; for words, split and join from the back. This is the version implemented in the repository.

```java
public static String reverse(String text) {
    char[] chars = text.toCharArray();
    int left = 0, right = chars.length - 1;
    while (left < right) {
        char temp = chars[left];
        chars[left++] = chars[right];
        chars[right--] = temp;
    }
    return new String(chars);
}

public static String reverseWords(String sentence) {
    String trimmed = sentence.trim();
    if (trimmed.isEmpty()) return "";
    String[] words = trimmed.split("\\s+");
    StringBuilder result = new StringBuilder();
    for (int i = words.length - 1; i >= 0; i--) {
        result.append(words[i]);
        if (i > 0) result.append(' ');
    }
    return result.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | StringBuilder.reverse | O(n) | O(n) |
| 2 | Prepend Characters (Inefficient) | O(n^2) | O(n) |
| 3 | Recursion | O(n^2) because of `substring` copies | O(n^2) |
| 4 | Two Pointers Swap (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### ReverseString.java

```java
public class ReverseString {

    public static void main(String[] args) {
        String[] texts = {"hello", "Java", "a", ""};
        for (String text : texts) {
            System.out.println("Input  : text = \"" + text + "\"");
            System.out.println("Output : \"" + reverse(text) + "\"");
            System.out.println();
        }

        String[] sentences = {"the sky is blue", "  hello   world  ", "single"};
        for (String sentence : sentences) {
            System.out.println("Input  : sentence = \"" + sentence + "\"");
            System.out.println("Output : \"" + reverseWords(sentence) + "\"");
            System.out.println();
        }
    }

    public static String reverse(String text) {
        char[] chars = text.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = temp;
        }
        return new String(chars);
    }

    public static String reverseWords(String sentence) {
        String trimmed = sentence.trim();
        if (trimmed.isEmpty()) {
            return "";
        }

        String[] words = trimmed.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);
            if (i > 0) {
                result.append(' ');
            }
        }
        return result.toString();
    }
}
```
