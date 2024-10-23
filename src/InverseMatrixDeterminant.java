public class InverseMatrixDeterminant {
    public static Matrix inverse_with_determinant(Matrix matrix){
        int rows = matrix.get_rows();

        double det = DeterminantRowReduction.determinant_row_reduction(matrix);
        if (det == 0){
            throw new IllegalArgumentException("Matrix has no inverse, singular matrix");
        } 
        if (rows == 2){
            return inverse_2x2(matrix);
        }

        Matrix matrix_cofactor = new Matrix(rows, rows);
        for (int i = 0; i < matrix_cofactor.get_rows(); i++) {
            for (int j = 0; j < matrix_cofactor.get_cols(); j++) {
                double cofac = DeterminantCofactor.cofactor(matrix, i, j);
                matrix_cofactor.set(i, j, cofac);
            }
        }
        Matrix adjoint = matrix_cofactor.transpose_matrix();
        Matrix inverse = adjoint.multiply_by_constant(1/det);
        return inverse;
    }

    private static Matrix inverse_2x2 (Matrix matrix){
        double det = DeterminantCofactor.determinant(matrix);
        Matrix inverse = new Matrix(2, 2);
        inverse.set(0, 0, matrix.get(1,1)/det);
        inverse.set(0, 1, matrix.get(0, 1)/det*(-1));
        inverse.set(1, 0, matrix.get(1, 0)/det*(-1));
        inverse.set(1, 1, matrix.get(0,0)/det);
        return inverse;
    }
}
