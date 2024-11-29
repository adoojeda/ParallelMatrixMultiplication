package benchmark;

import basic.BasicMatrixMultiplication;
import parallel.ParallelMatrixMultiplication;
import vectorized.VectorizedMatrixMultiplication;
import utils.MatrixUtils;
import utils.Metrics;

public class Benchmark {

    public static void main(String[] args) {
        try {
            int[] matrixSizes = {100, 200, 500, 1000, 1500, 2000};  // Array of matrix sizes
            int threadCount = 8;   // Number of threads for the parallel algorithm

            for (int size : matrixSizes) {
                System.out.println("\n=== Benchmarking for Matrix Size: " + size + "x" + size + " ===");

                // Generate matrices for the current size
                double[][] matrixA = MatrixUtils.generateMatrix(size);
                double[][] matrixB = MatrixUtils.generateMatrix(size);

                // --- Basic Implementation ---
                System.out.println("Basic Implementation:");
                long basicTime = measureAndPrintMetrics(
                        () -> BasicMatrixMultiplication.multiply(matrixA, matrixB)
                );

                // --- Parallel Implementation ---
                System.out.println("\nParallel Implementation:");
                long parallelTime = measureAndPrintMetrics(() -> {
                    try {
                        ParallelMatrixMultiplication.multiply(matrixA, matrixB, threadCount);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                // --- Vectorized Implementation ---
                System.out.println("\nVectorized Implementation:");
                long vectorizedTime = measureAndPrintMetrics(
                        () -> VectorizedMatrixMultiplication.multiply(matrixA, matrixB)
                );

                // --- Metrics Analysis ---
                System.out.println("\n--- Metrics Analysis ---");

                // Speedup and Efficiency
                double speedupParallel = Metrics.calculateSpeedup(basicTime, parallelTime);
                double efficiencyParallel = Metrics.calculateEfficiency(speedupParallel, threadCount);
                double speedupVectorized = Metrics.calculateSpeedup(basicTime, vectorizedTime);

                // Print Results
                System.out.println("Speedup (Parallel vs Basic): " + speedupParallel);
                System.out.println("Efficiency (Parallel): " + efficiencyParallel);
                System.out.println("Speedup (Vectorized vs Basic): " + speedupVectorized);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Measures and prints execution time, CPU usage, and memory usage for a given algorithm.
     * Returns execution time in ms.
     */
    private static long measureAndPrintMetrics(Runnable algorithm) {
        // Measure execution time
        long executionTime = Metrics.measureExecutionTime(algorithm);

        // Measure CPU usage
        double cpuUsage = Metrics.measureCpuUsage(algorithm);

        // Measure memory usage
        long memoryUsage = Metrics.measureMemoryUsage(algorithm);

        // Print metrics
        System.out.printf("Execution Time (ms): %d%n", executionTime);
        System.out.printf("CPU Usage (%%): %.2f%n", cpuUsage);
        System.out.printf("Memory Usage (bytes): %d%n", memoryUsage);

        return executionTime;
    }
}