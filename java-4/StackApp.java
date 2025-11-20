import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class CustomEmptyStackException extends Exception {
    public CustomEmptyStackException(String message) {
        super(message);
    }
}

class ExceptionLogger {
    public static void log(Exception e) {
        try (FileWriter log = new FileWriter("exceptions.log", true)) {
            log.write(e.toString() + "\n");
        } catch (IOException io) {
            System.out.println("Не удалось записать лог: " + io.getMessage());
        }
    }
}

class CustomStack<T> {
    private ArrayList<T> stack = new ArrayList<>();

    public void push(T item) {
        stack.add(item);
    }

    public T pop() throws CustomEmptyStackException {
        if (stack.isEmpty()) {
            CustomEmptyStackException e = new CustomEmptyStackException("Попытка удалить из пустого стека");
            ExceptionLogger.log(e);
            throw e;
        }
        return stack.remove(stack.size() - 1);
    }
}

public class StackApp {
    public static void main(String[] args) {
        CustomStack<Integer> stack = new CustomStack<>();
        stack.push(10);
        stack.push(20);
        try {
            stack.pop();
            stack.pop();
            stack.pop();
        } catch (CustomEmptyStackException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
