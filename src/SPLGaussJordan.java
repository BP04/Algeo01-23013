import java.util.Scanner;
public class SPLGaussJordan {
    public static void spl_gauss_jordan(Matrix matrix, Scanner scanner) {
        int rows = matrix.get_rows(), cols = matrix.get_cols();

        // Execute the Gauss-Jordan Elimination function on matrix
        matrix = GaussJordanElimination.gauss_jordan_elimination(matrix);
        
        matrix.print_matrix();

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
            
            // Search for free variables
            int not_free = 0;
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
                else{
                    not_free = j;
                }
            }

            // Output the parametric solutions
            int free_index = 1;
            for (int i = 0; i < cols - 1; i++) {
                if (is_free_variable[i]) {
                    System.out.println("x" + (i + 1) + " = t" + (free_index));
                    free_index++;  
                } 
                else {
                    System.out.print("x" + (i + 1) + " = ");
                    for(int j = not_free + 1; j < cols - 1; ++j){
                        if(matrix.get(i, j) != 0){
                            System.out.print(-matrix.get(i, j) + "t" + (j - not_free) + " + ");
                        }
                    }
                    if(matrix.get(i, cols - 1) == 0){
                        System.out.print("0.0");
                    }
                    else{
                        System.out.print(matrix.get(i, cols - 1));
                    }
                    System.out.println();
                }
            }
        } 
        else {
            Matrix solution = new Matrix(rows, 1);
            for (int i = 0; i < cols - 1; i++) {
                System.out.print("x" + (i + 1) + " = " + matrix.get(i, cols - 1));
                if (i < cols - 2) {
                    System.out.print(", ");
                }
                solution.set(i, 0, matrix.get(i, cols - 1));
            }
            System.out.println();
            Matrix.save_output_SPL(scanner, solution);
        }
    }
}
