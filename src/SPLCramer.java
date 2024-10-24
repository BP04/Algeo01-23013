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

    private static int find_non_zero(Matrix matrix, int row){
        int cols = matrix.get_cols();
        for(int j = 0; j < cols; ++j){
            if(matrix.get(row, j) != 0){
                return j;
            }
        }
        return -1;
    }

    public static Matrix basis(Matrix matrix){
        int rows = matrix.get_rows(), cols = matrix.get_cols();
        Matrix result = matrix.copy_matrix();
        Matrix temp = matrix.copy_matrix();
        int[] rows_position = new int[rows];

        for(int i = 0; i < rows; ++i){
            rows_position[i] = i;
        }

        for(int i = 0; i < rows; ++i){
            int other_row = i, minimum = find_non_zero(result, i);
            if(minimum == -1){
                minimum = cols + 1;
            }
            for(int k = i + 1; k < rows; ++k){
                int helper = find_non_zero(result, k);
                if(helper == -1){
                    helper = cols + 1;
                }
                if(helper < minimum){
                    minimum = helper;
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

        Matrix ret = new Matrix(used + 1, cols);

        for(int i = 0; i <= used; ++i){
            int index = rows_position[i];
            for(int j = 0; j < cols; ++j){
                ret.set(i, j, matrix.get(index, j));
            }
        }

        return ret;
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

    public static void spl_cramer(Matrix matrix, Scanner scanner) {
        int rows = matrix.get_rows(), cols = matrix.get_cols();

        if (rows != (cols-1)) {
            System.out.println("The Cramer method is used for square matrix, i.e. the number of rows is equal to the number of columns.");
            return;
        }

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
                if(i < cols - 2){
                    System.out.print(", ");
                }
                solution.set(i, 0, result); 
            }
            System.out.println("");
            Matrix.save_output_SPL(scanner, solution);
        }
    }
}
