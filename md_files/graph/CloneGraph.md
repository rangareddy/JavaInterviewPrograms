# Clone Graph

Given a reference to a node of a connected undirected graph, return a deep copy (clone) of the graph. Each node has an integer value and a list of its neighbors.

## Algorithm Strategy (DFS with a Map from Original to Clone)

1. Keep a `Map<Node, Node>` that maps every original node to its already created clone. It also acts as the visited set, which prevents infinite loops in cyclic graphs.
2. For a node, return its clone straight from the map if it exists.
3. Otherwise create the clone, store it in the map <i>before</i> visiting the neighbors, then clone every neighbor recursively and add the cloned neighbors to the clone.
4. Return the clone of the starting node.

## Example walkthrough

- For `[[2,4],[1,3],[2,4],[1,3]]` (a square 1-2-3-4): clone 1, then recursively 2 (whose neighbor 1 is already cloned), 3, and 4.
- The cloned graph has the same adjacency list but none of its nodes is the original object.

## Complexity Analysis

- **Time Complexity:** `O(V + E)` — Each node is created once and each edge is traversed twice.
- **Space Complexity:** `O(V)` — The map and the recursion stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Iterative BFS with a Map

Create the clone of the start node, then process a queue: for every node, create missing clones of its neighbors and connect them. No recursion, so no stack overflow on large graphs.

