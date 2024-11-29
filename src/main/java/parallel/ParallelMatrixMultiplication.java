package parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelMatrixMultiplication {

    // Method to perform parallel matrix multiplication
    public static double[][] multiply(double[][] matrixA, double[][] matrixB, int threadCount) throws Exception {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        // Validate dimensions
        if (colsA != rowsB) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }

        // Initialize the result matrix
        double[][] result = new double[rowsA][colsB];

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // Assign tasks to threads
        for (int i = 0; i < rowsA; i++) {
            final int row = i;
            executor.submit(() -> {
                for (int j = 0; j < colsB; j++) {
                    for (int k = 0; k < colsA; k++) {
                        result[row][j] += matrixA[row][k] * matrixB[k][j];
                    }
                }
            });
        }

        // Shutdown the executor
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all tasks to complete
        }

        return result;
    }
}
