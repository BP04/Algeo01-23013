import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Matrix {
    private int rows;
    private int cols;
    private double[][] data;
    public Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    public double get(int row, int col){
        if(row >= 0 && row < rows && col >= 0 && col < cols){
            return data[row][col];
        }
        else{
            throw new IndexOutOfBoundsException("Invalid row or column index");
        }
    }

    public void set(int row, int col, double value){
        if(row >= 0 && row < rows && col >= 0 && col < cols){
            data[row][col] = value;
        }
        else{
            throw new IndexOutOfBoundsException("Invalid row or column index");
        }
    }

    public int get_rows(){
        return rows;
    }

    public int get_cols(){
        return cols;
    }

    public Matrix copy_matrix(){
        Matrix res = new Matrix(rows, cols);
        for(int i = 0; i < rows; ++i){
            for(int j = 0; j < cols; ++j){
                res.set(i, j, data[i][j]);
            }
        }
        return res;
    }

    public double[] copy_row(int row){
        double[] res = new double[cols];
        for(int j = 0; j < cols; ++j){
            res[j] = data[row][j];
        }
        return res;
    }

    public double[] copy_col(int col){
        double[] res = new double[rows];
        for(int i = 0; i < rows; ++i){
            res[i] = data[i][col];
        }
        return res;
    }

    public Matrix multiply_by_constant(double constant) {
        Matrix res = new Matrix(rows, cols);
        for(int i = 0; i < rows; ++i){
            for(int j = 0; j < cols; ++j){
                res.set(i, j, data[i][j] * constant);
            }
        }
        return res;
    }
    

    public void print_matrix(){
        for(int i = 0; i < rows; ++i){
            for(int j = 0; j < cols; ++j){
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public Matrix transpose_matrix(){
        Matrix transpose = new Matrix(cols, rows);
        for (int i = 0; i < rows; ++i){
            for (int j = 0; j < cols; ++j){
                transpose.set(j, i, data[i][j]);
            }
        }
        return transpose;
    }

    public Matrix multiply(Matrix other){
        if (cols != other.get_rows()) {
            throw new IllegalArgumentException("Matrix multiplication not possible: " + 
            "First matrix's number of columns must be equal to the second matrix's number of rows.");
        }

        Matrix result = new Matrix(rows, other.get_cols()) ;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.get_cols(); j++) {
                double sum = 0;
                for (int k = 0; k < cols; k++) {
                    sum += (this.get(i, k) * other.get(k, j));
                }
                result.set(i, j, sum);
            }
        }

        return result;
    }

    public static void save_output_SPL(Scanner scanner, Matrix result) {
        char save_option;
        do {
            System.out.print("Apakah output ingin disimpan ke file? (y/n): ");
            save_option = scanner.next().charAt(0);
        } while (save_option != 'y' && save_option != 'Y' && save_option != 'n' && save_option != 'N');

        if (save_option == 'y' || save_option == 'Y') {
            System.out.print("Masukkan nama file (tanpa .txt): ");
            String filename = scanner.next();

            File dir = new File("../test");
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("Direktori 'test' berhasil dibuat.");
                } else {
                    System.out.println("Gagal membuat direktori 'test'.");
                    return; 
                }
            }

            try {
                PrintWriter writer = new PrintWriter(new File(dir, filename + ".txt"));

                for (int i = 0; i < result.get_rows(); i++) {
                    writer.printf("x%d = " + result.get(i, 0) + "\n", i + 1); 
                }

                writer.close();
                System.out.println("Output berhasil disimpan di folder test/" + filename + ".txt");
            } catch (FileNotFoundException e) {
                System.out.println("Terjadi kesalahan saat menyimpan file.");
                e.printStackTrace(); 
            }
        }
    }
}
