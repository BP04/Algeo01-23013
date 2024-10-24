import java.util.Scanner;
public class SPLInverse {
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

    public static Matrix basis(Matrix matrix){
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        Matrix temp = matrix.copy_matrix();
        int[] rows_position = new int[rows];

        for(int i = 0; i < rows; ++i){
            rows_position[i] = i;
        }

        for(int i = 0; i < rows; ++i){
            if(temp.get(i, i) == 0){
                for(int j = i + 1; j < rows; ++j){
                    if(temp.get(j, i) != 0){
                        swap_rows(temp, i, j);
                        int aux = rows_position[i];
                        rows_position[i] = rows_position[j];
                        rows_position[j] = aux;
                        break;
                    }
                }
            }

            double pivot = temp.get(i, i);
            if(pivot != 0){
                for(int j = i; j < cols; ++j){
                    temp.set(i, j, temp.get(i, j) / pivot);
                }
            }

            for(int k = i + 1; k < rows; ++k){
                double constant = temp.get(k, i);
                for(int j = i; j < cols; ++j){
                    temp.set(k, j, temp.get(k, j) - temp.get(i, j) * constant);
                }
            }
        }

        int used = -1;
        for(int i = 0; i < rows; ++i){
            boolean leading_one = false;
            for(int j = 0; j < cols; ++j){
                if(temp.get(i, j) == 1){
                    leading_one = true;
                    break;
                }
            }
            if(leading_one){
                used = i;
            }
        }

        Matrix result = new Matrix(used + 1, cols);

        for(int i = 0; i <= used; ++i){
            int index = rows_position[i];
            for(int j = 0; j < cols; ++j){
                result.set(i, j, matrix.get(index, j));
            }
        }

        return result;
    }
    
    public static void spl_inverse(Matrix matrix, Scanner scanner) {
        matrix = basis(matrix);
        
        int rows = matrix.get_rows();
        int cols = matrix.get_cols();

        if (cols != rows + 1) {
            throw new IllegalArgumentException("Matrix might not be augmented (last column should be constants).");
        }

        Matrix coefficients = new Matrix(rows, rows);
        Matrix constants = new Matrix(rows, 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                coefficients.set(i, j, matrix.get(i, j));
            }
            constants.set(i, 0, matrix.get(i, cols - 1));
        }

        double det = DeterminantRowReduction.determinant_row_reduction(coefficients);
        if (det == 0) {
            SPLGaussian.spl_gaussian(matrix, scanner);
        } else {
            Matrix inverse = InverseMatrixGaussJordan.inverse_matrix(coefficients);
            Matrix solution = inverse.multiply(constants);
            for (int i = 0; i < rows; i++) {
                System.out.print("x" + (i + 1) + " = " + solution.get(i, 0));
                if (i < rows - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
            Matrix.save_output_SPL(scanner, solution);
        }
    }
}
