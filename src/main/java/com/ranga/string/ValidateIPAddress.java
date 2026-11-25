package com.ranga.string;

/**
 * Given a string {@code queryIP}, return {@code "IPv4"} if it is a valid IPv4 address, {@code "IPv6"} if it is a valid IPv6 address, and {@code "Neither"} otherwise. A valid IPv4 address has four decimal parts in 0..255 without leading zeros, separated by dots. A valid IPv6 address has eight groups of one to four hexadecimal digits (either case) separated by colons.
 *
 * <p><b>Algorithm Strategy (Split and Validate by Type):</b>
 * <ol>
 *   <li>If the string contains a dot (and no colon), treat it as IPv4: split on {@code .} with limit -1, require 4 parts, and check each is 1-3 digits, has no leading zero (unless it is {@code 0}) and is at most 255.</li>
 *   <li>If it contains a colon, treat it as IPv6: split on {@code :} with limit -1, require 8 parts, and check each is 1-4 hex digits.</li>
 *   <li>Splitting with a negative limit keeps trailing empty parts, so {@code 1.1.1.1.} is rejected.</li>
 *   <li>Anything else is {@code "Neither"}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code 172.16.254.1} has four valid decimal parts: IPv4.</li>
 *   <li>{@code 2001:0db8:85a3:0:0:8A2E:0370:7334} has eight valid hex groups: IPv6.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the characters.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The split parts.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ValidateIPAddress {

    public static void main(String[] args) {
        String[] inputs = {"172.16.254.1", "2001:0db8:85a3:0:0:8A2E:0370:7334", "256.256.256.256", "192.168.01.1", "1e1.4.5.6", "2001:0db8:85a3::8A2E:037j:7334"};
        for (String ip : inputs) {
            System.out.println("Input  : queryIP = \"" + ip + "\"");
            System.out.println("Output : " + validIPAddress(ip));
            System.out.println();
        }
    }

    public static String validIPAddress(String queryIP) {
        if (queryIP == null) {
            return "Neither";
        }
        if (queryIP.contains(".") && !queryIP.contains(":")) {
            return isIPv4(queryIP) ? "IPv4" : "Neither";
        }
        if (queryIP.contains(":") && !queryIP.contains(".")) {
            return isIPv6(queryIP) ? "IPv6" : "Neither";
        }
        return "Neither";
    }

    private static boolean isIPv4(String ip) {
        String[] parts = ip.split("\\.", -1);
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            if (part.isEmpty() || part.length() > 3 || (part.length() > 1 && part.charAt(0) == '0')) {
                return false;
            }
            for (char c : part.toCharArray()) {
                if (c < '0' || c > '9') {
                    return false;
                }
            }
            if (Integer.parseInt(part) > 255) {
                return false;
            }
        }
        return true;
    }

    private static boolean isIPv6(String ip) {
        String[] parts = ip.split(":", -1);
        if (parts.length != 8) {
            return false;
        }
        for (String part : parts) {
            if (part.isEmpty() || part.length() > 4) {
                return false;
            }
            for (char c : part.toCharArray()) {
                boolean digit = c >= '0' && c <= '9';
                boolean lower = c >= 'a' && c <= 'f';
                boolean upper = c >= 'A' && c <= 'F';
                if (!digit && !lower && !upper) {
                    return false;
                }
            }
        }
        return true;
    }
}
