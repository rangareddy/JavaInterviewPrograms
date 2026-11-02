package com.ranga.collection;

/**
 * Demonstrates a generic singly linked list with common interview operations.
 *
 * <p><b>Algorithm Strategy:</b>
 * <ol>
 *   <li>Each {@code Node} stores a value and a reference to the next node; the list keeps a {@code head} reference and a {@code size}.</li>
 *   <li><b>add:</b> walk to the last node and link the new node after it ({@code O(n)}); an empty list just sets {@code head}.</li>
 *   <li><b>remove(index):</b> for index {@code 0} move {@code head} to the next node; otherwise stop at the node
 *       before the target and bypass the target with {@code prev.next = prev.next.next}.</li>
 *   <li><b>reverse:</b> walk the list once, pointing every node's {@code next} to the previous node, then make the
 *       last visited node the new head.</li>
 *   <li><b>middle element:</b> either advance {@code size / 2} steps, or use two pointers where {@code fast} moves two
 *       nodes per step and {@code slow} one; when {@code fast} reaches the end, {@code slow} is in the middle (single pass).</li>
 *   <li><b>even/odd size:</b> either count the nodes, or jump two nodes at a time and check where the pointer ends
 *       ({@code null} means even, the last node means odd).</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} for add, remove, get, reverse and middle; {@code O(1)} for size and isEmpty.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} auxiliary space for every operation (the list itself uses {@code O(n)}).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        SingleLinkedList<String> list = new SingleLinkedList<>();
        for (int i = 1; i <= 12; i++) {
            list.add("List" + i);
        }

        System.out.println("List size      : " + list.size());
        System.out.println("List of values : " + list);

        String deletedValue = list.remove(0);
        System.out.println("Deleted 0th index value : " + deletedValue);
        System.out.println("List size      : " + list.size());
        System.out.println("List of values : " + list);

        System.out.println("List size is " + ((list.size() % 2 == 0) ? "Even" : "Odd"));
        list.findSizeEvenOrOdd();
        list.findLinkedListLengthEven();

        System.out.println("Middle Element (counting)         : " + list.getMiddleElement());
        System.out.println("Middle Element (slow/fast)        : " + list.getMiddleElement2());
        System.out.println("Middle Element (get(size() / 2))  : " + list.get(list.size() / 2));

        list.reverse();
        System.out.println("Reversed list  : " + list);
    }
}

class Node<T> {

    private final T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return data + "";
    }
}

class SingleLinkedList<T> {

    private Node<T> head;
    private int size;

    public SingleLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(T data) {
        Node<T> node = new Node<>(data);
        if (isEmpty()) {
            head = node;
        } else {
            Node<T> curr = head;
            while (curr.getNext() != null) {
                curr = curr.getNext();
            }
            curr.setNext(node);
        }
        size++;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public T remove(int index) {
        validateIndex(index);

        Node<T> curr = head;
        Node<T> deletedNode;
        if (index == 0) {
            deletedNode = curr;
            head = curr.getNext();
        } else {
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            deletedNode = curr.getNext();
            curr.setNext(deletedNode.getNext());
        }
        size--;
        return deletedNode.getData();
    }

    public T get(int index) {
        validateIndex(index);
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    public void reverse() {
        Node<T> curr = head;
        Node<T> reverseNode = null;
        while (curr != null) {
            Node<T> next = curr.getNext();
            curr.setNext(reverseNode);
            reverseNode = curr;
            curr = next;
        }
        head = reverseNode;
    }

    private void validateIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index value must be >= 0 but current index is " + index);
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /** Middle element found by advancing {@code size / 2} nodes from the head. */
    public T getMiddleElement() {
        Node<T> curr = head;
        for (int i = 0; i < size / 2; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    /** Middle element found in a single pass with a slow and a fast pointer. */
    public T getMiddleElement2() {
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow.getData();
    }

    public void findSizeEvenOrOdd() {
        Node<T> current = head;
        int count = 0;
        while (current != null) {
            current = current.getNext();
            count++;
        }

        System.out.println("Size (counting nodes) is " + (count % 2 == 0 ? "Even" : "Odd"));
    }

    public void findLinkedListLengthEven() {
        Node<T> node = head;
        while (node != null && node.getNext() != null) {
            node = node.getNext().getNext();
        }
        System.out.println("Size (two-step jump) is " + (node == null ? "Even" : "Odd"));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.getData());
            if (curr.getNext() != null) {
                sb.append(", ");
            }
            curr = curr.getNext();
        }
        return sb.append("]").toString();
    }
}
