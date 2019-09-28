import java.util.NoSuchElementException;

public class LinkedStack<T> {

    private LinkedNode<T> head;
    private int size;

    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        LinkedNode<T> temp = new LinkedNode<T>(data);
        temp.setNext(head);
        head = temp;
        size++;
    }

    public T pop() {
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
}
