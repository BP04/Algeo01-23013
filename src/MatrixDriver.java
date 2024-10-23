public class MatrixDriver {
    public static void main(String[] args) {
        // Define a 3x3 matrix for testing
        Matrix matrix = new Matrix(3, 3);

        // Populate the matrix with values
        matrix.set(0, 0, 4);
        matrix.set(0, 1, -3);
        matrix.set(0, 2, 2);

        matrix.set(1, 0, 4);
        matrix.set(1, 1, -3);
        matrix.set(1, 2, 2);

        matrix.set(2, 0, -1);
        matrix.set(2, 1, 1);
        matrix.set(2, 2, 2);

        System.out.println("Initial Matrix:");
        matrix.print_matrix();
        
        System.out.println(DeterminantRowReduction.determinant_row_reduction(matrix));
    }
}