public class BicubicSplineInterpolation {
    private static double power(double b, double p){
        if(b == 0 && p == 0){
            return 1;
        }
        return Math.pow(b, p);
    }

    private static double Mvalue(int type, double x, double y, int i, int j){
        double result = 0;

        if(type == 0){
            result = power(x, i) * power(y, j);
        }
        else if(type == 1){
            if(i == 0){
                result = 0;
            }
            else result = (double)i * power(x, i - 1) * power(y, j);
        }
        else if(type == 2){
            if(j == 0){
                result = 0;
            }
            else result = (double)j * power(x, i) * power(y, j - 1);
        }
        else{
            if(i == 0 || j == 0){
                result = 0;
            }
            else result = (double)i * (double)j * power(x, i - 1) * power(y, j - 1);
        }

        return result;
    }

    private static Matrix bicubic_spline_interpolation(Matrix F){
        //a00 a01 a02 a03 a10 a11 a12 a13 a20 a21 a22 a23 a30 a31 a32 a33
        Matrix result = new Matrix(16, 1);

        Matrix transformer = new Matrix(16, 16);

        for(int i = 0; i < 4; ++i){
            for(int j = 0; j < 4; ++j){
                result.set(4 * i + j, 0, F.get(i, j));
            }
        }

        for(int type = 0; type < 4; ++type){
            for(int posy = 0; posy < 2; ++posy){
                for(int posx = 0; posx < 2; ++posx){
                    int current_row = 4 * type + 2 * posy + posx;
                    for(int i = 0; i < 4; ++i){
                        for(int j = 0; j < 4; ++j){
                            int current_col = 4 * i + j;
                            transformer.set(current_row, current_col, Mvalue(type, posx, posy, i, j));
                        }
                    }
                }
            }
        }
        
        // for(int i = 0; i < 16; ++i){
        //     for(int j = 0; j < 16; ++j){
        //         System.out.print((int)transformer.get(i, j) + " ");
        //     }
        //     System.out.println();
        // }

        transformer = InverseMatrixGaussJordan.inverse_matrix(transformer);

        result = transformer.multiply(result);

        return result;
    }
    
    public static double evaluate(Matrix F, double x, double y){
        Matrix coeff = bicubic_spline_interpolation(F);

        //coeff.print_matrix();

        double result = 0;
        for(int i = 0; i < 4; ++i){
            for(int j = 0; j < 4; ++j){
                result += coeff.get(4 * i + j, 0) * power(x, i) * power(y, j);
            }
        }

        return result;
    }

    public static void main(String args[]){
        Matrix F = new Matrix(4, 4);

        for(int i = 1; i <= 4; ++i){
            for(int j = 1; j <= 4; ++j){
                F.set(i - 1, j - 1, 4 * (i - 1) + j);
            }
        }

        F.print_matrix();

        System.out.println(evaluate(F, 0.5, 0.5));
    }
}