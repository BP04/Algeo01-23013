public class SPLGaussJordan {
    public static void spl_gauss_jordan(Matrix matrix) {
        int rows = matrix.get_rows(), cols = matrix.get_cols();

        // Execute the Gauss-Jordan Elimination function on matrix
        matrix = GaussJordanElimination.gauss_jordan_elimination(matrix);

        // for(int i = 0; i < rows; ++i){
        //     for(int j = 0; j < cols; ++j){
        //         System.out.print((int)matrix.get(i, j) + " ");
        //     }
        //     System.out.println();
        // }

        boolean has_contradiction = false;
        boolean has_all_zeros = false;
        
        // Infinite solutions and no solution case checking
        for (int i = 0; i < Math.min(rows, cols - 1); i++) {
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
            System.out.println("Matrix has infinite solutions.");
            System.out.println("Parametric solution:");

            boolean[] is_free_variable = new boolean[cols - 1];
            for(int j = 0; j < cols - 1; ++j){
                is_free_variable[j] = true;
            }
            
            // Search for free variables
            for (int i = 0; i < rows; i++) {
                boolean has_leading_one = false;
                int one_position = -1;
                for (int j = 0; j < cols - 1; j++) {
                    if (matrix.get(i, j) == 1) {
                        // Check if there's a already a leading one in the same column
                        has_leading_one = true;
                        one_position = j;
                        break;
                    }
                }
                if (has_leading_one) {
                    is_free_variable[one_position] = false;
                }
            }

            // Output the parametric solutions
            int free_index = 1, row_index = 0;
            for (int i = 0; i < cols - 1; i++) {
                if (is_free_variable[i]) {
                    System.out.println("x" + (i + 1) + " = t" + (free_index));
                    free_index++;  
                } 
                else {
                    System.out.print("x" + (i + 1) + " = ");
                    int free_count = 0;
                    for(int j = 0; j < cols - 1; ++j){
                        if(!is_free_variable[j]){
                            free_count++;
                            continue;
                        }
                        if(matrix.get(row_index, j) != 0){
                            System.out.print(-matrix.get(row_index, j) + "t" + (free_count) + " + ");
                            free_count++;
                        }
                    }
                    if(matrix.get(row_index, cols - 1) != 0){
                        System.out.print(matrix.get(row_index, cols - 1));
                    }
                    System.out.println();
                    row_index++;
                }
            }
        } 
        else {
            for (int i = 0; i < cols - 1; i++) {
                System.out.print("x" + (i + 1) + " = " + matrix.get(i, cols - 1));
                if (i < cols - 2) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String args[]){
        Matrix M = new Matrix(3, 7);
        M.set(0, 0, 0); M.set(0, 1, 1); M.set(0, 2, 0); M.set(0, 3, 0); M.set(0, 4, 1); M.set(0, 5, 0); M.set(0, 6, 2);
        M.set(1, 0, 0); M.set(1, 1, 0); M.set(1, 2, 0); M.set(1, 3, 1); M.set(1, 4, 1); M.set(1, 5, 0); M.set(1, 6, -1);
        M.set(2, 0, 0); M.set(2, 1, 1); M.set(2, 2, 0); M.set(2, 3, 0); M.set(2, 4, 0); M.set(2, 5, 1); M.set(2, 6, 1);

        spl_gauss_jordan(M);
    }
}
