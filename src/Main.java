import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;

public class Main {
    public static void main (String[] args) {
        Locale.setDefault(Locale.US);

        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("MENU");
            System.out.println("1. Sistem Persamaan Linier\n" 
            + "2. Determinan\n" 
            + "3. Matriks Balikan\n" 
            + "4. Interpolasi Polinom\n"
            + "5. Interpolasi Bicubic Spline\n"
            + "6. Regresi Linier dan Kuadratik Berganda\n"
            + "7. Scaling Gambar\n"
            + "8. Keluar");
            System.out.print("Menu yang ingin diakses: ");
            int option = scanner.nextInt();
            
            while (option > 8 || option < 1){
                System.out.print("Menu yang ingin diakses: ");
                option = scanner.nextInt();
            }
            
            Matrix mat;
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
                    if (input_from == 1){
                        System.out.print("Masukkan banyak baris: ");
                        int rows = scanner.nextInt();
                        System.out.print("Masukkan banyak kolom: ");
                        int cols = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Masukkan koefisien dalam bentuk matriks " + rows + "x" + cols + ":\n");
                        Matrix a = input_matrix(rows, cols, scanner);
                        System.out.print("Masukkan hasil dari tiap persamaan (1 elemen tiap baris):\n");
                        Matrix b = input_matrix(rows, 1, scanner);
                        mat = augment_matrix(a, b);
                    } else {
                        mat = input_matrix_file(scanner);
                    }
                    if (sub_option == 1){ // gaussian
                        SPLGaussian.spl_gaussian(mat, scanner);
                    } else if (sub_option == 2){ // gauss-jordan
                        SPLGaussJordan.spl_gauss_jordan(mat, scanner); 
                    } else if (sub_option == 3){ //inverse
                        SPLInverse.spl_inverse(mat, scanner);
                    } else { //sub_option == 4, cramer
                       SPLCramer.spl_cramer(mat, scanner);
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
                        System.out.print("Masukkan N: ");
                        int rows = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Masukkan matriks ukuran " + rows + "x" + rows + ":\n");
                        mat = input_matrix(rows, rows, scanner);
                    } else {
                        mat = input_matrix_file(scanner);
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
                        System.out.print("Masukkan banyak baris: ");
                        int rows = scanner.nextInt();
                        System.out.print("Masukkan banyak kolom: ");
                        int cols = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Masukkan matriks ukuran " + rows + "x" + cols + ":\n");
                        mat = input_matrix(rows, rows, scanner);
                    } else {
                        mat = input_matrix_file(scanner);
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
                        System.out.print("Masukkan banyak titik yang dilewati fungsi: ");
                        int rows = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Masukkan koordinat titik-titik yang dilewati fungsi:\n");
                        mat = input_matrix(rows, 2, scanner);   
                        System.out.print("Nilai x yang ingin ditaksir: ");
                        x = scanner.nextDouble();
                    } else { 
                        double[][] M;
                        double[] a = new double[1];
                        Integer[] len = new Integer[1];
                        M = input_matrix_file_interpolation(a, len, scanner);
                        mat = new Matrix(len[0], 2);
                        for(int i = 0; i < len[0]; ++i){
                            mat.set(i, 0, M[i][0]);
                            mat.set(i, 1, M[i][1]);
                            //System.out.println(M[i][0] + " " + M[i][1]);
                        }
                        mat.print_matrix();
                        x = a[0];
                        System.out.println(x);
                    }
                    try {
                        Matrix coefficients = PolynomialInterpolation.polynomial_interpolation(mat, sub_option);
                        PolynomialInterpolation.estimate_value(coefficients, x);
                        PolynomialInterpolation.interpolation_output_file(scanner, coefficients, x); 
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred: " + e.getMessage());
                    }
                    break;

                case 5:
                    Matrix samples;
                    //  File operations 
                    double[] xcomps;
                    double[] ycomps;
                    double[] results;
                    double results_size;

                    System.out.println("interpolasi bicubic spline");

                    System.out.println("1. Input dari keyboard\n" 
                        + "2. Input dari file");
                    
                    do {
                        System.out.print("Cara input matrix: ");
                        input_from = scanner.nextInt();
                    } while(input_from < 1 || input_from > 2);

                    if (input_from == 1) {
                        System.out.println("Masukkan matriks 4x4:");
                        scanner.nextLine();
                        mat = input_matrix(4, 4, scanner);
                        System.out.println("Masukkan banyak sampel yang akan dikalkulasi:");
                        int N = scanner.nextInt();
                        scanner.nextLine();
                        xcomps = new double[N];
                        ycomps = new double[N];
                        results = new double[N];
                        results_size = N;
                        for(int it = 0; it < N; ++it){
                            System.out.println("Masukkan sample ke-" + (it + 1) + ":");
                            if (scanner.hasNextLine()) {
                                String sampleInput = scanner.nextLine();
                                String[] components = sampleInput.trim().split("\\s+");
                                try {
                                    double x_comp = Double.parseDouble(components[0]);
                                    double y_comp = Double.parseDouble(components[1]);
                
                                    double result = BicubicSplineInterpolation.evaluate(mat, x_comp, y_comp);
                                    System.out.println("f(" + x_comp + ", " + y_comp + ") = " + result);
                                    xcomps[it] = x_comp;
                                    ycomps[it] = y_comp;
                                    results[it] = result;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input format for sample coordinates.");
                                }
                            }
                        }
                        BicubicSplineInterpolation.bicubic_spline_output_file(scanner, results, xcomps, ycomps, results_size);
                    }
                    else{
                        Matrix[] matrices = input_matrix_file_bicubic(scanner);
                        
                        if (matrices == null) {
                            System.out.println("Terjadi kesalahan dalam pembacaan file atau format tidak valid.");
                            break;
                        }
                        
                        mat = matrices[0];
                        samples = matrices[1];

                        if (mat == null || samples == null) {
                            System.out.println("Error: Matriks tidak valid.");
                            break;
                        }
                        
                        xcomps = new double[samples.get_rows()];
                        ycomps = new double[samples.get_rows()];
                        results = new double[samples.get_rows()];
                        results_size = samples.get_rows();
                        for(int it = 0; it < samples.get_rows(); ++it){
                            double x_comp = samples.get(it, 0), y_comp = samples.get(it, 1);
                            double result = BicubicSplineInterpolation.evaluate(mat, x_comp, y_comp);
                            System.out.println("f(" + x_comp + ", " + y_comp + ") = " + result);
                            xcomps[it] = x_comp;
                            ycomps[it] = y_comp;
                            results[it] = result;
                        }
                        BicubicSplineInterpolation.bicubic_spline_output_file(scanner, results, xcomps, ycomps, results_size);
                    }

                    break;

                case 6: // regresi linier dan kuadratik berganda
                    // Inisialisasi Matrix xk, sebuah matrix berisi input features untuk menghitung prediksi (eksklusif untuk regresi)
                    Matrix xk;
                    System.out.println("1. Regresi linier\n" 
                    + "2. Regresi kuadratik berganda");

                    do {
                        System.out.print("Sub-menu yang ingin diakses: ");
                        sub_option = scanner.nextInt();
                    } while(sub_option < 1 || sub_option > 2);

                    System.out.println("1. Input dari keyboard\n" 
                        + "2. Input dari file");
                    
                    do {
                        System.out.print("Cara input matrix: ");
                        input_from = scanner.nextInt();
                    } while(input_from < 1 || input_from > 2);
                    
                    if (input_from == 1) {
                        System.out.print("Masukkan banyak baris: ");
                        int rows = scanner.nextInt();
                        System.out.print("Masukkan banyak kolom: ");
                        int cols = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Masukkan koefisien dalam bentuk matriks " + rows + "x" + cols + ":\n");
                        Matrix a = input_matrix(rows, cols, scanner);
                        System.out.print("Masukkan hasil dari tiap persamaan (1 elemen tiap baris):\n");
                        Matrix b = input_matrix(rows, 1, scanner);
                        mat = augment_matrix(a, b);
                        // Buat input untuk xk
                        System.out.print("Masukkan nilai variabel input sebanyak " + cols + " untuk diprediksi:\n");
                        xk = input_matrix(1, cols, scanner);
                    } else {
                        Matrix[] matrices = input_matrix_file_regression(scanner);

                        if (matrices == null) {
                            System.out.println("Terjadi kesalahan dalam pembacaan file atau format tidak valid.");
                            break;
                        }

                        mat = matrices[0];
                        xk = matrices[1];

                        if (mat == null || xk == null) {
                            System.out.println("Error: Matriks tidak valid.");
                            break;
                        }
                    } 

                    if (sub_option == 1){ //regresi linear
                        MultipleLinearRegression.multiple_linear_regression(mat, xk, scanner);
                    } else { //sub_option == 2, regresi kuadratik
                        MultivariateQuadraticRegression.multiple_quadratic_regression(mat, xk, scanner);
                    }
                    
                    break;

                case 7:
                    System.out.println("Scaling gambar");

                    if (scanner.hasNextLine()) {
                        scanner.nextLine();
                    }

                    String input_file_name;
                    File temp;

                    do {
                        System.out.println("Masukkan nama file gambar (misalnya input.jpg):");
                        input_file_name = scanner.nextLine();
                        temp = new File(input_file_name);

                        if (!temp.exists()) {
                            System.out.println("File tidak ditemukan, silakan coba lagi.");
                        }
                    } while (!temp.exists());

                    System.out.println("Masukkan faktor skala untuk sumbu X:");
                    double scaleX = scanner.nextDouble();

                    System.out.println("Masukkan faktor skala untuk sumbu Y:");
                    double scaleY = scanner.nextDouble();

                    scanner.nextLine();

                    System.out.println("Masukkan nama file output (misalnya output):");
                    String output_file_name = scanner.nextLine();

                    ImageResizer.scale_image(input_file_name, output_file_name, scaleX, scaleY);

                    break;

                case 8: // exit
                    System.exit(0);
                    break;

            }
            System.out.println("");
        }
    }

    public static Matrix input_matrix(int rows, int cols, Scanner scanner){
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

    public static Matrix input_matrix_file(Scanner scanner) {
        try {
            System.out.println("Masukkan nama file:");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
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

            fileScanner.close();

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

    public static double[][] input_matrix_file_interpolation(double[] x, Integer[] len, Scanner scanner) {
        double[][] M = null;
        try {
            System.out.println("Masukkan nama file:");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            String filename = scanner.nextLine();
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
    
            List<List<Double>> tempMatrix = new ArrayList<>();
            int cols = 0;
    
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!fileScanner.hasNextLine()) {
                    x[0] = Double.parseDouble(line);
                    break;
                }
                String[] values = line.split("\\s+");
                List<Double> row = new ArrayList<>();
                for (String value : values) {
                    row.add(Double.parseDouble(value));
                }
                tempMatrix.add(row);
                cols = Math.max(cols, row.size());
            }
    
            int rows = tempMatrix.size();
            len[0] = rows;
    
            M = new double[rows][2];
    
            for (int i = 0; i < rows; i++) {
                List<Double> row = tempMatrix.get(i);
                for (int j = 0; j < 2; j++) {
                    M[i][j] = row.get(j);
                }
            }
    
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    
        return M;
    }

    public static Matrix[] input_matrix_file_regression(Scanner scanner) {
        Matrix[] matrices = new Matrix[2];
        try {
            System.out.println("Masukkan nama file untuk matriks:");

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
    
            String filename = scanner.nextLine();
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
    
            List<List<Double>> tempMatrix = new ArrayList<>();
            int cols = 0;
    
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                String[] values = line.split("\\s+");
    
                List<Double> row = new ArrayList<>();
                for (String value : values) {
                    row.add(Double.parseDouble(value));
                }
                tempMatrix.add(row);
                cols = Math.max(cols, row.size());
            }
    
            int rows = tempMatrix.size();
            if (rows == 0) {
                System.out.println("File kosong atau format tidak valid.");
                return null;
            }
    
            List<Double> xkList = tempMatrix.remove(rows - 1);
            int featureCols = xkList.size();
    
            for (List<Double> row : tempMatrix) {
                if (row.size() != featureCols + 1) {
                    throw new IllegalArgumentException("Jumlah kolom fitur dan output tidak sesuai.");
                }
            }
    
            Matrix augmentedMatrix = new Matrix(tempMatrix.size(), featureCols + 1);
            for (int i = 0; i < tempMatrix.size(); i++) {
                List<Double> row = tempMatrix.get(i);
                for (int j = 0; j < featureCols + 1; j++) {
                    augmentedMatrix.set(i, j, row.get(j));
                }
            }
    
            Matrix xkMatrix = new Matrix(1, featureCols);
            for (int i = 0; i < featureCols; i++) {
                xkMatrix.set(0, i, xkList.get(i));
            }
    
            matrices[0] = augmentedMatrix;
            matrices[1] = xkMatrix;
    
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    
        return matrices;
    }    
    
    public static Matrix[] input_matrix_file_bicubic(Scanner scanner){
        Matrix[] matrices = new Matrix[2];
        try {
            System.out.println("Masukkan nama file untuk matriks:");
    
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
    
            String filename = scanner.nextLine();
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
    
            double[][] matrixData = new double[4][4];
            for (int i = 0; i < 4; i++) {
                if (!fileScanner.hasNextLine()) {
                    throw new IllegalArgumentException("Masukan harus berupa matriks 4x4");
                }
                String line = fileScanner.nextLine().trim();
                String[] values = line.split("\\s+");
                if (values.length != 4) {
                    throw new IllegalArgumentException("Masukan harus berupa matriks 4x4");
                }
                for (int j = 0; j < 4; j++) {
                    matrixData[i][j] = Double.parseDouble(values[j]);
                }
            }

            Matrix matrix4x4 = new Matrix(4, 4);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    matrix4x4.set(i, j, matrixData[i][j]);
                }
            }
    
            List<double[]> coordinatesList = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                String[] coordinateValues = line.split("\\s+");

                if (coordinateValues.length == 2) {
                    double[] coordinate = new double[2];
                    coordinate[0] = Double.parseDouble(coordinateValues[0]);
                    coordinate[1] = Double.parseDouble(coordinateValues[1]);
                    coordinatesList.add(coordinate);
                } else {
                    throw new IllegalArgumentException("Each coordinate must have exactly 2 values.");
                }
            }

            int numCoordinates = coordinatesList.size();
            Matrix coordinatesMatrix = new Matrix(numCoordinates, 2);
            for (int i = 0; i < numCoordinates; i++) {
                coordinatesMatrix.set(i, 0, coordinatesList.get(i)[0]);
                coordinatesMatrix.set(i, 1, coordinatesList.get(i)[1]);
            }

            matrices[0] = matrix4x4;
            matrices[1] = coordinatesMatrix;

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        return matrices;
    }
}
