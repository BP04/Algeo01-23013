public class SPLGaussJordan {
    public static void spl_gauss_jordan(Matrix matrix) {
        int rows = matrix.get_rows(), cols = matrix.get_cols();

        // Execute the Gauss-Jordan Elimination function on matrix
        matrix = GaussJordanElimination.gauss_jordan_elimination(matrix);
        
        boolean has_contradiction = false;
        boolean has_all_zeros = false;
        
        // Infinite solutions and no solution case checking
        for (int i = 0; i < rows; i++) {
            boolean is_all_zero = true;
            for (int j = 0; j < cols - 1; j++) {
                if (matrix.get(i, j) != 0) {
                    is_all_zero = false;
                }
            }
            // Case: infinite solutions (has at least one row of zeros)
            if ((is_all_zero) && (matrix.get(i, cols - 1) == 0)) {
                has_all_zeros = true;
            }
            // Case: no solution (has at least one row of contradiction, i.e. all zeros except for the last column)
            if ((is_all_zero) && (matrix.get(i, cols - 1) != 0)) {
                has_contradiction = true;
            }
        }

        // Calculation output
        if (has_contradiction) {
            System.out.println("Matrix does not have a solution.");
        } 
        else if ((rows < (cols - 1)) || has_all_zeros) {

            boolean[] is_free_variable = new boolean[cols - 1];
            
            // Search for free variables
            for (int j = 0; j < cols - 1; j++) {
                boolean has_leading_one = false;
                for (int i = 0; i < rows; i++) {
                    if (matrix.get(i, j) == 1) {
                        // Check if there's a already a leading one in the same column
                        boolean is_leading_one = true;
                        for (int k = 0; k < j; k++) {
                            if (matrix.get(i, k) == 1) {
                                is_leading_one = false;
                            }
                        }
                        if (is_leading_one) {
                            has_leading_one = true;
                            break;
                        }
                    }
                }
                if (!has_leading_one) {
                    is_free_variable[j] = true;
                }
            }

            // Output the parametric solutions
            for (int i = 0; i < cols - 1; i++) {
                if (is_free_variable[i]) {
                    System.out.println("x" + (i + 1) + " = t" + (i + 1));  
                } 
                else {
                    System.out.print("x" + (i + 1) + " = ");
                    if (matrix.get(i, cols - 1) != 0) {
                        System.out.print(matrix.get(i, cols - 1));
                    }
                    for (int j = 0; j < cols - 1; j++) {
                        if (is_free_variable[j] && matrix.get(i, j) != 0) {
                            System.out.print(" - ");
                            System.out.print(matrix.get(i, j) + " * t" + (j + 1));
                        }
                    }
                    System.out.println();
                }
            }
        } 
        else {
            for (int i = 0; i < rows; i++) {
                System.out.print("x" + (i + 1) + " = " + matrix.get(i, cols - 1));
                if (i < rows - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}
