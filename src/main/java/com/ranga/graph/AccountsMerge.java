package com.ranga.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Each account is a list whose first element is a name and whose remaining elements are emails. Two accounts belong to the same person if they share at least one email (people can have the same name without being the same person). Merge the accounts and return, for each person, the name followed by their emails in sorted order. The accounts can be returned in any order.
 *
 * <p><b>Algorithm Strategy (Union-Find over Emails):</b>
 * <ol>
 *   <li>Treat every distinct email as a node. Give each email an integer id, remember which name owns it, and keep a union-find over the ids.</li>
 *   <li>For each account, union the id of its first email with the id of every other email in the same account: they are all one person.</li>
 *   <li>Group the emails by their union-find root; each group is one merged account.</li>
 *   <li>Sort the emails of each group, put the owner's name in front, and add the list to the answer. Names are never used to merge accounts, only emails are.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For John's accounts {@code [johnsmith@mail.com, john_newyork@mail.com]} and {@code [johnsmith@mail.com, john00@mail.com]}, the shared email joins them into one group of three emails.</li>
 *   <li>The result lists {@code John, john00@mail.com, john_newyork@mail.com, johnsmith@mail.com} (sorted) as one account, while the second John with {@code johnnybravo@mail.com} stays separate.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(N log N)} — {@code N} is the total number of emails: union-find is almost linear and the sort of the emails dominates.</li>
 *   <li><b>Space Complexity:</b> {@code O(N)} — The id map, the owner map and the groups.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AccountsMerge {

    public static void main(String[] args) {
        List<List<String>> accounts = Arrays.asList(
                Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"),
                Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"),
                Arrays.asList("Mary", "mary@mail.com"),
                Arrays.asList("John", "johnnybravo@mail.com"));
        System.out.println("Input  : accounts = " + accounts);
        System.out.println("Output : " + accountsMerge(accounts));
    }

    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> ids = new HashMap<>();
        Map<String, String> owner = new HashMap<>();
        int total = 0;
        for (List<String> account : accounts) {
            total += account.size() - 1;
        }
        int[] parent = new int[total];
        for (int i = 0; i < total; i++) {
            parent[i] = i;
        }
        int next = 0;
        for (List<String> account : accounts) {
            String name = account.get(0);
            int first = -1;
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                if (!ids.containsKey(email)) {
                    ids.put(email, next++);
                    owner.put(email, name);
                }
                int id = ids.get(email);
                if (first == -1) {
                    first = id;
                } else {
                    parent[find(parent, id)] = find(parent, first);
                }
            }
        }
        Map<Integer, List<String>> groups = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : ids.entrySet()) {
            groups.computeIfAbsent(find(parent, entry.getValue()), k -> new ArrayList<>()).add(entry.getKey());
        }
        List<List<String>> result = new ArrayList<>();
        for (List<String> emails : groups.values()) {
            Collections.sort(emails);
            List<String> merged = new ArrayList<>();
            merged.add(owner.get(emails.get(0)));
            merged.addAll(emails);
            result.add(merged);
        }
        return result;
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
