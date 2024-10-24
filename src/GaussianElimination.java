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

    private static int find_non_zero(Matrix matrix, int row){
        int cols = matrix.get_cols();
        for(int j = 0; j < cols; ++j){
            if(matrix.get(row, j) != 0){
                return j;
            }
        }
        return -1;
    }

    public static Matrix gaussian_elimination(Matrix matrix){
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        Matrix result = matrix.copy_matrix();

        for(int i = 0; i < rows; ++i){
            int other_row = i, minimum = find_non_zero(result, i);
            if(minimum == -1){
                minimum = cols + 1;
            }
            for(int k = i + 1; k < rows; ++k){
                int temp = find_non_zero(result, k);
                if(temp == -1){
                    temp = cols + 1;
                }
                if(temp < minimum){
                    minimum = temp;
                    other_row = k;
                }
            }

            if(other_row != -1){
                swap_rows(result, i, other_row);
            }

            int non_zero_position = find_non_zero(result, i);

            if(non_zero_position != -1){
                double pivot = result.get(i, non_zero_position);
                if(pivot != 0){
                    for(int j = 0; j < cols; ++j){
                        result.set(i, j, result.get(i, j) / pivot);
                    }
                }
                
                for(int k = i + 1; k < rows; ++k){
                    double constant = result.get(k, non_zero_position);
                    for(int j = non_zero_position; j < cols; ++j){
                        result.set(k, j, result.get(k, j) - result.get(i, j) * constant);
                    }
                }
            }
        }

        return result;
    }
    
    // public static void main(String args[]){
    //     Matrix M = new Matrix(3, 7);
    //     M.set(0, 0, 0); M.set(0, 1, 1); M.set(0, 2, 0); M.set(0, 3, 0); M.set(0, 4, 1); M.set(0, 5, 0); M.set(0, 6, 2);
    //     M.set(1, 0, 0); M.set(1, 1, 0); M.set(1, 2, 0); M.set(1, 3, 1); M.set(1, 4, 1); M.set(1, 5, 0); M.set(1, 6, -1);
    //     M.set(2, 0, 0); M.set(2, 1, 1); M.set(2, 2, 0); M.set(2, 3, 0); M.set(2, 4, 0); M.set(2, 5, 1); M.set(2, 6, 1);

    //     M = gaussian_elimination(M);
    //     M.print_matrix();
    // }
}