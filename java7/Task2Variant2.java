import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class Task2Variant2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int rows = 5;
        int cols = 10;
        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (int) (Math.random() * 100);
            }
        }

        System.out.println("Матрица:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%4d ", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        int numThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> futures = new ArrayList<>();

        int rowsPerThread = (int) Math.ceil((double) rows / numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int startRow = i * rowsPerThread;
            final int endRow = Math.min((i + 1) * rowsPerThread, rows);

            if (startRow >= rows) break;

            Callable<Integer> task = () -> {
                int max = Integer.MIN_VALUE;
                for (int r = startRow; r < endRow; r++) {
                    for (int c = 0; c < cols; c++) {
                        if (matrix[r][c] > max) {
                            max = matrix[r][c];
                        }
                    }
                }
                System.out.println("Поток " + Thread.currentThread().getName() + 
                                   ": строки " + startRow + "-" + (endRow-1) + 
                                   ", максимум = " + max);
                return max;
            };

            futures.add(executor.submit(task));
        }

        int globalMax = Integer.MIN_VALUE;
        for (Future<Integer> future : futures) {
            int partialMax = future.get();
            if (partialMax > globalMax) {
                globalMax = partialMax;
            }
        }

        executor.shutdown();
        System.out.println("\nНаибольший элемент в матрице: " + globalMax);
    }
}
