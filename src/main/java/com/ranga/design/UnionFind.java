package com.ranga.design;

/**
 * Implement a disjoint set union (union-find) over the elements {@code 0..n-1} with {@code find(x)} (the representative of the set of {@code x}), {@code union(a, b)} (merge the sets, returning whether they were different), {@code connected(a, b)} and {@code count()} (the number of sets). Use path compression and union by rank so that operations take almost constant time.
 *
 * <p><b>Algorithm Strategy (Parent Array with Path Compression and Union by Rank):</b>
 * <ol>
 *   <li>Each element points to a parent; a root points to itself. Two elements are in the same set exactly when they have the same root.</li>
 *   <li>{@code find(x)} follows parents to the root. Path compression makes every node on the way point straight to the root, so the next lookups are short. The loop below does it by path halving: each node on the path is re-pointed to its grandparent.</li>
 *   <li>{@code union(a, b)} finds the two roots. If they are the same the elements are already connected. Otherwise attach the root with the smaller rank (an upper bound on the tree height) under the other; if the ranks are equal, pick one and increase its rank.</li>
 *   <li>With both optimizations the amortized cost per operation is {@code O(alpha(n))}, where {@code alpha} is the inverse Ackermann function, below 5 for any practical {@code n}. Each successful union reduces the set count by one.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>With 5 elements: {@code union(0, 1)} and {@code union(1, 2)} give the set {@code {0, 1, 2}}; {@code count()} is 3 (that set, {@code {3}} and {@code {4}}).</li>
 *   <li>{@code connected(0, 2)} is true, {@code connected(0, 3)} is false; {@code union(0, 2)} returns false because they are already joined.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(alpha(n))} — Amortized per {@code find} or {@code union}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent and rank arrays.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class UnionFind {

    private final int[] parent;
    private final int[] rank;
    private int count;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public static void main(String[] args) {
        UnionFind uf = new UnionFind(5);
        System.out.println("Input  : UnionFind(5), union(0, 1), union(1, 2), count(), connected(0, 2), connected(0, 3), union(0, 2)");
        boolean a = uf.union(0, 1);
        boolean b = uf.union(1, 2);
        int sets = uf.count();
        boolean c = uf.connected(0, 2);
        boolean d = uf.connected(0, 3);
        boolean e = uf.union(0, 2);
        System.out.println("Output : " + a + ", " + b + ", " + sets + ", " + c + ", " + d + ", " + e);
    }

    public int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) {
            return false;
        }
        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
        count--;
        return true;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public int count() {
        return count;
    }
}
