import java.util.NoSuchElementException;

public class ArrayStack<T> {

    private T[] backingArray;
    private int size;

    public static final int INITIAL_CAPACITY = 9;

    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } else if (size == 0) {
            backingArray[0] = data;
        } else if (size == backingArray.length) {
            T[] temp = (T[]) new Object[2 * backingArray.length];
            temp[size] = data;
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        } else {
            backingArray[size] = data;
        }
        size++;
    }

    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T ret = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return ret;
    }

    public T peek() {
        if (size == 0) {
            return null;
        }
        return backingArray[size - 1];
    }

    public int size() {
        return size;
    }

    public Object[] getBackingArray() {
        return backingArray;
    }
}
