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
            
            if (option == 1 || option == 2 || option == 3 || option == 6){
                System.out.println("1. Metode eliminasi Gauss\n" 
                + "2. Metode eliminasi Gauss-Jordan\n" 
                + "3. Matriks matriks balikan\n" 
                + "4. Kaidah Cramer");
                System.out.print("Sub-menu yang ingin diakses: ");
                int sub_option = scanner.nextInt();
                
                while (sub_option < 1 || sub_option > 4){
                    System.out.print("Sub-menu yang ingin diakses: ");
                    sub_option = scanner.nextInt();
                }
                
                if (option == 1){
                    if (sub_option == 1){
                        System.out.println("Gauss");
                    } else if (sub_option == 2){
                        System.out.println("Gauss-Jordan");
                    } else if (sub_option == 3){
                        System.out.println("Matriks balikan");
                    } else { //sub_option == 4
                        System.out.println("Kaidah Cramer");
                    }
                } else if (option == 2){

                } else if (option == 3){

                } else { // option == 6

                } 
            } else { 
                if (option == 4){
                    System.out.println("polinomial");
                } else if (option == 5) {
                    System.out.println("bicubic");
                } else if (option == 7) {
                    System.out.println("interpolasi gambar");
                } else { // option == 8
                    System.exit(0);
                }
            }
            System.out.println("");
        }
    }
}
