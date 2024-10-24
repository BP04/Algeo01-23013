public class GaussJordanElimination {
    private static int find_non_zero(Matrix matrix, int row){
        int cols = matrix.get_cols();
        for(int j = 0; j < cols; ++j){
            if(matrix.get(row, j) != 0){
                return j;
            }
        }
        return -1;
    }

    public static Matrix gauss_jordan_elimination(Matrix matrix){
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        //Execute gaussian elimination
        matrix = GaussianElimination.gaussian_elimination(matrix);

        //Execute backward elimination
        for(int i = 1; i < rows; i++){
            int non_zero_position = find_non_zero(matrix, i);

            if(non_zero_position != -1){
                double leadingCoefficient = matrix.get(i, non_zero_position);
                for (int k = 0; k < cols; k++) {
                    matrix.set(i, k, matrix.get(i, k) / leadingCoefficient); // Normalize the row
                }
                
                for(int j = 0; j < i; j++){
                    if (matrix.get(j, non_zero_position) != 0) {
                        double constants = (matrix.get(j, non_zero_position) / matrix.get(i, non_zero_position));
                        for(int k = 0; k < cols; k++) {
                            matrix.set(j, k, (matrix.get(j, k) - matrix.get(i, k) * constants));
                        }
                    }
                }
            }
        }

        // for(int i = 0; i < rows; ++i){
        //     for(int j = 0; j < cols; ++j){
        //         System.out.print((int)matrix.get(i, j) + " " );
        //     }
        //     System.out.println();
        // }

        return matrix;
    }

    // public static void main(String args[]){
    //     Matrix M = new Matrix(3, 7);
    //     M.set(0, 0, 0); M.set(0, 1, 1); M.set(0, 2, 0); M.set(0, 3, 0); M.set(0, 4, 1); M.set(0, 5, 0); M.set(0, 6, 2);
    //     M.set(1, 0, 0); M.set(1, 1, 0); M.set(1, 2, 0); M.set(1, 3, 1); M.set(1, 4, 1); M.set(1, 5, 0); M.set(1, 6, -1);
    //     M.set(2, 0, 0); M.set(2, 1, 1); M.set(2, 2, 0); M.set(2, 3, 0); M.set(2, 4, 0); M.set(2, 5, 1); M.set(2, 6, 1);

    //     M = gauss_jordan_elimination(M);
    //     M.print_matrix();
    // }
}
