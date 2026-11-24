package com.ranga.design;

import java.util.HashMap;
import java.util.Map;

/**
 * Design an authentication system with a fixed token lifetime {@code timeToLive}. {@code generate(tokenId, currentTime)} creates a token that expires at {@code currentTime + timeToLive}. {@code renew(tokenId, currentTime)} extends an unexpired token (nothing happens for an unknown or expired token). {@code countUnexpiredTokens(currentTime)} returns the number of tokens that have not expired. A token expiring exactly at {@code currentTime} counts as expired.
 *
 * <p><b>Algorithm Strategy (HashMap of Expiry Times):</b>
 * <ol>
 *   <li>Store the expiry time of each token in a {@code HashMap<String, Integer>}.</li>
 *   <li>{@code generate} puts {@code currentTime + timeToLive}.</li>
 *   <li>{@code renew} updates the expiry only if the token exists and {@code expiry > currentTime}.</li>
 *   <li>{@code countUnexpiredTokens} counts the entries whose expiry is greater than {@code currentTime}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>With {@code timeToLive = 5}: generate {@code aaa} at 2 (expires 7), renew {@code aaa} at 6 (expires 11), count at 12 gives 0.</li>
 *   <li>A token renewed after it expired stays expired.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) except count, O(n)} — Counting scans the tokens; a time-ordered structure can make it faster.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The tokens.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
