import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MaxHeap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = data.size();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("A value in data is null");
            }
            backingArray[i + 1] = data.get(i);
        }
        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }

    }

    private void downHeap(int i) {
        if (backingArray[2 * i] != null) {
            if (backingArray[2 * i + 1] == null) {
                if (backingArray[2 * i].compareTo(backingArray[i]) > 0) {
                    T temp = backingArray[i];
                    backingArray[i] = backingArray[2 * i];
                    backingArray[2 * i] = temp;
                    downHeap(2 * i);
                }
            } else {
                if (backingArray[2 * i].compareTo(backingArray[i]) > 0) {
                    if (backingArray[2 * i + 1].
                            compareTo(backingArray[2 * i]) < 0) {
                        T temp = backingArray[i];
                        backingArray[i] = backingArray[2 * i];
                        backingArray[2 * i] = temp;
                        downHeap(2 * i);
                    } else {
                        T temp = backingArray[i];
                        backingArray[i] = backingArray[2 * i + 1];
                        backingArray[2 * i + 1] = temp;
                        downHeap(2 * i + 1);
                    }
                } else if (backingArray[2 * i + 1].
                        compareTo(backingArray[i]) > 0) {
                    T temp = backingArray[i];
                    backingArray[i] = backingArray[2 * i + 1];
                    backingArray[2 * i + 1] = temp;
                    downHeap(2 * i + 1);
                }
            }
        }
    }

    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Input Data is null");
        }
        if (size == 0) {
            backingArray[1] = item;
        }
        if (size == backingArray.length - 1) {
            T[] temp = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 1; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
            add(item);
            size--;
        } else {
            backingArray[size + 1] = item;
            upHeap(size + 1);
        }
        size++;
    }

    private void upHeap(int i) {
        if (i != 1) {
            if (backingArray[i].compareTo(backingArray[i / 2]) > 0) {
                T temp = backingArray[i];
                backingArray[i] = backingArray[i / 2];
                backingArray[i / 2] = temp;
                upHeap(i / 2);
            }
        }
    }

    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Empty heap");
        }
        T ret = backingArray[1];
        if (size == 1) {
            backingArray[1] = null;
        } else {
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            downHeap(1);
        }
        size--;
        return ret;
    }

    public T getMax() {
        if (size == 0) {
            return null;
        }
        return backingArray[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public Object[] getBackingArray() {
        return backingArray;
    }

}
