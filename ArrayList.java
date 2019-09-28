public class ArrayList<T> {

    private T[] backingArray;
    private int size;

    public static final int INITIAL_CAPACITY = 9;

    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    public void addAtIndex(int index, T data) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (data == null) {
            throw new IllegalArgumentException();
        }

        if (size != backingArray.length) {
            for (int i = backingArray.length - 1; i >= 0; i--) {
                if (i > index) {
                    backingArray[i] = backingArray[i - 1];
                } else if (i == index) {
                    backingArray[i] = data;
                }
            }
        } else {
            T[] temp = (T[]) new Object[2 * backingArray.length];
            int count = 0;
            for (int i = 0; i < backingArray.length + 1; i++) {
                if (i == index) {
                    temp[i] = data;
                } else {
                    temp[i] = backingArray[count];
                    count++;
                }
            }

            backingArray = temp;
        }
        size++;
    }

    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data input is null");
        }
        if (size != backingArray.length) {
            backingArray[size] = data;
        } else {
            T[] temp = (T[]) new Object[2 * backingArray.length];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            temp[backingArray.length] = data;
            backingArray = temp;
        }
        size++;
    }

    public T removeAtIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {
            backingArray[0] = null;
        }

        T[] temp = (T[]) new Object[backingArray.length];
        int count = 0;
        T ret = null;

        for (int i = 0; i <= size; i++) {
            if (i != index) {
                temp[count] = backingArray[i];
                count++;
            } else if (i == index) {
                ret = backingArray[i];
            }

        }

        backingArray = temp;
        size--;
        return ret;
    }

    public T removeFromFront() {
        return removeAtIndex(0);
    }

    public T removeFromBack() {
        T ret = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return ret;
    }

    public T get(int index) {
        if (index > backingArray.length || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return backingArray[index];
    }

    public int lastIndexOf(T data) {
        for (int i = size - 1; i >= 0; i--) {
            if (backingArray[i].equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return backingArray[0] == null;
    }

    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public Object[] getBackingArray() {
        return backingArray;
    }
}
