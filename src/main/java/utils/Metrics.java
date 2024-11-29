package utils;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

public class Metrics {

    // Measure the execution time of a matrix multiplication algorithm
    public static long measureExecutionTime(Runnable algorithm) {
        long startTime = System.currentTimeMillis();
        algorithm.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime; // Time in milliseconds
    }

    // Calculate speedup: (time for basic algorithm) / (time for optimized algorithm)
    public static double calculateSpeedup(long basicTime, long optimizedTime) {
        return (double) basicTime / optimizedTime;
    }

    // Calculate efficiency: speedup / number of threads used
    public static double calculateEfficiency(double speedup, int numThreads) {
        return speedup / numThreads;
    }

    // Measure CPU usage for the current process
    public static double measureCpuUsage(Runnable algorithm) {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        long cpuTimeBefore = threadBean.getCurrentThreadCpuTime();
        long startTime = System.nanoTime();

        algorithm.run();

        long endTime = System.nanoTime();
        long cpuTimeAfter = threadBean.getCurrentThreadCpuTime();

        // Calculate CPU usage as a percentage
        double elapsedTime = (endTime - startTime) / 1e9;
        double cpuTimeUsed = (cpuTimeAfter - cpuTimeBefore) / 1e9;
        return (cpuTimeUsed / elapsedTime) * 100.0;
    }

    // Measure memory usage before and after execution
    public static long measureMemoryUsage(Runnable algorithm) {
        Runtime runtime = Runtime.getRuntime();

        System.gc();

        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        algorithm.run();

        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        return memoryAfter - memoryBefore;
    }
}
