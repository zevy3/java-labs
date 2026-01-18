public class Task1Variant1 {
    private static int[] array;
    private static int sum1 = 0;
    private static int sum2 = 0;

    public static void main(String[] args) {
        array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        int mid = array.length / 2;

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < mid; i++) {
                sum1 += array[i];
            }
            System.out.println("Поток 1: сумма первой половины = " + sum1);
        });

        Thread thread2 = new Thread(() -> {
            for (int i = mid; i < array.length; i++) {
                sum2 += array[i];
            }
            System.out.println("Поток 2: сумма второй половины = " + sum2);
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int totalSum = sum1 + sum2;
        System.out.println("Общая сумма элементов массива: " + totalSum);
    }
}
