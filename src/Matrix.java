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
            res[j] = data[rows][j];
        }
        return res;
    }

    public double[] copy_col(int col){
        double[] res = new double[rows];
        for(int i = 0; i < cols; ++i){
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
    
}
