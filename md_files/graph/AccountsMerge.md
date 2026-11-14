# Accounts Merge

Each account is a list whose first element is a name and whose remaining elements are emails. Two accounts belong to the same person if they share at least one email (people can have the same name without being the same person). Merge the accounts and return, for each person, the name followed by their emails in sorted order. The accounts can be returned in any order.

## Algorithm Strategy (Union-Find over Emails)

1. Treat every distinct email as a node. Give each email an integer id, remember which name owns it, and keep a union-find over the ids.
2. For each account, union the id of its first email with the id of every other email in the same account: they are all one person.
3. Group the emails by their union-find root; each group is one merged account.
4. Sort the emails of each group, put the owner's name in front, and add the list to the answer. Names are never used to merge accounts, only emails are.

## Example walkthrough

- For John's accounts `[johnsmith@mail.com, john_newyork@mail.com]` and `[johnsmith@mail.com, john00@mail.com]`, the shared email joins them into one group of three emails.
- The result lists `John, john00@mail.com, john_newyork@mail.com, johnsmith@mail.com` (sorted) as one account, while the second John with `johnnybravo@mail.com` stays separate.

## Complexity Analysis

- **Time Complexity:** `O(N log N)` — `N` is the total number of emails: union-find is almost linear and the sort of the emails dominates.
- **Space Complexity:** `O(N)` — The id map, the owner map and the groups.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Graph of Emails with DFS

Connect the first email of each account to all the others in an undirected graph, then run a DFS from every unvisited email to collect its connected component.

```java
public static List<List<String>> accountsMergeDfs(List<List<String>> accounts) {
    Map<String, Set<String>> graph = new HashMap<>();
    Map<String, String> owner = new HashMap<>();
    for (List<String> account : accounts) {
        String first = account.get(1);
        for (int i = 1; i < account.size(); i++) {
            String email = account.get(i);
            owner.put(email, account.get(0));
            graph.computeIfAbsent(email, k -> new HashSet<>()).add(first);
            graph.computeIfAbsent(first, k -> new HashSet<>()).add(email);
        }
    }
    Set<String> seen = new HashSet<>();
    List<List<String>> result = new ArrayList<>();
    for (String email : graph.keySet()) {
        if (seen.add(email)) {
            List<String> component = new ArrayList<>();
            Deque<String> stack = new ArrayDeque<>();
            stack.push(email);
            while (!stack.isEmpty()) {
                String e = stack.pop();
                component.add(e);
                for (String n : graph.get(e)) if (seen.add(n)) stack.push(n);
            }
            Collections.sort(component);
            component.add(0, owner.get(email));
            result.add(component);
        }
    }
    return result;
}
```

- **Time Complexity:** O(N log N)
- **Space Complexity:** O(N)

### Approach 2: Union-Find (Optimized)

Union all emails of an account into one set, then group by root; no explicit graph is built. This is the version implemented in the repository.

```java
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
```

- **Time Complexity:** O(N log N)
- **Space Complexity:** O(N)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Graph of Emails with DFS | O(N log N) | O(N) |
| 2 | Union-Find (Optimized) | O(N log N) | O(N) |

## Source Code

### AccountsMerge.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: two accounts belong to the same person when they share an email, and sharing is transitive, so this is a connected-components problem on emails. Each distinct email gets an integer id (`ids`) and remembers its owner name (`owner`). For every account, the ids of all its emails are unioned with the id of its first email (Union-Find with `parent[]`). At the end, every email is grouped by its root, each group is sorted, and the owner name is put in front. Groups are collected in a `TreeMap` keyed by the root id, so the output order is deterministic.

**Input:**
`[[John, johnsmith@mail.com, john_newyork@mail.com], [John, johnsmith@mail.com, john00@mail.com], [Mary, mary@mail.com], [John, johnnybravo@mail.com]]`.
`parent` has `total = 6` slots (the sum of the email counts, duplicates included); only ids 0 to 4 are used.

| Step | Account | Email | Id | Action | `parent[0..4]` |
|------|---------|-------|----|--------|----------------|
| 1 | John #1 | `johnsmith@` | 0 (new) | first email of the account | `[0,1,2,3,4]` |
| 2 | John #1 | `john_newyork@` | 1 (new) | union(1, 0): `parent[1] = 0` | `[0,0,2,3,4]` |
| 3 | John #2 | `johnsmith@` | 0 (known) | first email of the account | `[0,0,2,3,4]` |
| 4 | John #2 | `john00@` | 2 (new) | union(2, 0): `parent[2] = 0` | `[0,0,0,3,4]` |
| 5 | Mary | `mary@` | 3 (new) | first email, nothing to union | `[0,0,0,3,4]` |
| 6 | John #3 | `johnnybravo@` | 4 (new) | first email, nothing to union | `[0,0,0,3,4]` |

The shared email `johnsmith@` (id 0) is what links the first two accounts. Grouping by root: root 0 has `john00@, john_newyork@, johnsmith@` (after sorting), root 3 has `mary@`, root 4 has `johnnybravo@`.

```text
root 0: johnsmith -- john_newyork       (account 1)
        johnsmith -- john00             (account 2, linked through johnsmith)
root 3: mary
root 4: johnnybravo
```

Each group is emitted as `[owner, sorted emails...]`, where the owner is looked up from the first sorted email.

**Edge cases.** An account with only a name and one email contributes a group of one. Two different people with the same name but no shared email stay separate (names are never used for merging). An empty account list produces an empty result.

**Result:** `[[John, john00@mail.com, john_newyork@mail.com, johnsmith@mail.com], [Mary, mary@mail.com], [John, johnnybravo@mail.com]]`, identical to the class output.
