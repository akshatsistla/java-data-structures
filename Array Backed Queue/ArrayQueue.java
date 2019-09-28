import java.util.NoSuchElementException;

public class ArrayQueue<T> {

    private T[] backingArray;
    private int front;
    private int size;

    public static final int INITIAL_CAPACITY = 9;

    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        front = 0;
    }

    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (size == 0) {
            backingArray[0] = data;
        } else if (size == backingArray.length) {
            T[] temp = (T[]) new Object[2 * backingArray.length];
            for (int i = front; i < backingArray.length; i++) {
                temp[i - front] = backingArray[i];
            }
            for (int i = 0; i < front; i++) {
                temp[size - front + i] = backingArray[i];
            }
            temp[size] = data;
            backingArray = temp;
            front = 0;
        } else if (front + size >= backingArray.length) {
            backingArray[front + size - backingArray.length] = data;
        } else {
            backingArray[size + front] = data;
        }
        size++;
    }

    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            T ret = backingArray[0];
            backingArray[0] = null;
            size = 0;
            front = 0;
            return ret;
        }
        T ret = backingArray[front];
        backingArray[front] = null;
        front++;
        size--;
        return ret;
    }

    public T peek() {
        if (size == 0) {
            return null;
        }
        return backingArray[front];
    }

    public int size() {
        return size;
    }

    public Object[] getBackingArray() {
        return backingArray;
    }
}
