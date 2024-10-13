public class SPLInverse {
    public static void spl_inverse(Matrix matrix) {
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

        double det = DeterminantRowReduction.determinant_row_reduction(coefficients);
        if (det == 0) {
            matrix = GaussJordanElimination.gauss_jordan_elimination(matrix);
            boolean has_contradiction = false;
            boolean has_all_zeros = false;
            for (int i = 0; i < rows; i++) {
                double sum = 0;
                for (int j = 0; j < cols-1; j++) {
                    sum += matrix.get(i, j); 
                }
                if (sum == 0 && matrix.get(i, cols - 1) == 0) {
                    has_all_zeros = true;  
                }
                if (sum == 0 && matrix.get(i, cols - 1) != 0) {
                    has_contradiction = true;  
                }
            }
            if (has_contradiction) {
                System.out.println("Matrix does not have a solution.");
            } else if (has_all_zeros) {
                System.out.println("Matrix has infinite solutions.");
            }

        } else {
            Matrix inverse = InverseMatrixGaussJordan.inverse_matrix(coefficients);
            Matrix solution = inverse.multiply(constants);
            for (int i = 0; i < rows; i++) {
                System.out.print("x" + (i + 1) + " = " + solution.get(i, 0));
                if (i < rows-1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}