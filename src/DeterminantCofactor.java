public class DeterminantCofactor {

    //Finding minor of matrix at (row, col) (remove given row and col from matrix)
    private static Matrix get_minor(Matrix matrix, int row, int col){
        int rows = matrix.get_rows(), cols = matrix.get_cols(), minor_row = 0, minor_col = 0;
        Matrix result = new Matrix(rows - 1, cols - 1);

        for(int i = 0; i < rows; ++i){
            if(i == row){
                continue;
            }
            minor_col = 0;
            for(int j = 0; j < cols; ++j){
                if(j == col){
                    continue;
                }
                result.set(minor_row, minor_col, matrix.get(i, j));
                minor_col++;
            }
            minor_row++;
        }

        return result;
    }

    //Calculate determinant using cofactor method
    private static double determinant_cofactor(Matrix matrix){
        //Determinant of 1x1 matrix
        if(matrix.get_rows() == 1){
            return matrix.get(0, 0);
        }

        //Determinant of 2x2 matrix
        if(matrix.get_rows() == 2){
            return matrix.get(0, 0) * matrix.get(1, 1) - matrix.get(0, 1) * matrix.get(1, 0);
        }

        double det = 0;
        for(int j = 0; j < matrix.get_cols(); ++j){
            det += matrix.get(0, j) * cofactor(matrix, 0, j);
        }
        
        return det;
    }

    //Calculate cofactor of (row, col) in matrix
    public static double cofactor(Matrix matrix, int row, int col){
        if(matrix.get_rows() != matrix.get_cols()){
            throw new IllegalArgumentException("Can't calculate cofactor: number of rows and columns of matrix are not equal.");
        }

        return determinant_cofactor(get_minor(matrix, row, col)) * ((row + col) % 2 == 1 ? -1 : 1);
    }

    //Calculate determinant of matrix
    public static double determinant(Matrix matrix){
        if(matrix.get_rows() != matrix.get_cols()){
            throw new IllegalArgumentException("Can't calculate determinant: number of rows and columns of matrix are not equal.");
        }
        return determinant_cofactor(matrix);
    }
}
