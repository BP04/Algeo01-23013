public class SPLGaussian {
    public static void spl_gaussian(Matrix matrix) {
        int rows = matrix.get_rows();
        int cols = matrix.get_cols();
        matrix = GaussianElimination.gaussian_elimination(matrix);

        boolean has_contradiction = false;
        boolean has_all_zeros = false;
        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols-1; j++) {
                sum += matrix.get(i, j); 
            }
            if (sum == 0 && matrix.get(i, cols-1) == 0) {
                has_all_zeros = true;  
            }
            if (sum == 0 && matrix.get(i, cols-1) != 0) {
                has_contradiction = true;  
            }
        }
        if (has_contradiction) {
            System.out.println("Matrix does not have a solution.");
        } else if ((rows < (cols - 1)) || has_all_zeros) {
            System.out.println("Matrix has infinite solutions.");
            System.out.println("Parametric solution:");

            boolean[] is_free_variable = new boolean[cols - 1];
            
            for (int j = 0; j < cols - 1; j++) {
                boolean has_leading_one = false;
                for (int i = 0; i < rows; i++) {
                    if (matrix.get(i, j) == 1) {
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
        } else {
            Matrix solution = new Matrix(rows, 1);
            for (int i = rows-1; i >= 0; i--) {
                double sum = matrix.get(i, cols-1); 
                for (int j = i+1; j < rows; j++) {
                    sum -= matrix.get(i, j) * solution.get(j, 0);
                }
                double value = sum / matrix.get(i, i); 
                solution.set(i, 0, value); 
            }
            for (int i = 0; i < rows; i++) {
                System.out.print("x" + (i+1) + " = " + solution.get(i, 0));
                if (i < rows-1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}
