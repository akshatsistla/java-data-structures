public class LinkedNode<T> {

    private T data;
    private LinkedNode<T> next;

    public LinkedNode(T data, LinkedNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public LinkedNode(T data) {
        this(data, null);
    }

    public T getData() {
        return data;
    }

    public LinkedNode<T> getNext() {
        return next;
    }

    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node containing: " + data;
    }

}
