public class MultipleLinearRegression {
    public static void multiple_linear_regression(Matrix points, double xk, int idx) {
        int rows = points.get_rows();
        int cols = points.get_cols()-1;

        Matrix x = new Matrix(rows, cols + 1);
        Matrix y = new Matrix(rows, 1);

        for (int i = 0; i < rows; i++) {
            x.set(i, 0, 1);  
            for (int j = 1; j <= cols; j++) {
                x.set(i, j, points.get(i, j - 1));
            }
            y.set(i, 0, points.get(i, cols));
        }

        Matrix matrix = new Matrix(cols+1, cols+2); 
        Matrix x_t = x.transpose_matrix();
        Matrix x_tx = x_t.multiply(x);
        Matrix x_ty = x_t.multiply(y);
        for (int i = 0; i < x_tx.get_rows(); i++) {
            for (int j = 0; j < x_tx.get_cols(); j++) {
                matrix.set(i, j, x_tx.get(i, j));
            }
            matrix.set(i, x_tx.get_cols(), x_ty.get(i, 0)); 
        }

        Matrix coefficients;
        switch (idx) {
            case 1:
                coefficients = spl_gaussian(matrix);
                break;
            case 2:
                coefficients = spl_gauss_jordan(matrix);
                break;
            case 3:
                coefficients = spl_inverse(matrix); // modify to square dulu
                break;
            case 4:
                coefficients = spl_cramer(matrix); // modify to square dulu
                break;
            default:
                throw new IllegalArgumentException("Invalid index.");
        }

        System.out.print("f(x) = ");
        for (int i = 0; i < coefficients.get_rows(); i++) {
            if (i == 0) {
                System.out.printf("%f", coefficients.get(i, 0));
            } else {
                System.out.printf(" + %fx%d", coefficients.get(i, 0), i);
            }
        }
        System.out.println();

        double f_xk = coefficients.get(0, 0); 
        for (int i = 1; i < coefficients.get_rows(); i++) {
            f_xk += coefficients.get(i, 0) * xk;
        }
        System.out.printf("f(xk) = %f\n", f_xk);
    }

    public static Matrix spl_gaussian(Matrix matrix) {
        matrix = GaussianElimination.gaussian_elimination(matrix);
        return gauss_solution(matrix);
    }

    public static Matrix spl_gauss_jordan(Matrix matrix) {
        matrix = GaussJordanElimination.gauss_jordan_elimination(matrix);
        return gauss_solution(matrix);
    }

    private static Matrix gauss_solution(Matrix matrix) {
        int rows = matrix.get_rows();
        int cols = matrix.get_cols();
        boolean hasContradiction = false;
        boolean hasAllZeros = false;

        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols - 1; j++) {
                sum += matrix.get(i, j);
            }
            if (sum == 0) {
                if (matrix.get(i, cols - 1) != 0) {
                    hasContradiction = true;
                } else {
                    hasAllZeros = true;
                }
            }
        }

        if (hasContradiction) {
            throw new IllegalArgumentException("Matrix does not have a solution");
        } else if (hasAllZeros) {
            throw new IllegalArgumentException("Matrix has infinite solutions.");
        }

        Matrix solution = new Matrix(rows, 1);
        for (int i = rows - 1; i >= 0; i--) {
            double sum = matrix.get(i, cols - 1);
            for (int j = i + 1; j < rows; j++) {
                sum -= matrix.get(i, j) * solution.get(j, 0);
            }
            double value = sum / matrix.get(i, i);
            solution.set(i, 0, value);
        }
        return solution;
    }

    public static Matrix spl_inverse(Matrix matrix) { 
        int rows = matrix.get_rows();
        int cols = matrix.get_cols();
        if (cols != rows + 1) {
            throw new IllegalArgumentException("Matrix is not square.");
        }
        Matrix coefficients = new Matrix(rows, rows);
        Matrix constants = new Matrix(rows, 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                coefficients.set(i, j, matrix.get(i, j));
            }
            constants.set(i, 0, matrix.get(i, cols-1));
        }
        Matrix inverse = InverseMatrixGaussJordan.inverse_matrix(coefficients);
        Matrix solution = inverse.multiply(constants);
        return solution;
    }

    public static Matrix spl_cramer(Matrix matrix) {
        int rows = matrix.get_rows();
        int cols = matrix.get_cols();
        if (rows != cols - 1) {
            throw new IllegalArgumentException("Cramer’s rule requires a square matrix.");
        }
        Matrix matrix_det = new Matrix(rows, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix_det.set(i, j, matrix.get(i, j));
            }
        }

        double det = DeterminantRowReduction.determinant_row_reduction(matrix_det);
        if (det == 0) {
            throw new IllegalArgumentException("Matrix is singular, there is no unique solution.");
        }

        Matrix solution = new Matrix(rows, 1);
        for (int i = 0; i < rows; i++) {
            double result = determinant_n(matrix, i) / det;
            solution.set(i, 0, result);
        }
        return solution;
    }

    private static double determinant_n(Matrix matrix, int n) {
        int rows = matrix.get_rows();
        int cols = matrix.get_cols();
        Matrix matrix_n = new Matrix(rows, cols - 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                matrix_n.set(i, j, (j == n) ? matrix.get(i, cols-1) : matrix.get(i, j));
            }
        }
        return DeterminantRowReduction.determinant_row_reduction(matrix_n);
    }
}
