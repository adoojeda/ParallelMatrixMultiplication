package utils;

import java.util.Random;

public class MatrixUtils {

    // Generate a random matrix of size n x n
    public static double[][] generateMatrix(int size) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Math.random() * 10;  // Or any other logic to fill the matrix
            }
        }
        return matrix;
    }
}
