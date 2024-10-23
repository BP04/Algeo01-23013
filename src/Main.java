import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("MENU");
            System.out.println("1. Sistem Persamaan Linier\n" 
            + "2. Determinan\n" 
            + "3. Matriks Balikan\n" 
            + "4. Interpolasi Polinom\n"
            + "5. Interpolasi Bicubic Spline\n"
            + "6. Regresi Linier dan Kuadratik Berganda\n"
            + "7. Interpolasi Gambar\n"
            + "8. Keluar");
            System.out.print("Menu yang ingin diakses: ");
            int option = scanner.nextInt();
            
            while (option > 8 || option < 1){
                System.out.print("Menu yang ingin diakses: ");
                option = scanner.nextInt();
            }
            
            switch (option){
                case 1: // SPL
                    System.out.println("1. Metode eliminasi Gauss\n" 
                    + "2. Metode eliminasi Gauss-Jordan\n" 
                    + "3. Matriks balikan\n" 
                    + "4. Kaidah Cramer");
                    System.out.print("Sub-menu yang ingin diakses: ");
                    int sub_option = scanner.nextInt();
                    
                    while (sub_option < 1 || sub_option > 4){
                        System.out.print("Sub-menu yang ingin diakses: ");
                        sub_option = scanner.nextInt();
                    }
                    System.out.println("1. Input dari keyboard\n" 
                    + "2. Input dari file");
                    System.out.print("Cara input matrix: ");
                    int input_from = scanner.nextInt();
                    
                    while (input_from < 1 || input_from > 2){
                        System.out.print("Cara input matrix: ");
                        input_from = scanner.nextInt();
                    }
                    Matrix mat;
                    if (input_from == 1){
                        int rows = scanner.nextInt();
                        int cols = scanner.nextInt();
                        scanner.nextLine();
                        Matrix a = input_matrix(rows, cols);
                        Matrix b = input_matrix(rows, 1);
                        mat = augment_matrix(a, b);
                    } else {
                        mat = input_matrix_file();
                    }
                    if (sub_option == 1){ // gauss
                        SPLGaussian.spl_gaussian(mat);
                    } else if (sub_option == 2){ // gauss-jordan
                        SPLGaussJordan.spl_gauss_jordan(mat); 
                    } else if (sub_option == 3){ //inverse
                        SPLInverse.spl_inverse(mat);
                    } else { //sub_option == 4, cramer
                       SPLCramer.spl_cramer(mat);
                    }
                    break;
                    
                case 2: //determinan
                    System.out.println("1. Metode eliminasi Gauss-Jordan\n" 
                    + "2. Matriks balikan");
                    System.out.print("Sub-menu yang ingin diakses: ");
                    sub_option = scanner.nextInt();
                    
                    while (sub_option < 1 || sub_option > 2){
                        System.out.print("Sub-menu yang ingin diakses: ");
                        sub_option = scanner.nextInt();
                    }

                    System.out.println("1. Input dari keyboard\n" 
                    + "2. Input dari file");
                    System.out.print("Cara input matrix: ");
                    input_from = scanner.nextInt();
                    
                    while (input_from < 1 || input_from > 2){
                        System.out.print("Cara input matrix: ");
                        input_from = scanner.nextInt();
                    }
                    if (input_from == 1){
                        int rows = scanner.nextInt();
                        scanner.nextLine();
                        mat = input_matrix(rows, rows);
                    } else {
                        mat = input_matrix_file();
                    }

                    if (sub_option == 1){ //gauss-jordan
                        double det = DeterminantCofactor.determinant(mat);
                        System.out.println("Determinan: " + det);
                    } else { //sub_option == 2, invers
                        double det = DeterminantRowReduction.determinant_row_reduction(mat);
                        System.out.println("Determinan: " + det);
                    }
                    break;

                case 3: //invers matrix
                System.out.println("1. Operasi Baris Elementer (OBE)\n" 
                + "2. Matriks Adjoin");
                System.out.print("Sub-menu yang ingin diakses: ");
                sub_option = scanner.nextInt();
                
                while (sub_option < 1 || sub_option > 2){
                    System.out.print("Sub-menu yang ingin diakses: ");
                    sub_option = scanner.nextInt();
                }

                System.out.println("1. Input dari keyboard\n" 
                    + "2. Input dari file");
                System.out.print("Cara input matrix: ");
                input_from = scanner.nextInt();
                
                while (input_from < 1 || input_from > 2){
                    System.out.print("Cara input matrix: ");
                    input_from = scanner.nextInt();
                }
                if (input_from == 1){
                    int rows = scanner.nextInt();
                    int cols = scanner.nextInt();
                    scanner.nextLine();
                    mat = input_matrix(rows, cols);
                } else {
                    mat = input_matrix_file();
                }

                if (sub_option == 1){ // obe
                    Matrix inverse_mat = InverseMatrixGaussJordan.inverse_matrix(mat);
                    inverse_mat.print_matrix();

                } else { //sub_option == 2, adjoin
                    Matrix inverse_mat = InverseMatrixDeterminant.inverse_with_determinant(mat);
                    inverse_mat.print_matrix();
                }                    
                break;

            case 4: //interpolasi polinom
                System.out.println("1. Metode eliminasi Gauss\n" 
                + "2. Metode eliminasi Gauss-Jordan\n" 
                + "3. Matriks balikan\n" 
                + "4. Kaidah Cramer");
                System.out.print("Sub-menu yang ingin diakses: ");
                sub_option = scanner.nextInt();
                
                while (sub_option < 1 || sub_option > 4){
                    System.out.print("Sub-menu yang ingin diakses: ");
                    sub_option = scanner.nextInt();
                }    
                System.out.println("1. Input dari keyboard\n" 
                    + "2. Input dari file");
                System.out.print("Cara input matrix: ");
                input_from = scanner.nextInt();
                
                while (input_from < 1 || input_from > 2){
                    System.out.print("Cara input matrix: ");
                    input_from = scanner.nextInt();
                }
                double x = 0;
                if (input_from == 1){
                    int rows = scanner.nextInt();
                    scanner.nextLine();
                    mat = input_matrix(rows, 2);   
                    System.out.print("Nilai x yang ingin ditaksir: ");
                    x = scanner.nextDouble();
                } else {    // x to find ga kebaca
                    mat = new Matrix(0, 0);
                    Double[] a = new Double[1];  
                    input_matrix_file_interpolation(mat, a);
                    x = a[0];
                }
                try {
                    Matrix coefficients = PolynomialInterpolation.polynomial_interpolation(mat, sub_option);
                    PolynomialInterpolation.estimate_value(coefficients, x); 
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
                break;

            case 5:
                System.out.println("interpolasi bicubic spline");
                break;

            case 6: // regresi linier dan kuadratik berganda
                System.out.println("1. Regresi linier\n" 
                + "2. Regresi kuadratik berganda");
                System.out.print("Sub-menu yang ingin diakses: ");
                sub_option = scanner.nextInt();
                
                while (sub_option < 1 || sub_option > 2){
                    System.out.print("Sub-menu yang ingin diakses: ");
                    sub_option = scanner.nextInt();
                }

                System.out.println("1. Input dari keyboard\n" 
                    + "2. Input dari file");
                System.out.print("Cara input matrix: ");
                input_from = scanner.nextInt();
                
                while (input_from < 1 || input_from > 2){
                    System.out.print("Cara input matrix: ");
                    input_from = scanner.nextInt();
                }
                if (input_from == 1){
                    int rows = scanner.nextInt();
                    scanner.nextLine();
                    mat = input_matrix(rows, rows);
                } else {
                    mat = input_matrix_file();
                }

                if (sub_option == 1){ //regresi linear
                   // MultipleLinearRegression.multiple_linear_regression(mat);

                } else { //sub_option == 2, regresi kuadratik
                   // MultivariateQuadraticRegression.multiple_quadratic_regression(mat);
                }
                
                break;

            case 7:
                System.out.println("interpolasi gambar");
                break;

            case 8: // exit
                System.exit(0);
                break;

            }
            System.out.println("");
        }
    }

    public static Matrix input_matrix(int rows, int cols){
        Scanner scanner = new Scanner(System.in);
        Matrix mat = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            String line = scanner.nextLine(); 
            String[] values = line.split(" "); 
            
            for (int j = 0; j < cols; j++) {
                double value = Double.parseDouble(values[j]);
                mat.set(i, j, value);
            }
        }
        return mat;
    }
    public static Matrix augment_matrix(Matrix a, Matrix b){
        int rows = a.get_rows();
        int cols = a.get_cols();
        Matrix augmented = new Matrix(rows, cols + 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                augmented.set(i, j, a.get(i, j));
            }
            augmented.set(i, cols, b.get(i, 0));
        }
        return augmented;
    }

    public static Matrix input_matrix_file() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Masukkan nama file:");
            String filename = scanner.nextLine();
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);

            List<List<Double>> tempMatrix = new ArrayList<>();
            int cols = 0;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] values = line.trim().split("\\s+"); 
                List<Double> row = new ArrayList<>();

                for (String value : values) {
                    row.add(Double.parseDouble(value));
                }
                tempMatrix.add(row);
                cols = Math.max(cols, row.size());
            }

            int rows = tempMatrix.size();
            Matrix mat = new Matrix(rows, cols);

            for (int i = 0; i < rows; i++) {
                List<Double> row = tempMatrix.get(i);
                for (int j = 0; j < row.size(); j++) {
                    mat.set(i, j, row.get(j));
                }
            }
            return mat;
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan.");
            return null;
        }
    }

    public static void input_matrix_file_interpolation(Matrix mat, Double[] x) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Masukkan nama file:");
            String filename = scanner.nextLine();
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
    
            List<List<Double>> tempMatrix = new ArrayList<>();
            int cols = 0;
    
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (fileScanner.hasNextLine()) {
                    String[] values = line.split("\\s+");
                    List<Double> row = new ArrayList<>();
                    for (String value : values) {
                        row.add(Double.parseDouble(value));
                    }
                    tempMatrix.add(row);
                    cols = Math.max(cols, row.size());
                } else {
                    x[0] = Double.parseDouble(line);
                }
            }
    
            int rows = tempMatrix.size();
            
            if (mat.get_rows() != rows || mat.get_cols() != cols) {
                mat = new Matrix(rows, cols); 
            }
    
            for (int i = 0; i < rows; i++) {
                List<Double> row = tempMatrix.get(i);
                for (int j = 0; j < row.size(); j++) {
                    mat.set(i, j, row.get(j));
                }
            }
    
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close(); 
        }
    }
}
