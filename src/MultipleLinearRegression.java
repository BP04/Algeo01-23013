import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MultipleLinearRegression {
    public static void matrix_regression_output_file(Scanner scanner, Matrix weights, Matrix prediction) {
        char save_option;
        String filename;
        File dir = new File("../test"); // Ensure this directory path is correct for your system
        
        // Prompt user whether to save output
        do {
            System.out.print("Apakah output ingin disimpan ke file? (y/n): ");
            save_option = scanner.next().charAt(0);
        } while (save_option != 'y' && save_option != 'Y' && save_option != 'n' && save_option != 'N');

        // If user wants to save the output
        if (save_option == 'y' || save_option == 'Y') {
            System.out.print("Masukkan nama file (tanpa .txt): ");
            filename = scanner.next();

            // Create directory if it doesn't exist
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                // Open file writer
                PrintWriter writer = new PrintWriter(new File(dir, filename + ".txt"));

                // Write the regression equation to the file
                writer.print("f(x) = ");
                writer.print(weights.get(0, 0) + " + ");
                for (int i = 1; i < weights.get_rows(); i++) {
                    writer.print(weights.get(i, 0) + "x" + i);
                    if (i < weights.get_rows() - 1) {
                        writer.print(" + ");
                    }
                }
                writer.println(); // Move to the next line

                // Write the predictions to the file
                for (int i = 0; i < prediction.get_rows(); i++) {
                    writer.println("f(xk" + (i+1) + ") = " + prediction.get(i, 0));
                }

                writer.close(); // Close the writer
                System.out.println("Output berhasil disimpan ke file: " + filename + ".txt");

            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat menulis ke file: " + e.getMessage());
            }
        }
    }

    public static void multiple_linear_regression(Matrix matrix, Matrix xk, Scanner scanner) {
        int rows = matrix.get_rows(), cols = matrix.get_cols() - 1;
        int rows2 = xk.get_rows(), cols2 = xk.get_cols();

        if (cols <= 0) {
            throw new IllegalArgumentException("Insufficient data features");
        }

        // matrix and xk dimension  checking
        if (cols != cols2) {
            throw new IllegalArgumentException("Mismatch of input matrix and xk's number of features");
        }

        Matrix x = new Matrix(rows, cols + 1);
        Matrix y = new Matrix(rows, 1);

        // matrix columns expansion
        for (int i = 0; i < rows; i++) {
            x.set(i, 0, 1);  
            for (int j = 1; j <= cols; j++) {
                x.set(i, j, matrix.get(i, j - 1));
            }
            y.set(i, 0, matrix.get(i, cols));
        }

        Matrix pred_matrix = new Matrix(rows2, cols2 + 1);
        for (int i = 0; i < rows2; i++) {
            pred_matrix.set(i, 0, 1);  
            for (int j = 1; j <= cols2; j++) {
                pred_matrix.set(i, j, xk.get(i, j - 1));
            }
        }

        // xk columsn expansion

        // Regularization's lambda initialization
        double lambda = 0.0;

        // Choose method choice
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

        // Continue calculation
        Matrix b = x.transpose_matrix();
        b = b.multiply(y);
        Matrix beta = A.multiply(b);

        // Output terms result
        System.out.println();
        System.out.print("f(x) = ");
        System.out.print(beta.get(0, 0) + " + ");
        for (int i = 1; i < beta.get_rows(); i++) {
            System.out.print(beta.get(i, 0) + "x" + i);
            if (i < beta.get_rows() - 1) {
                System.out.print(" + ");
            }
        }

        // Output predictions
        System.out.println();
        Matrix predictions = pred_matrix.multiply(beta);
        if (rows2 == 1) {
            System.out.println("f(xk) = " + predictions.get(0, 0));
        }
        else {
            for (int i = 0; i < rows2; i++) {
                System.out.println("f(xk" + (i+1) + ") = " + predictions.get(i, 0));
            } 
        }

        matrix_regression_output_file(scanner, beta, predictions);
    }
}
