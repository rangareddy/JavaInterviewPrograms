# Encode and Decode Strings

Design an algorithm to encode a list of strings into a single string that can be sent over the network, and decode it back into the original list. The strings may contain any characters.

## Algorithm Strategy (Length-Prefix Encoding)

1. A delimiter alone is not safe because the strings may contain it. Instead prefix every string with its length and a separator: `length + "#" + string`.
2. Encode: append `length#string` for every string to a `StringBuilder`.
3. Decode: read digits up to the next `#` to get the length `len`, then take exactly the next `len` characters as the string, whatever they contain, and continue after them.
4. Because the length tells how many characters to read, even strings containing `#`, digits or empty strings are decoded correctly.

## Example walkthrough

- `["we", "say", ":", "yes"]` is encoded as `"2#we3#say1#:3#yes"`.
- Decoding reads 2 then `"we"`, 3 then `"say"`, 1 then `":"`, 3 then `"yes"`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Both directions touch each character once (`n` = total length).
- **Space Complexity:** `O(n)` — The encoded string and the decoded list.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Join with a Delimiter (Fragile)

Join the strings with a delimiter such as `":;"` and split on it. This breaks as soon as a string contains the delimiter itself (for example `[":;"]` decodes as two empty strings), so it is only a starting point.

```java
public static String encodeDelimiter(List<String> strs) {
    return String.join(":;", strs);
}

public static List<String> decodeDelimiter(String s) {
    return new ArrayList<>(Arrays.asList(s.split(":;", -1)));
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Escape the Delimiter

Escape the escape character (`/` becomes `//`) and terminate every string with the pair `/:`. While decoding, `//` is a literal slash and `/:` ends a string, so strings may contain any characters.

```java
public static String encodeEscape(List<String> strs) {
    StringBuilder sb = new StringBuilder();
    for (String str : strs) sb.append(str.replace("/", "//")).append("/:");
    return sb.toString();
}

public static List<String> decodeEscape(String s) {
    List<String> result = new ArrayList<>();
    StringBuilder current = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '/') {
            i++;
            if (s.charAt(i) == '/') current.append('/');
            else { result.add(current.toString()); current.setLength(0); }   // "/:"
        } else {
            current.append(s.charAt(i));
        }
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Length Prefix (Optimized)

Write `length#string` for every string and decode by reading exactly that many characters; no escaping is needed, and decoding never scans the content of a string. This is the version implemented in the repository.

```java
public static String encode(List<String> strs) {
    StringBuilder sb = new StringBuilder();
    for (String str : strs) sb.append(str.length()).append('#').append(str);
    return sb.toString();
}

public static List<String> decode(String s) {
    List<String> result = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
        int separator = s.indexOf('#', i);
        int length = Integer.parseInt(s.substring(i, separator));
        result.add(s.substring(separator + 1, separator + 1 + length));
        i = separator + 1 + length;
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Join with a Delimiter (Fragile) | O(n) | O(n) |
| 2 | Escape the Delimiter | O(n) | O(n) |
| 3 | Length Prefix (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### EncodeAndDecodeStrings.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncodeAndDecodeStrings {

    public static void main(String[] args) {
        List<List<String>> inputs = Arrays.asList(
                Arrays.asList("lint", "code", "love", "you"),
                Arrays.asList("we", "say", ":", "yes"),
                Arrays.asList("", "#", "3#abc"));
        for (List<String> strs : inputs) {
            String encoded = encode(strs);
            System.out.println("Input   : " + strs);
            System.out.println("Encoded : " + encoded);
            System.out.println("Output  : " + decode(encoded));
            System.out.println();
        }
    }

    public static String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str.length()).append('#').append(str);
        }
        return sb.toString();
    }

    public static List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            int separator = s.indexOf('#', i);
            int length = Integer.parseInt(s.substring(i, separator));
            result.add(s.substring(separator + 1, separator + 1 + length));
            i = separator + 1 + length;
        }
        return result;
    }
}
```