```java
public static Node cloneGraphBfs(Node start) {
    if (start == null) return null;
    Map<Node, Node> clones = new HashMap<>();
    clones.put(start, new Node(start.val));
    Queue<Node> queue = new LinkedList<>();
    queue.add(start);
    while (!queue.isEmpty()) {
        Node node = queue.poll();
        for (Node neighbor : node.neighbors) {
            if (!clones.containsKey(neighbor)) {
                clones.put(neighbor, new Node(neighbor.val));
                queue.add(neighbor);
            }
            clones.get(node).neighbors.add(clones.get(neighbor));
        }
    }
    return clones.get(start);
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Approach 2: Iterative DFS with a Stack

The same as the BFS version with a stack instead of a queue.

```java
public static Node cloneGraphStack(Node start) {
    if (start == null) return null;
    Map<Node, Node> clones = new HashMap<>();
    clones.put(start, new Node(start.val));
    Deque<Node> stack = new ArrayDeque<>();
    stack.push(start);
    while (!stack.isEmpty()) {
        Node node = stack.pop();
        for (Node neighbor : node.neighbors) {
            if (!clones.containsKey(neighbor)) {
                clones.put(neighbor, new Node(neighbor.val));
                stack.push(neighbor);
            }
            clones.get(node).neighbors.add(clones.get(neighbor));
        }
    }
    return clones.get(start);
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Approach 3: Two Passes: Create All Clones, Then Connect Them

First discover all nodes with a BFS and create an unconnected clone for each. A second pass copies the edges between the clones. Separating node creation from edge creation keeps both loops trivial.

```java
public static Node cloneGraphTwoPass(Node start) {
    if (start == null) return null;
    Map<Node, Node> clones = new HashMap<>();
    List<Node> order = new ArrayList<>();
    Queue<Node> queue = new LinkedList<>();
    clones.put(start, new Node(start.val));
    queue.add(start);
    while (!queue.isEmpty()) {                                  // pass 1: discover and create
        Node node = queue.poll();
        order.add(node);
        for (Node neighbor : node.neighbors) {
            if (!clones.containsKey(neighbor)) { clones.put(neighbor, new Node(neighbor.val)); queue.add(neighbor); }
        }
    }
    for (Node node : order) {                                   // pass 2: connect
        for (Node neighbor : node.neighbors) clones.get(node).neighbors.add(clones.get(neighbor));
    }
    return clones.get(start);
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Approach 4: Array Indexed by the Node Value

The nodes are numbered `1..n` with unique values, so an array can replace the hash map: `clones[node.val]` is the clone of the node with that value (and `null` means not yet cloned). The problem limits the graph to 100 nodes.

```java
public static Node cloneGraphArray(Node node) {
    return node == null ? null : copy(node, new Node[101]);
}

private static Node copy(Node node, Node[] clones) {
    if (clones[node.val] != null) return clones[node.val];
    Node clone = new Node(node.val);
    clones[node.val] = clone;
    for (Node neighbor : node.neighbors) clone.neighbors.add(copy(neighbor, clones));
    return clone;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Approach 5: Rebuild from an Adjacency List

Read the graph into an adjacency list (value to neighbour values) and construct a brand-new graph from it. The new graph shares nothing with the old one by construction, and the intermediate list is easy to print or test.

```java
public static Node cloneGraphRebuild(Node start) {
    if (start == null) return null;
    Map<Integer, List<Integer>> adjacency = new TreeMap<>();
    Deque<Node> queue = new ArrayDeque<>();
    queue.add(start);
    adjacency.put(start.val, new ArrayList<>());
    while (!queue.isEmpty()) {
        Node node = queue.poll();
        for (Node neighbor : node.neighbors) {
            adjacency.get(node.val).add(neighbor.val);
            if (!adjacency.containsKey(neighbor.val)) { adjacency.put(neighbor.val, new ArrayList<>()); queue.add(neighbor); }
        }
    }
    Map<Integer, Node> nodes = new HashMap<>();
    for (int val : adjacency.keySet()) nodes.put(val, new Node(val));
    for (Map.Entry<Integer, List<Integer>> e : adjacency.entrySet()) {
        for (int neighbor : e.getValue()) nodes.get(e.getKey()).neighbors.add(nodes.get(neighbor));
    }
    return nodes.get(start.val);
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 6: BFS with an IdentityHashMap

If `Node` defined `equals`/`hashCode` by value (which would make two different nodes with the same value collide), a normal `HashMap` would break. An `IdentityHashMap` compares nodes by reference, so the clone logic stays correct whatever `equals` does.

```java
public static Node cloneGraphIdentity(Node start) {
    if (start == null) return null;
    Map<Node, Node> clones = new IdentityHashMap<>();
    clones.put(start, new Node(start.val));
    Queue<Node> queue = new LinkedList<>();
    queue.add(start);
    while (!queue.isEmpty()) {
        Node node = queue.poll();
        for (Node neighbor : node.neighbors) {
            if (!clones.containsKey(neighbor)) { clones.put(neighbor, new Node(neighbor.val)); queue.add(neighbor); }
            clones.get(node).neighbors.add(clones.get(neighbor));
        }
    }
    return clones.get(start);
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Approach 7: Recursive DFS with a Map (Optimized)

Map every original node to its clone; the map is also the visited set. Clone a node, register it, then clone the neighbors. This is the version implemented in the repository.

```java
static class Node {
    public int val;
    public List<Node> neighbors = new ArrayList<>();
    public Node(int val) { this.val = val; }
}

public static Node cloneGraph(Node node) {
    return node == null ? null : clone(node, new HashMap<>());
}

private static Node clone(Node node, Map<Node, Node> clones) {
    if (clones.containsKey(node)) return clones.get(node);
    Node copy = new Node(node.val);
    clones.put(node, copy);
    for (Node neighbor : node.neighbors) copy.neighbors.add(clone(neighbor, clones));
    return copy;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Iterative BFS with a Map | O(V + E) | O(V) |
| 2 | Iterative DFS with a Stack | O(V + E) | O(V) |
| 3 | Two Passes: Create All Clones, Then Connect Them | O(V + E) | O(V) |
| 4 | Array Indexed by the Node Value | O(V + E) | O(V) |
| 5 | Rebuild from an Adjacency List | O(V + E) | O(V + E) |
| 6 | BFS with an IdentityHashMap | O(V + E) | O(V) |
| 7 | Recursive DFS with a Map (Optimized) | O(V + E) | O(V) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### CloneGraph.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CloneGraph {

    public static class Node {
        public int val;
        public List<Node> neighbors = new ArrayList<>();

        public Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        int[][][] inputs = {{{2, 4}, {1, 3}, {2, 4}, {1, 3}}, {{}}, {}};
        for (int[][] adjList : inputs) {
            Node original = build(adjList);
            Node copy = cloneGraph(original);
            System.out.println("Input  : adjList = " + Arrays.deepToString(adjList));
            System.out.println("Output : adjList = " + toAdjList(copy) + ", same object as the original: " + (original != null && original == copy));
            System.out.println();
        }
    }

    public static Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        return clone(node, new HashMap<>());
    }

    private static Node clone(Node node, Map<Node, Node> clones) {
        if (clones.containsKey(node)) {
            return clones.get(node);
        }

        Node copy = new Node(node.val);
        clones.put(node, copy);                 // register before visiting the neighbors
        for (Node neighbor : node.neighbors) {
            copy.neighbors.add(clone(neighbor, clones));
        }
        return copy;
    }

    // Builds a graph from an adjacency list where node i (1-indexed) is described by adjList[i - 1]
    private static Node build(int[][] adjList) {
        if (adjList.length == 0) {
            return null;
        }
        Node[] nodes = new Node[adjList.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i + 1);
        }
        for (int i = 0; i < adjList.length; i++) {
            for (int neighbor : adjList[i]) {
                nodes[i].neighbors.add(nodes[neighbor - 1]);
            }
        }
        return nodes[0];
    }

    private static List<List<Integer>> toAdjList(Node start) {
        Map<Integer, List<Integer>> adjacency = new TreeMap<>();
        if (start != null) {
            Deque<Node> queue = new ArrayDeque<>();
            queue.add(start);
            adjacency.put(start.val, new ArrayList<>());
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                for (Node neighbor : node.neighbors) {
                    adjacency.get(node.val).add(neighbor.val);
                    if (!adjacency.containsKey(neighbor.val)) {
                        adjacency.put(neighbor.val, new ArrayList<>());
                        queue.add(neighbor);
                    }
                }
            }
        }
        return new ArrayList<>(adjacency.values());
    }
}
```
