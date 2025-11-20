class ArrayAverage {
    public static void main(String[] args) {
        Object[] arr = {1, 2, 3,"das", 4, 5};
        double sum = 0;
        int count = 0;
        
        try {
            if (arr == null || arr.length == 0) {
                throw new IllegalArgumentException("Массив пуст");
            }
            
            System.out.println("Обработка массива:");
            for (int i = 0; i < arr.length; i++) {
                try {
                    if (arr[i] instanceof Number) {
                        sum += ((Number) arr[i]).doubleValue();
                        count++;
                        System.out.println("Элемент " + i + ": " + arr[i]);
                    } else {
                        throw new ClassCastException("Элемент " + i + 
                            " не является числом: " + arr[i]);
                    }
                } catch (ClassCastException e) {
                    System.err.println("Предупреждение: " + e.getMessage());
                }
            }
            
            if (count == 0) {
                throw new ArithmeticException("Нет числовых элементов для вычисления");
            }
            
            double average = sum / count;
            System.out.println("\nСумма: " + sum);
            System.out.println("Среднее арифметическое: " + average);
            System.out.println("Обработано элементов: " + count + " из " + arr.length);
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Ошибка: Выход за границы массива!");
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}