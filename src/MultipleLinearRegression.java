import java.util.Scanner;

public class MultipleLinearRegression {
    public static void multiple_linear_regression(Matrix matrix) {
        int rows = matrix.get_rows();
        int cols = matrix.get_cols() - 1;

        Matrix x = new Matrix(rows, cols + 1);
        Matrix y = new Matrix(rows, 1);

        for (int i = 0; i < rows; i++) {
            x.set(i, 0, 1);  
            for (int j = 1; j <= cols; j++) {
                x.set(i, j, matrix.get(i, j - 1));
            }
            y.set(i, 0, matrix.get(i, cols));
        }

        // Regularization's lambda initialization
        double lambda = 0.0;

        // Choose method choice
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose regression method number:");
        System.out.println("1. Ordinary Least Squares (OLS)");
        System.out.println("2. Ridge Regression");

        int method_choice = scanner.nextInt();
        while ((method_choice != 1) && (method_choice != 2)) {
            System.out.println("Option not available, please enter 1 or 2.");
            method_choice = scanner.nextInt();
        }

        // Sets lambda = 0 for OLS method
        if (method_choice == 1) {
            lambda = 0.0;
        }
        // Sets desired lambda value for Ridge Regression method
        else if (method_choice == 2) {
            System.out.println("Please enter a lambda value for Ridge Regression Method Calculation:");
            lambda = scanner.nextDouble();
        }

        // Calculate each beta variables value using Ordinary Least Squares / Ridge Regression
        Matrix A = x.transpose_matrix();
        A = A.multiply(x);
        // Regularization
        for (int i = 0; i < A.get_rows(); i++) {
            A.set(i, i, A.get(i, i) + lambda); 
        }
 
        boolean error_caught = false;
        // Error catching in case of OLS method failuire
        try {
            A = InverseMatrixDeterminant.inverse_with_determinant(A);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Try using Ridge Regression Method.");
            System.out.println();
            error_caught = true;
         }
 
        // Input lambda when OLS method fails
        if (error_caught) {
            System.out.println("Please enter a lambda value for Ridge Regression Method Calculation:");
            lambda = scanner.nextDouble();
            for (int i = 0; i < A.get_rows(); i++) {
                A.set(i, i, A.get(i, i) + lambda); 
            }
            A = InverseMatrixDeterminant.inverse_with_determinant(A);
        }   
 
        // Close scanner object
        scanner.close();

        // Continue calculation
        Matrix b = x.transpose_matrix();
        b = b.multiply(y);
        Matrix beta = A.multiply(b);

        // Output terms result
        System.out.println();
        System.out.println("Regression. terms:");
        System.out.println("c" + " = " + beta.get(0, 0));
        for (int i = 1; i < beta.get_rows(); i++) {
            System.out.println("x" + (i) + " = " + beta.get(i, 0));
        }

        // Output predictions
        System.out.println();
        Matrix predictions = x.multiply(beta);
        System.out.println("Input predictions: ");
        for (int i = 0; i < rows; i++) {
            System.out.print((i+1) + ". f" + (i+1) + "(");
            System.out.print("c, ");
            for (int j = 1; j < beta.get_rows(); j++) {
                System.out.print("x" + (j));
                if (j < beta.get_rows() - 1) {
                    System.out.print(", ");
                }
                else {
                    System.out.print(") = ");
                }
            }
            System.out.println(predictions.get(i, 0));
        }
    }
}
