public interface myStack<T> {
    void push(T x);
    T peek();
    T pop();
    boolean isEmpty();
    int size();
    void delete(T n);
}

class Node<T> {
    T data;
    Node<T> next;

    Node(T data) {
        this.data = data;
        this.next = null;
        }
}

public class ArrayStack<T> implements MyStack<T> {

    private T[] data;
    private int size;
    private int capacity;

    public ArrayStack() {
        capacity = 10;
        data = (T[]) new Object[capacity];
        size = 0;
    }

    public void push(T x) {
        if (size == capacity) {
            resize();
        }
        data[size++] = x;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T item = data[--size];
        data[size] = null;
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return data[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void delete(T n) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(n)) {
                for (int j = i; j < size - 1; j++) {
                    data[j] = data[j + 1];
                }
                data[--size] = null;
                return;
            }
        }
    }

    private void resize() {
        capacity *= 2;
        T[] newData = (T[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}
 