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
        } else if (has_all_zeros) {
            System.out.println("Matrix has infinite solutions.");
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