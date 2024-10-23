public class SPLInverse {
    public static void spl_inverse(Matrix matrix) {
        int rows = matrix.get_rows();
        int cols = matrix.get_cols();
        
        if (cols != rows + 1) {
            throw new IllegalArgumentException("Matrix might not be augmented (last column should be constants).");
        }

        Matrix coefficients = new Matrix(rows, rows);
        Matrix constants = new Matrix(rows, 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                coefficients.set(i, j, matrix.get(i, j));
            }
            constants.set(i, 0, matrix.get(i, cols - 1));
        }

        double det = DeterminantRowReduction.determinant_row_reduction(coefficients);
        if (det == 0) {
            SPLGaussian.spl_gaussian(matrix);
        } else {
            Matrix inverse = InverseMatrixGaussJordan.inverse_matrix(coefficients);
            Matrix solution = inverse.multiply(constants);
            for (int i = 0; i < rows; i++) {
                System.out.print("x" + (i + 1) + " = " + solution.get(i, 0));
                if (i < rows - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}
