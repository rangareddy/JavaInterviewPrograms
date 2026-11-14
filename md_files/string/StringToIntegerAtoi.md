# String to Integer (atoi)

Implement `myAtoi(String s)`, which converts a string to a 32-bit signed integer: skip leading spaces, read an optional `+` or `-` sign, read digits until the first non-digit, and clamp the result to the range `[-2^31, 2^31 - 1]`. Return 0 if no digits were read.

## Algorithm Strategy (State-by-State Parsing with Overflow Clamping)

1. Skip the leading spaces.
2. If the next character is `+` or `-`, remember the sign and move on.
3. Read digits one by one: `result = result * 10 + digit`. Stop at the first character that is not a digit.
4. Before multiplying check for overflow: if `result > (Integer.MAX_VALUE - digit) / 10` return `Integer.MAX_VALUE` (or `Integer.MIN_VALUE` for a negative sign). Return `sign * result`.

## Example walkthrough

- `" -42"`: spaces skipped, sign `-`, digits `42`, result `-42`.
- `"4193 with words"` reads `4193` and stops at the space; `"91283472332"` exceeds the range and is clamped to `2147483647`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Regex and BigInteger

Extract the leading number with a regex, parse it with `BigInteger` and clamp.

```java
public static int myAtoiRegex(String s) {
    Matcher matcher = Pattern.compile("^ *([+-]?\\d+)").matcher(s);
    if (!matcher.find()) return 0;
    String number = matcher.group(1);
    BigInteger value = new BigInteger(number.startsWith("+") ? number.substring(1) : number);
    if (value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) return Integer.MAX_VALUE;
    if (value.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0) return Integer.MIN_VALUE;
    return value.intValue();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Long Accumulator

Accumulate in a `long` and clamp as soon as it leaves the 32-bit range. (Relies on 64-bit arithmetic.)

```java
public static int myAtoiLong(String s) {
    int i = 0, n = s.length();
    while (i < n && s.charAt(i) == ' ') i++;
    int sign = 1;
    if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) sign = s.charAt(i++) == '-' ? -1 : 1;
    long result = 0;
    while (i < n && Character.isDigit(s.charAt(i))) {
        result = result * 10 + (s.charAt(i++) - '0');
        if (sign * result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (sign * result < Integer.MIN_VALUE) return Integer.MIN_VALUE;
    }
    return (int) (sign * result);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Finite State Machine

A table-driven automaton with the states start, signed, number and end.

```java
public static int myAtoiAutomaton(String s) {
    String state = "start";
    int sign = 1;
    long result = 0;
    for (char c : s.toCharArray()) {
        if (state.equals("start")) {
            if (c == ' ') continue;
            if (c == '+' || c == '-') { sign = c == '-' ? -1 : 1; state = "signed"; }
            else if (Character.isDigit(c)) { result = c - '0'; state = "number"; }
            else state = "end";
        } else if (state.equals("signed") || state.equals("number")) {
            if (Character.isDigit(c)) {
                result = result * 10 + (c - '0');
                state = "number";
                if (sign * result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
                if (sign * result < Integer.MIN_VALUE) return Integer.MIN_VALUE;
            } else state = "end";
        }
        if (state.equals("end")) break;
    }
    return (int) (sign * result);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Parse with an Overflow Pre-Check (Optimized)

Detect overflow before multiplying, so only 32-bit arithmetic is needed. This is the version implemented in the repository.

```java
public static int myAtoi(String s) {
    int i = 0, n = s.length();
    while (i < n && s.charAt(i) == ' ') i++;
    int sign = 1;
    if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) { sign = s.charAt(i) == '-' ? -1 : 1; i++; }
    int result = 0;
    while (i < n && Character.isDigit(s.charAt(i))) {
        int digit = s.charAt(i) - '0';
        if (result > (Integer.MAX_VALUE - digit) / 10) return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        result = result * 10 + digit;
        i++;
    }
    return sign * result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Regex and BigInteger | O(n) | O(n) |
| 2 | Long Accumulator | O(n) | O(1) |
| 3 | Finite State Machine | O(n) | O(1) |
| 4 | Parse with an Overflow Pre-Check (Optimized) | O(n) | O(1) |

## Source Code

### StringToIntegerAtoi.java

```java
public class StringToIntegerAtoi {

    public static void main(String[] args) {
        String[] inputs = {"42", "   -42", "4193 with words", "words and 987", "-91283472332", "91283472332", "+1", "", "   "};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + myAtoi(s));
            System.out.println();
        }
    }

    public static int myAtoi(String s) {
        int i = 0;
        int n = s.length();
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }

        int result = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + digit;
            i++;
        }
        return sign * result;
    }
}
```

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: read the string left to right in four phases and never build a number outside `int`: skip leading spaces, read one optional `+` or `-`, read digits while they are digits, and stop at the first other character. Before each digit is added, check `result > (Integer.MAX_VALUE - digit) / 10`; if so, `result * 10 + digit` would overflow, so the answer is clamped to `Integer.MAX_VALUE` or `Integer.MIN_VALUE` at once, without using `long`.

**Input:** `s = "   -42"`.

| Step | `i` | Char | Phase | State |
|------|-----|------|-------|-------|
| 1 | 0 | space | skip spaces | `i = 1` |
| 2 | 1 | space | skip spaces | `i = 2` |
| 3 | 2 | space | skip spaces | `i = 3` |
| 4 | 3 | `-` | sign | `sign = -1`, `i = 4` |
| 5 | 4 | `4` | digit; overflow check `0 > (2147483647 - 4) / 10`? no | `result = 4` |
| 6 | 5 | `2` | digit; `4 > (2147483647 - 2) / 10 = 214748364`? no | `result = 42` |
| 7 | 6 | end | `i == n`, stop | return `-1 * 42` |

**Overflow example:** `"-91283472332"`. After nine digits `result = 912834723`. For the next digit `3`, the limit is `(2147483647 - 3) / 10 = 214748364`, and `912834723 > 214748364`, so the method returns `Integer.MIN_VALUE` (sign is -1) = `-2147483648`.

**Edge cases.** `""` and `"   "` give `0` (no digits read). `"words and 987"` gives `0` because the first character is not a space, sign or digit. `"4193 with words"` gives `4193` (stops at the space). `"+1"` gives `1`, and `"91283472332"` gives `Integer.MAX_VALUE` = `2147483647`. A `null` string would throw `NullPointerException`.

**Result:** `-42`, matching the class output.
