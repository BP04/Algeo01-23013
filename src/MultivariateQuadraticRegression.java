public class MultivariateQuadraticRegression {
    public static void multiple_quadratic_regression(Matrix matrix) {
        int rows = matrix.get_rows(), cols = matrix.get_cols() - 1;
        // n > 1 checking
        if (cols <= 1) {
            throw new IllegalArgumentException("Number of terms must be greater than 1.");
        }

        // Columns combination as number of interactive variables
        int cols_comb = (cols * (cols - 1)) / 2;
        Matrix augmented_matrix = new Matrix(rows,(2 * cols) + cols_comb + 1);
        // Fill constants (1)
        for (int i = 0; i < rows; i++) {
            augmented_matrix.set(i, 0, 1);
        }
        // Fill linear variable
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                augmented_matrix.set(i, j + 1, matrix.get(i, j));
            }
        }
        // Fill squared variables
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                augmented_matrix.set(i, j + cols + 1, Math.pow(matrix.get(i, j), 2));
            }
        }
        // Fill interaction variables
        for (int k = 0; k < rows; k++) {
            int idx = 0;
            for (int i = 0; i < cols - 1; i++) {
                for (int j = i + 1; j < cols; j++) {
                    augmented_matrix.set(k, 1 + (2 * cols) + idx, (matrix.get(k, i) * matrix.get(k, j)));
                    idx++;
                }
            }
        }
        // Fill output (y)
        Matrix output = new Matrix(augmented_matrix.get_rows(), 1);
        for (int i = 0; i < rows; i++) {
            output.set(i, 0, matrix.get(i, cols));
        }

        // Generate output variables
        int col = matrix.get_cols() - 1;
        String[] variables = new String[(2 * col) + cols_comb + 1];
        variables[0] = "c";
        // Linear variables
        for (int i = 1; i <= col; i++) {
            variables[i] = "x" + i;
        }
        // Squared variables
        for (int i = 1; i <= col; i++) {
            variables[i+col] = "x" + i + "^2";
        }
        // Interaction variables
        int idx = 1 + col + col;
        for (int i = 1; i <= col - 1; i++) {
            for (int j = i + 1; j <= col; j++) {
                variables[idx] = "x" + i + "x" + j;
                idx++;
            }
        }

        // Calculate each beta variables value using regular formula for Ordinary Least Square
        Matrix A = augmented_matrix.transpose_matrix();
        A = A.multiply(augmented_matrix);
        A.print_matrix();
        A = InverseMatrixDeterminant.inverse_with_determinant(A);
        Matrix b = augmented_matrix.transpose_matrix();
        b = b.multiply(output);
        Matrix beta = A.multiply(b);

        // Output result
        for (int i = 0; i < (2 * col) + cols_comb + 1; i++) {
            System.out.println(variables[i] + " = " + beta.get(i, 0));
        }
    }
}