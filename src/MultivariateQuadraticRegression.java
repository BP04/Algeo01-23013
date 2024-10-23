import java.util.Scanner;

public class MultivariateQuadraticRegression {
    public static void multiple_quadratic_regression(Matrix matrix, Matrix xk) {
        int rows = matrix.get_rows(), cols = matrix.get_cols() - 1;
        int rows2 = xk.get_rows(), cols2 = xk.get_cols();
        // n > 1 checking
        if (cols <= 1) {
            throw new IllegalArgumentException("Number of terms must be greater than 1.");
        }

        // matrix and xk dimension  checking
        if (cols != cols2) {
            throw new IllegalArgumentException("Mismatch of input matrix and xk's number of features");
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

        // Columns combination as number of interactive variables
        int cols_comb = (cols * (cols - 1)) / 2;
        Matrix augmented_matrix = new Matrix(rows,(2 * cols) + cols_comb + 1);
        // Input matrix columns expansion
        // Fill augmented matrix's constants (1)
        for (int i = 0; i < rows; i++) {
            augmented_matrix.set(i, 0, 1);
        }
        // Fill augmented matrix's linear variable
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                augmented_matrix.set(i, j + 1, matrix.get(i, j));
            }
        }
        // Fill augmented matrix's squared variables
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                augmented_matrix.set(i, j + cols + 1, Math.pow(matrix.get(i, j), 2));
            }
        }
        // Fill augmented matrix's interaction variables
        for (int k = 0; k < rows; k++) {
            int idx = 0;
            for (int i = 0; i < cols - 1; i++) {
                for (int j = i + 1; j < cols; j++) {
                    augmented_matrix.set(k, 1 + (2 * cols) + idx, (matrix.get(k, i) * matrix.get(k, j)));
                    idx++;
                }
            }
        }
        // Fill output matrix (y)
        Matrix output = new Matrix(augmented_matrix.get_rows(), 1);
        for (int i = 0; i < rows; i++) {
            output.set(i, 0, matrix.get(i, cols));
        }

        // Columns combination as number of interactive variables
        int cols_comb2 = (cols2 * (cols2 - 1)) / 2;
        Matrix pred_matrix = new Matrix(rows2,(2 * cols2) + cols_comb2 + 1);
        // xk's columns expansion
        // Fill augmented matrix's constants (1)
        for (int i = 0; i < rows2; i++) {
            pred_matrix.set(i, 0, 1);
        }
        // Fill augmented matrix's linear variable
        for (int i = 0; i < rows2; i++) {
            for (int j = 0; j < cols2; j++) {
                pred_matrix.set(i, j + 1, xk.get(i, j));
            }
        }
        // Fill augmented matrix's squared variables
        for (int i = 0; i < rows2; i++) {
            for (int j = 0; j < cols2; j++) {
                pred_matrix.set(i, j + cols2 + 1, Math.pow(xk.get(i, j), 2));
            }
        }
        // Fill augmented matrix's interaction variables
        for (int k = 0; k < rows2; k++) {
            int idx = 0;
            for (int i = 0; i < cols2 - 1; i++) {
                for (int j = i + 1; j < cols2; j++) {
                    pred_matrix.set(k, 1 + (2 * cols2) + idx, (xk.get(k, i) * xk.get(k, j)));
                    idx++;
                }
            }
        }

        // Generate output variables
        int col = matrix.get_cols() - 1;
        String[] variables = new String[(2 * col) + cols_comb + 1];
        variables[0] = "c";
        // Linear variables
        for (int i = 1; i <= col; i++) {
            variables[i] = "x" + i;
        }
        // Squared variables
        for (int i = 1; i <= col; i++) {
            variables[i+col] = "x" + i + "^2";
        }
        // Interaction variables
        int idx = 1 + col + col;
        for (int i = 1; i <= col - 1; i++) {
            for (int j = i + 1; j <= col; j++) {
                variables[idx] = "x" + i + "x" + j;
                idx++;
            }
        }

        // Calculate each beta variables value using Ordinary Least Squares / Ridge Regression
        Matrix A = augmented_matrix.transpose_matrix();
        A = A.multiply(augmented_matrix);
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
        Matrix b = augmented_matrix.transpose_matrix();
        b = b.multiply(output);
        Matrix beta = A.multiply(b);

        // Output terms result
        System.out.println();
        System.out.println("f(x) = " + beta.get(0, 0) + " + ");
        System.out.print("       ");
        for (int i = 1; i < (2 * col) + cols_comb + 1; i++) {
            System.out.print(beta.get(i, 0) + variables[i]);
            if (i < (2 * col) + cols_comb) {
                System.out.print(" + ");
            }
            if (i % cols2 == 0) {
                System.out.println();
                System.out.print("       ");
            }
            //System.out.println(variables[i] + " = " + beta.get(i, 0));
        }
        System.out.println();

        // xk's prediction
        Matrix predictions = pred_matrix.multiply(beta);
        //System.out.println("Input predictions: ");
        if (rows2 == 1) {
            System.out.println("f(xk) = " + predictions.get(0, 0));
        }
        else {
            for (int i = 0; i < rows2; i++) {
                System.out.println("f(xk" + (i+1) + ") = " + predictions.get(i, 0));
            } 
        }
    }
}