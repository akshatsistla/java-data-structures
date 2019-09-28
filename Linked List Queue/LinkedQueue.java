import java.util.NoSuchElementException;

public class LinkedQueue<T> {

    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (size == 0) {
            head = new LinkedNode<T>(data);
            tail = head;
            size++;
        } else {
            LinkedNode<T> temp = new LinkedNode<T>(data);
            tail.setNext(temp);
            tail = temp;
            size++;
        }
    }

    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T ret = head.getData();
        LinkedNode<T> temp = head.getNext();
        head.setNext(null);
        head = temp;
        size--;
        return ret;
    }

    public T peek() {
        if (size == 0) {
            return null;
        }
        return head.getData();
    }

    public int size() {
        return size;
    }

    public LinkedNode<T> getHead() {
        return head;
    }

    public LinkedNode<T> getTail() {
        return tail;
    }
}
