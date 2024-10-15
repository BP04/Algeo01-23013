public class SPLCramer {
    private static double determinant_n(Matrix matrix, int n) {
        int rows = matrix.get_rows(), cols = matrix.get_cols();

        Matrix matrix_n = new Matrix(rows, cols-1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                if (j == n) {
                    matrix_n.set(i, j, matrix.get(i, cols-1));
                }
                else {
                    matrix_n.set(i, j, matrix.get(i, j));
                }
            }
        }
        return DeterminantRowReduction.determinant_row_reduction(matrix_n);
    }

    public static void spl_cramer(Matrix matrix) {
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        Matrix matrix_det = new Matrix(rows, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix_det.set(i, j, matrix.get(i, j));
            }
        }
        double det = DeterminantRowReduction.determinant_row_reduction(matrix_det);

        // Case: not a square matrix
        if (rows != (cols-1)) {
            System.out.println("The Cramer method is used for square matrix, i.e. the number of rows is equal to the number of columns.");
        }
        // Case: infinite solutions and no solution
        // A determinant of 0 can result in infinite solutions or no solution
        else if (det == 0) { 
            boolean has_zero_subdet = false;
            for (int i = 0; i < cols-1; i++) {
                if (determinant_n(matrix, i) != 0) {
                    has_zero_subdet = true;
                    break;
                }
            }

            if (has_zero_subdet) {
                System.out.println("Matrix does not have a solution.");
            }
            else {
                System.out.println("Matrix has infinite solutions.");
            }
        }
        // Case: unique or trivial solution
        // A determinant of other than 0 result in a unique set of solution
        else {
            for (int i = 0; i < cols-1; i++) {
                double result = (determinant_n(matrix, i) / det);
                System.out.print("x" + (i+1) + " = " + result);
                if (i < cols-2) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }
}