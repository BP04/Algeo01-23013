public class PolynomialInterpolation {

    public static Matrix polynomial_interpolation(Matrix points, int idx) {
        int n = points.get_rows(); 
        Matrix matrix = new Matrix(n, n+1); 

        for (int i = 0; i < n; i++) {
            double x = points.get(i, 0);
            double y = points.get(i, 1); 
            for (int j = 0; j < n; j++) {
                matrix.set(i, j, Math.pow(x, j)); 
            }
            matrix.set(i, n, y); 
        }

        switch (idx) {
            case 1:
                return spl_gaussian(matrix);
            case 2:
                return spl_gauss_jordan(matrix);
            case 3:
                return spl_inverse(matrix);
            case 4:
                return spl_cramer(matrix);
            default:
                throw new IllegalArgumentException("Invalid index.");
        }
    }

    public static double estimate_value(Matrix coefficients, double x) {
        double result = 0;
        for (int i = 0; i < coefficients.get_rows(); i++) {
            result += coefficients.get(i, 0) * Math.pow(x, i); 
        }
        return result;
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
            throw new IllegalArgumentException("atrix does not have a solution");
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