# SingleLinkedList Demo

## SingleLinkedListDemo.java

```java
class Node<T> {

    private T data;
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

    public void setNext(Node next) {
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
        Node<T> node = new Node(data);
        if (isEmpty()) {
            head = node;
        } else {
            Node curr = head;
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
        Node<T> deletedNode = null;
        if (index == 0) {
            deletedNode = curr;
            head = curr.getNext();
        } else {
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            deletedNode = curr.getNext();
            curr.setNext(curr.getNext().getNext());
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
        Node curr = head;
        Node reverseNode = null;
        while (curr != null) {
            Node next = curr.getNext();
            curr.setNext(reverseNode);
            reverseNode = curr;
            curr = next;
        }
        head = reverseNode;
    }

    private void validateIndex(int index) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(" Index value must be >= 0 but current index is " + index);
        } else if (index > size) {
            throw new ArrayIndexOutOfBoundsException(" Index: " + index + ", Size: " + size);
        }
    }

    public T getMiddleElement() {
        Node<T> curr = head;
        for (int i = 0; i < size / 2; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    public T getMiddleElement2() {
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast != null && fast.getNext() != null //&& fast.getNext().getNext() != null
                ) {
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

        System.out.print("Size is ");
        if (count % 2 == 0) {
            System.out.println("Even ");
        } else {
            System.out.println("Odd ");
        }
    }

    public void findLinkedListLengthEven() {
        Node node = head;
        while (node != null && node.getNext() != null) {
            node = node.getNext().getNext();
        }
        if (node == null) {
            System.out.println("Even Number");
        } else {
            System.out.println("Odd Number");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node curr = head;
        while (curr.getNext() != null) {
            sb.append(curr.getData() + ", ");
            curr = curr.getNext();
        }

        if (curr != null) {
            sb.append(curr.getData());
        }

        sb.append("]");
        return sb.toString();
    }
}

public class SingleLinkedListDemo {

    public static void main(String[] args) {

        SingleLinkedList<String> list = new SingleLinkedList<>();

        list.add("List1");
        list.add("List2");
        list.add("List3");
        list.add("List4");
        list.add("List5");
        list.add("List6");
        list.add("List7");
        list.add("List8");
        list.add("List9");
        list.add("List10");
        list.add("List11");
        list.add("List12");

        System.out.println("List size " + list.size());
        System.out.println("List of values " + list);
        
        String deletedValue = list.remove(0);
        System.out.println("Deleted 0th index value is " + deletedValue);
        System.out.println("List size " + list.size());
        System.out.println("List of values " + list);
        
        System.out.println("List Size is " + ((list.size() % 2 == 0) ? "Even" : "Odd"));
        list.findSizeEvenOrOdd();
        list.findLinkedListLengthEven();
        
        System.out.println("Middle Element " + list.getMiddleElement());
        System.out.println("Middle Element using way2 " + list.getMiddleElement2());
        System.out.println("Middle Element without using middleElement() " + list.get(list.size() / 2));
    }
}
```
