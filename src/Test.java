public class Test {

    public static void main(String[] args) {
        // Number of data points (rows) and number of features (3 independent variables + 1 dependent variable)
        int rows = 15;  // 15 data points
        int cols = 4;   // 3 independent variables (x1, x2, x3) and 1 dependent variable (y)

        // Create a matrix for the data
        Matrix matrix = new Matrix(rows, cols);

        // Data from the table (Nitrous Oxide y, Humidity x1, Temp x2, Pressure x3)
        // Fill in the matrix data as before:
        // Data point 1
        // Data point 1
        matrix.set(0, 0, 72.4); matrix.set(0, 1, 76.3); matrix.set(0, 2, 29.18); matrix.set(0, 3, 0.90);
        // Data point 2
        matrix.set(1, 0, 41.6); matrix.set(1, 1, 70.3); matrix.set(1, 2, 29.35); matrix.set(1, 3, 0.91);
        // Data point 3
        matrix.set(2, 0, 34.3); matrix.set(2, 1, 77.1); matrix.set(2, 2, 29.24); matrix.set(2, 3, 0.96);
        // Data point 4
        matrix.set(3, 0, 35.1); matrix.set(3, 1, 68.0); matrix.set(3, 2, 29.27); matrix.set(3, 3, 0.89);
        // Data point 5
        matrix.set(4, 0, 10.7); matrix.set(4, 1, 77.0); matrix.set(4, 2, 29.38); matrix.set(4, 3, 1.00);
        // Data point 6
        matrix.set(5, 0, 12.9); matrix.set(5, 1, 67.4); matrix.set(5, 2, 29.39); matrix.set(5, 3, 1.10);
        // Data point 7
        matrix.set(6, 0, 8.3); matrix.set(6, 1, 66.8); matrix.set(6, 2, 29.33); matrix.set(6, 3, 1.15);
        // Data point 8
        matrix.set(7, 0, 20.1); matrix.set(7, 1, 76.9); matrix.set(7, 2, 29.48); matrix.set(7, 3, 1.03);
        // Data point 9
        matrix.set(8, 0, 72.2); matrix.set(8, 1, 77.7); matrix.set(8, 2, 29.49); matrix.set(8, 3, 0.77);
        // Data point 10
        matrix.set(9, 0, 23.2); matrix.set(9, 1, 76.8); matrix.set(9, 2, 29.38); matrix.set(9, 3, 1.07);
        // Data point 11
        matrix.set(10, 0, 47.4); matrix.set(10, 1, 86.6); matrix.set(10, 2, 29.35); matrix.set(10, 3, 0.94);
        // Data point 12
        matrix.set(11, 0, 31.5); matrix.set(11, 1, 31.5); matrix.set(11, 2, 29.56); matrix.set(11, 3, 1.10);
        // Data point 13
        matrix.set(12, 0, 10.1); matrix.set(12, 1, 73.3); matrix.set(12, 2, 29.39); matrix.set(12, 3, 1.10);
        // Data point 14
        matrix.set(13, 0, 73.3); matrix.set(13, 1, 86.3); matrix.set(13, 2, 29.34); matrix.set(13, 3, 0.87);
        // Data point 15
        matrix.set(14, 0, 54.9); matrix.set(14, 1, 70.9); matrix.set(14, 2, 29.37); matrix.set(14, 3, 0.95);


        // Create a test case (xk) for prediction with similar independent variables (Humidity, Temp, Pressure)
        int pred_rows = 1;
        int pred_cols = 3;  // xk has the same independent variables as the original matrix (Humidity, Temp, Pressure)
        
        Matrix xk = new Matrix(pred_rows, pred_cols);
        
        // Set values for xk (new prediction point)
        // Let's test with some values for Humidity = 40, Temp = 70, Pressure = 30
        xk.set(0, 0, 40.0);  // Humidity
        xk.set(0, 1, 70.0);  // Temperature
        xk.set(0, 2, 30.0);  // Pressure
        
        // Call the multivariate quadratic regression method
        MultivariateQuadraticRegression.multiple_quadratic_regression(matrix, xk);
    }
}
