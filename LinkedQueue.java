
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedQueue<T> {

    private Node<T> frontNode;
    private Node<T> rearNode;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedQueue() {
        frontNode = null;
        rearNode = null;
        size = 0;
    }

    public void enqueue(T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Cannot enqueue null");
        }

        Node<T> newNode = new Node<>(item);

        if (isEmpty()) {
            frontNode = newNode;
        }
        else {
            rearNode.next = newNode;
        }

        rearNode = newNode;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                "Cannot dequeue from an empty queue");
        }

        T result = frontNode.data;
        frontNode = frontNode.next;
        size--;

        if (isEmpty()) {
            rearNode = null;
        }

        return result;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                "Cannot peek at an empty queue");
        }

        return frontNode.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public List<T> toList() {
        List<T> result = new ArrayList<>();

        Node<T> current = frontNode;

        while (current != null) {
            result.add(current.data);
            current = current.next;
        }

        return result;
    }
}
