public class GaussJordanElimination {
    public static Matrix gauss_jordan_elimination(Matrix matrix){
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        //Execute gaussian elimination
        matrix = GaussianElimination.gaussian_elimination(matrix);

        //Execute backward elimination
        for(int i = 1; i < rows; i++){
            for(int j = 0; j < i; j++){
                if (matrix.get(j, i) != 0) {
                    double constants = (matrix.get(j, i) / matrix.get(i, i));
                    for(int k = 0; k < cols; k++) {
                        matrix.set(j, k, (matrix.get(j, k) - matrix.get(i, k) * constants));
                    }
                }
            }
        }
        return matrix;
    }
}