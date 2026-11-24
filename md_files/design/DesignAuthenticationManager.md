# Design Authentication Manager

Design an authentication system with a fixed token lifetime `timeToLive`. `generate(tokenId, currentTime)` creates a token that expires at `currentTime + timeToLive`. `renew(tokenId, currentTime)` extends an unexpired token (nothing happens for an unknown or expired token). `countUnexpiredTokens(currentTime)` returns the number of tokens that have not expired. A token expiring exactly at `currentTime` counts as expired.

## Algorithm Strategy (HashMap of Expiry Times)

1. Store the expiry time of each token in a `HashMap<String, Integer>`.
2. `generate` puts `currentTime + timeToLive`.
3. `renew` updates the expiry only if the token exists and `expiry > currentTime`.
4. `countUnexpiredTokens` counts the entries whose expiry is greater than `currentTime`.

## Example walkthrough

- With `timeToLive = 5`: generate `aaa` at 2 (expires 7), renew `aaa` at 6 (expires 11), count at 12 gives 0.
- A token renewed after it expired stays expired.

## Complexity Analysis

- **Time Complexity:** `O(1) except count, O(n)` — Counting scans the tokens; a time-ordered structure can make it faster.
- **Space Complexity:** `O(n)` — The tokens.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashMap of Expiry Times (Optimized)

Check the expiry of each token on demand. This is the version implemented in the repository.

```java
public static class Lazy {
    private final int ttl; private final java.util.Map<String, Integer> m = new java.util.HashMap<>();
    public Lazy(int ttl) { this.ttl = ttl; }
    public void generate(String id, int t) { m.put(id, t + ttl); }
    public void renew(String id, int t) { Integer e = m.get(id); if (e != null && e > t) m.put(id, t + ttl); }
    public int count(int t) { int c = 0; for (int e : m.values()) if (e > t) c++; return c; }
}
```

- **Time Complexity:** O(1) / O(n) count
- **Space Complexity:** O(n)

### Approach 2: Linked Hash Map with Eager Eviction

Because times only increase, keep tokens in expiry order and drop the expired ones from the front on every call.

```java
public static class Eager {
    private final int ttl; private final java.util.LinkedHashMap<String, Integer> m = new java.util.LinkedHashMap<>();
    public Eager(int ttl) { this.ttl = ttl; }
    private void evict(int t) { java.util.Iterator<java.util.Map.Entry<String, Integer>> it = m.entrySet().iterator(); while (it.hasNext() && it.next().getValue() <= t) it.remove(); }
    public void generate(String id, int t) { evict(t); m.remove(id); m.put(id, t + ttl); }
    public void renew(String id, int t) { evict(t); if (m.containsKey(id)) { m.remove(id); m.put(id, t + ttl); } }
    public int count(int t) { evict(t); return m.size(); }
}
```

- **Time Complexity:** O(1) amortised
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashMap of Expiry Times (Optimized) | O(1) / O(n) count | O(n) |
| 2 | Linked Hash Map with Eager Eviction | O(1) amortised | O(n) |

## Source Code

### DesignAuthenticationManager.java

```java
import java.util.HashMap;
import java.util.Map;

public class DesignAuthenticationManager {

    public static void main(String[] args) {
        DesignAuthenticationManager auth = new DesignAuthenticationManager(5);
        auth.renew("aaa", 1);
        auth.generate("aaa", 2);
        System.out.println("Input  : timeToLive = 5, renew aaa at 1, generate aaa at 2, count at 6");
        System.out.println("Output : " + auth.countUnexpiredTokens(6));
        System.out.println();
        auth.generate("bbb", 7);
        auth.renew("aaa", 8);
        auth.renew("bbb", 10);
        System.out.println("Input  : generate bbb at 7, renew aaa at 8 (expired), renew bbb at 10, count at 15");
        System.out.println("Output : " + auth.countUnexpiredTokens(15));
    }

    private final int timeToLive;
    private final Map<String, Integer> expiry = new HashMap<>();

    public DesignAuthenticationManager(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void generate(String tokenId, int currentTime) {
        expiry.put(tokenId, currentTime + timeToLive);
    }

    public void renew(String tokenId, int currentTime) {
        Integer expires = expiry.get(tokenId);
        if (expires != null && expires > currentTime) {
            expiry.put(tokenId, currentTime + timeToLive);
        }
    }

    public int countUnexpiredTokens(int currentTime) {
        int count = 0;
        for (int expires : expiry.values()) {
            if (expires > currentTime) {
                count++;
            }
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository; the eager variant (Approach 2) makes counting O(1) amortised.

The idea: a token is just an expiry time. Renewing is allowed only while the old expiry is still in the future, and counting compares each expiry with the current time.

**Sequence** with `timeToLive = 5`:

| Call | Expiry map afterwards | Result |
|------|-----------------------|--------|
| renew aaa at 1 | {} | unknown token, ignored |
| generate aaa at 2 | {aaa: 7} | |
| count at 6 | {aaa: 7} | 1 |
| generate bbb at 7 | {aaa: 7, bbb: 12} | |
| renew aaa at 8 | unchanged | aaa expired at 7, ignored |
| renew bbb at 10 | {aaa: 7, bbb: 15} | |
| count at 15 | | 0, bbb expires at 15 |

**First count in `main` (at time 6):** printed by the program.
