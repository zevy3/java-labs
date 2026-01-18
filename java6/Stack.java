public class Stack<T> {
    private T[] data;
    private int size;

    public Stack(int capacity) {
        data = (T[]) new Object[capacity];
        size = 0;
    }

    public void push(T element) {
        if (size >= data.length) {
            throw new RuntimeException("Stack overflow");
        }
        data[size++] = element;
    }

    public T pop() {
        if (size == 0) {
            throw new RuntimeException("Stack is empty");
        }
        T element = data[--size];
        data[size] = null;
        return element;
    }

    public T peek() {
        if (size == 0) {
            throw new RuntimeException("Stack is empty");
        }
        return data[size - 1];
    }
}
