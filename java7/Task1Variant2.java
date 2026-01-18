import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class Task1Variant2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        int numThreads = 4;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> futures = new ArrayList<>();

        int chunkSize = array.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = (i == numThreads - 1) ? array.length : (i + 1) * chunkSize;

            Callable<Integer> task = () -> {
                int partialSum = 0;
                for (int j = start; j < end; j++) {
                    partialSum += array[j];
                }
                System.out.println("Поток " + Thread.currentThread().getName() + 
                                   ": сумма с " + start + " по " + (end-1) + " = " + partialSum);
                return partialSum;
            };

            futures.add(executor.submit(task));
        }

        int totalSum = 0;
        for (Future<Integer> future : futures) {
            totalSum += future.get();
        }

        executor.shutdown();
        System.out.println("Общая сумма элементов массива: " + totalSum);
    }
}
