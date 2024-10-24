public class SPLGaussian {
    public static void spl_gaussian(Matrix matrix) {
        int rows = matrix.get_rows();
        int cols = matrix.get_cols();
        matrix = GaussianElimination.gaussian_elimination(matrix);

        boolean has_contradiction = false;
        boolean has_all_zeros = false;
        for (int i = 0; i < Math.min(rows, cols - 1); i++) {
            boolean all_zero = true;
            for (int j = 0; j < cols-1; j++) {
                if(matrix.get(i, j) != 0){
                    all_zero = false;
                } 
            }
            if (all_zero && matrix.get(i, cols-1) == 0) {
                has_all_zeros = true;  
            }
            if (all_zero && matrix.get(i, cols-1) != 0) {
                has_contradiction = true;  
            }
        }

        if (has_contradiction) {
            System.out.println("Matrix does not have a solution.");
        } else if ((rows < (cols - 1)) || has_all_zeros) {
            SPLGaussJordan.spl_gauss_jordan(matrix);
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

    public static void main(String args[]){
        Matrix M = new Matrix(3, 7);
        M.set(0, 0, 0); M.set(0, 1, 1); M.set(0, 2, 0); M.set(0, 3, 0); M.set(0, 4, 1); M.set(0, 5, 0); M.set(0, 6, 2);
        M.set(1, 0, 0); M.set(1, 1, 0); M.set(1, 2, 0); M.set(1, 3, 1); M.set(1, 4, 1); M.set(1, 5, 0); M.set(1, 6, -1);
        M.set(2, 0, 0); M.set(2, 1, 1); M.set(2, 2, 0); M.set(2, 3, 0); M.set(2, 4, 0); M.set(2, 5, 1); M.set(2, 6, 1);

        spl_gaussian(M);
    }
}
