public class InverseMatrixGaussJordan {
    public static Matrix inverse_matrix(Matrix matrix){
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        // Ensure number of rows is equal to number of columns
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix is not square and does not have an inverse.");
        }

        //Merge original matrix and identity matrix
        Matrix augmented_matrix = new Matrix(rows, 2 * cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Add original matrix
                augmented_matrix.set(i, j, matrix.get(i, j));
                // Add identity matrix
                if (i == j) {
                    augmented_matrix.set(i, j + cols, 1);
                }
                else {
                    augmented_matrix.set(i, j + cols, 0);
                }
            }
        }

        //Execute gaussian elimination
        augmented_matrix = GaussJordanElimination.gauss_jordan_elimination(augmented_matrix);

        // Check whether matrix has an inverse
        for (int i = 0; i < rows; i++) {
            if (augmented_matrix.get(i, i) != 1) {
                throw new IllegalArgumentException("Matrix is singular and does not have an inverse.");
            }
        }

        //Return inverse matrix
        Matrix inverse_matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++){
                inverse_matrix.set(i, j, augmented_matrix.get(i, j + cols));
            }
        }
        return inverse_matrix;
    }
}
