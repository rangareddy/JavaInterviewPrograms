package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Given a reference to a node of a connected undirected graph, return a deep copy (clone) of the graph. Each node has an integer value and a list of its neighbors.
 *
 * <p><b>Algorithm Strategy (DFS with a Map from Original to Clone):</b>
 * <ol>
 *   <li>Keep a {@code Map<Node, Node>} that maps every original node to its already created clone. It also acts as the visited set, which prevents infinite loops in cyclic graphs.</li>
 *   <li>For a node, return its clone straight from the map if it exists.</li>
 *   <li>Otherwise create the clone, store it in the map <i>before</i> visiting the neighbors, then clone every neighbor recursively and add the cloned neighbors to the clone.</li>
 *   <li>Return the clone of the starting node.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[2,4],[1,3],[2,4],[1,3]]} (a square 1-2-3-4): clone 1, then recursively 2 (whose neighbor 1 is already cloned), 3, and 4.</li>
 *   <li>The cloned graph has the same adjacency list but none of its nodes is the original object.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(V + E)} — Each node is created once and each edge is traversed twice.</li>
 *   <li><b>Space Complexity:</b> {@code O(V)} — The map and the recursion stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
