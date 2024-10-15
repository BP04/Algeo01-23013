public class MultipleQuadraticRegression {
    public static void multiple_quadratic_regression(Matrix matrix) {
        int rows = matrix.get_rows(), cols = matrix.get_cols() - 1;
        // Columns combination as number of interactive variables
        int cols_comb = (cols * (cols - 1)) / 2;
        Matrix augmented_matrix = new Matrix(rows, (2 * cols) + cols_comb + 1);
        // Fill linear variable
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                augmented_matrix.set(i, j, matrix.get(i, j));
            }
        }
        // Fill squared variables
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                augmented_matrix.set(i, j + cols, Math.pow(matrix.get(i, j), 2));
            }
        }
        // Fill interaction variables
        for (int k = 0; k < rows; k++) {
            int idx = 0;
            for (int i = 0; i < cols - 1; i++) {
                for (int j = i + 1; j < cols; j++) {
                    augmented_matrix.set(k, (2 * cols) + idx, (matrix.get(k, i) * matrix.get(k, j)));
                    idx++;
                }
            }
        }
        // Fill output (y)
        for (int i = 0; i < rows; i++) {
            augmented_matrix.set(i, augmented_matrix.get_cols() - 1, matrix.get(i, cols));
        }

        SPLGaussJordan.spl_gauss_jordan(augmented_matrix);
    }
}