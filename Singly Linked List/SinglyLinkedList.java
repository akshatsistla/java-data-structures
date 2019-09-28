import java.util.Arrays;

public class SinglyLinkedList<T> {

    public static void main(String[] args) {
        SinglyLinkedList l = new SinglyLinkedList();
        l.addToFront(5);
        l.addToFront(5);
        l.addToFront(5);
        l.addToFront(5);
        l.addToFront(5);

        Comparable[] c = new Comparable[5];

        LinkedListNode curr = l.head;

        for(int i = 0 ;i < c.length; i++) {
            c[i] = (Comparable) curr.getData();
            curr = curr.getNext();
        }

        System.out.println(Arrays.toString(c));

    }

    private LinkedListNode<T> head;
    private int size;

    public void addAtIndex(int index, T data) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new IllegalArgumentException();
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> curr = head;
            LinkedListNode<T> temps = new LinkedListNode(data);
            temps.setNext(curr.getNext());
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
                temps.setNext(curr.getNext());
            }
            curr.setNext(temps);
            size++;
        }
    }

    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (head == null) {
            LinkedListNode<T> temps = new LinkedListNode(data);
            head = temps;
            temps.setNext(head);
        } else {
            LinkedListNode<T> temps = new LinkedListNode(head.getData());
            temps.setNext(head.getNext());
            head.setData(data);
            head.setNext(temps);
        }
        size++;
    }

    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (head == null) {
            LinkedListNode<T> temps = new LinkedListNode(data);
            head = temps;
            temps.setNext(head);
        } else {
            LinkedListNode<T> temps = new LinkedListNode(head.getData());
            temps.setNext(head.getNext());
            head.setData(data);
            head.setNext(temps);
            head = head.getNext();
        }
        size++;
    }

    public T removeAtIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            return removeFromFront();
        } else {
            LinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            T ret = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
            return ret;
        }
    }

    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            T ret = head.getData();
            clear();
            return ret;
        }
        T ret = head.getData();
        head.setData(head.getNext().getData());
        head.setNext(head.getNext().getNext());
        size--;
        return ret;
    }

    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            return removeFromFront();
        } else {
            LinkedListNode<T> curr = head;
            for (int i = 2; i < size; i++) {
                curr = curr.getNext();
            }
            T ret = curr.getNext().getData();
            curr.setNext(head);
            size--;
            return ret;
        }
    }

    public T removeLastOccurrence(T data) {

        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }

        if (size == 1) {
            if (head.getData().equals(data)) {
                return removeFromFront();
            }
        }
        LinkedListNode<T> prev = head;
        LinkedListNode<T> curr = head;
        int c = 0;
        for (int i = 0; i < size - 1; i++) {
            if (curr.getNext().getData().equals(data)) {
                prev = curr;
                c++;
            }
            curr = curr.getNext();
        }

        if (c == 0) {
            if (head.getData().equals(data)) {
                return removeFromFront();
            } else {
                return null;
            }
        }
        LinkedListNode<T> ret = prev.getNext();
        prev.setNext(ret.getNext());
        size--;
        return ret.getData();
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            return head.getData();
        }

        LinkedListNode<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    public Object[] toArray() {
        Object[] ret = new Object[size];
        LinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            ret[i] = curr.getData();
            curr = curr.getNext();
        }
        return ret;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public LinkedListNode<T> getHead() {
        return head;
    }
}
