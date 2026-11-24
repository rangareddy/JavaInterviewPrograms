package com.ranga.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Every email has a local name and a domain, separated by {@code @}. In the local name, dots are ignored, and everything after the first {@code +} is ignored. These rules do not apply to the domain. Return the number of different addresses that actually receive mail.
 *
 * <p><b>Algorithm Strategy (Normalise and Hash Set):</b>
 * <ol>
 *   <li>Split each address at {@code @} into a local name and a domain.</li>
 *   <li>In the local name, cut everything from the first {@code +} and remove every {@code .}.</li>
 *   <li>Rebuild {@code local + "@" + domain} and add it to a {@code HashSet}.</li>
 *   <li>The answer is the size of the set.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code test.email+alex@leetcode.com} and {@code test.e.mail+bob.cathy@leetcode.com} both normalise to {@code testemail@leetcode.com}.</li>
 *   <li>{@code testemail+david@lee.tcode.com} stays separate because its domain differs.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * L)} — Every address of length {@code L} is scanned once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n * L)} — The set stores the normalised addresses.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class UniqueEmailAddresses {

    public static void main(String[] args) {
        String[][] inputs = {
            {"test.email+alex@leetcode.com", "test.e.mail+bob.cathy@leetcode.com", "testemail+david@lee.tcode.com"},
            {"a@leetcode.com", "b@leetcode.com", "c@leetcode.com"},
            {}
        };
        for (String[] emails : inputs) {
            System.out.println("Input  : emails = " + Arrays.toString(emails));
            System.out.println("Output : " + numUniqueEmails(emails));
            System.out.println();
        }
    }

    public static int numUniqueEmails(String[] emails) {
        if (emails == null || emails.length == 0) {
            return 0;
        }
        Set<String> unique = new HashSet<>();
        for (String email : emails) {
            int at = email.indexOf('@');
            String local = email.substring(0, at);
            int plus = local.indexOf('+');
            if (plus >= 0) {
                local = local.substring(0, plus);
            }
            unique.add(local.replace(".", "") + email.substring(at));
        }
        return unique.size();
    }
}
