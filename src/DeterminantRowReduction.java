public class DeterminantRowReduction {
    private static void swap_rows(Matrix matrix, int row1, int row2) {
        System.out.println(row1 + " " + row2);
        double[] temp = matrix.copy_row(row1);
        int cols = matrix.get_cols();
        for(int j = 0; j < cols; ++j){
            matrix.set(row1, j, matrix.get(row2, j));
        }
        for(int j = 0; j < cols; ++j){
            matrix.set(row2, j, temp[j]);
        }
    }

    public static double determinant_row_reduction(Matrix matrix){
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        Matrix matrix_copy = matrix.copy_matrix();
        double result = 1;

        for(int i = 0; i < rows; ++i){
            if(matrix_copy.get(i, i) == 0){
                for(int j = i + 1; j < rows; ++j){
                    if(matrix_copy.get(j, i) != 0){
                        result *= -1.0;
                        swap_rows(matrix_copy, i, j);
                        break;
                    }
                }
            }

            double pivot = matrix_copy.get(i, i);
            if(pivot == 0){
                return 0;
            }
            result *= pivot;
            
            for(int k = i + 1; k < rows; ++k){
                double constant = matrix_copy.get(k, i) / matrix_copy.get(i, i);
                for(int j = i; j < cols; ++j){
                    matrix_copy.set(k, j, matrix_copy.get(k, j) - matrix_copy.get(i, j) * constant);
                }
            }
        }

        return result;
    }
}
