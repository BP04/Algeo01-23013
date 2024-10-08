public class GaussianElimination {
    private static void swap_rows(Matrix matrix, int row1, int row2) {
        double[] temp = matrix.copy_row(row1);
        int cols = matrix.get_cols();
        for(int j = 0; j < cols; ++j){
            matrix.set(row1, j, matrix.get(row2, j));
        }
        for(int j = 0; j < cols; ++j){
            matrix.set(row2, j, temp[j]);
        }
    }

    public static Matrix gaussian_elimination(Matrix matrix){
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        Matrix result = matrix.copy_matrix();

        for(int i = 0; i < rows; ++i){
            if(result.get(i, i) == 0){
                for(int j = i + 1; j < rows; ++j){
                    if(result.get(j, i) != 0){
                        swap_rows(result, i, j);
                    }
                }
            }

            double pivot = result.get(i, i);
            if(pivot != 0){
                for(int j = i; j < cols; ++j){
                    result.set(i, j, result.get(i, j) / pivot);
                }
            }

            
            
            for(int k = i + 1; k < rows; ++k){
                double constant = result.get(k, i);
                for(int j = i; j < cols; ++j){
                    result.set(k, j, result.get(k, j) - result.get(i, j) * constant);
                }
            }
        }

        return result;
    }
}