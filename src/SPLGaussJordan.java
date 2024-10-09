public class SPLGaussJordan {
    public static void spl_gauss_jordan(Matrix matrix) {
        int rows = matrix.get_rows(), cols = matrix.get_cols();

        // Execute the Gauss-Jordan Elimination function on matrix
        matrix = GaussJordanElimination.gauss_jordan_elimination(matrix);

        boolean has_contradiction = false;
        boolean has_all_zeros = false;
        // Infinite solutions and no solution case checking
        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols - 1; j++) {
                sum += matrix.get(i, j);
            }
            // Case: infinite solutions (has at least one row of zeros)
            if ((sum == 0) && (matrix.get(i, cols-1) == 0)) {
                has_all_zeros = true;
            }   
            // Case: no solution (has at least one row of contradiction, i.e. all zeros except for the last column)
            if ((sum == 0) && (matrix.get(i, cols-1) != 0)) {
                has_contradiction = true;
            }   
        }

        // Calculation output
        if (has_contradiction) {
            System.out.println("Matrix does not have a solution.");
        }
        else if ((rows < (cols-1)) || (has_all_zeros)) {
            System.out.println("Matrix has infinite solutions.");
        }
        else {
            for (int i = 0; i < rows; i++) {
                System.out.print("x" + (i+1) + " = " + matrix.get(i, cols-1));
                if (i < rows - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }
}
