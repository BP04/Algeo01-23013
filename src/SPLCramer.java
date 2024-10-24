import java.util.Scanner;
public class SPLCramer {
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

    private static double determinant_n(Matrix matrix, int n) {
        int rows = matrix.get_rows(), cols = matrix.get_cols();

        Matrix matrix_n = new Matrix(rows, cols-1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                if (j == n) {
                    matrix_n.set(i, j, matrix.get(i, cols-1));
                }
                else {
                    matrix_n.set(i, j, matrix.get(i, j));
                }
            }
        }
        return DeterminantRowReduction.determinant_row_reduction(matrix_n);
    }

    public static void spl_cramer(Matrix matrix) {
        Scanner scanner = new Scanner(System.in);
        matrix = basis(matrix);

        int rows = matrix.get_rows(), cols = matrix.get_cols();
        Matrix matrix_det = new Matrix(rows, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix_det.set(i, j, matrix.get(i, j));
            }
        }
        double det = DeterminantRowReduction.determinant_row_reduction(matrix_det);

        // Case: not a square matrix 
        if (rows != (cols-1)) {
            System.out.println("The Cramer method is used for square matrix, i.e. the number of rows is equal to the number of columns.");
        }
        // Case: infinite solutions and no solution
        // A determinant of 0 can result in infinite solutions or no solution
        else if (det == 0) { 
            boolean has_zero_subdet = false;
            for (int i = 0; i < cols-1; i++) {
                if (determinant_n(matrix, i) != 0) {
                    has_zero_subdet = true;
                    break;
                }
            }

            if (has_zero_subdet) {
                System.out.println("Matrix does not have a solution.");
            }
            else {
                System.out.println("Matrix has infinite solutions.");
            }
        }
        // Case: unique or trivial solution
        // A determinant of other than 0 result in a unique set of solution
        else {
            Matrix solution = new Matrix(rows, 1);
            for (int i = 0; i < cols-1; i++) {
                double result = (determinant_n(matrix, i) / det);
                System.out.print("x" + (i+1) + " = " + result);
                if (i < cols-2) {
                    System.out.print(", ");
                }
                solution.set(i, 0, result); 
            }
            System.out.println("");
            Matrix.save_output_SPL(scanner, solution);
        }
    }
}
