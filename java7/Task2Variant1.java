public class Task2Variant1 {
    private static int[][] matrix;
    private static int[] rowMaxValues;

    public static void main(String[] args) {
        int rows = 5;
        int cols = 10;
        matrix = new int[rows][cols];
        rowMaxValues = new int[rows];

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

        Thread[] threads = new Thread[rows];
        for (int i = 0; i < rows; i++) {
            final int rowIndex = i;
            threads[i] = new Thread(() -> {
                int max = matrix[rowIndex][0];
                for (int j = 1; j < matrix[rowIndex].length; j++) {
                    if (matrix[rowIndex][j] > max) {
                        max = matrix[rowIndex][j];
                    }
                }
                rowMaxValues[rowIndex] = max;
                System.out.println("Поток для строки " + rowIndex + ": максимум = " + max);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int globalMax = rowMaxValues[0];
        for (int i = 1; i < rowMaxValues.length; i++) {
            if (rowMaxValues[i] > globalMax) {
                globalMax = rowMaxValues[i];
            }
        }

        System.out.println("\nНаибольший элемент в матрице: " + globalMax);
    }
}
