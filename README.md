# Tugas Besar 1 IF2123 Aljabar Linear dan Geometri
#### Sistem Persamaan Linear, Determinan, dan Aplikasinya

### Kelompok 46 - Silahkan Siapapun Yang Mau Sekelompok Samaku (SSYMSS)
| Nama | NIM |
|----------|----------|
| Nathaniel Jonathan Rusli | 13523013 |
| Benedict Presley | 13523067 | 
| Naomi Risaka Sitorus | 13523122 | 

## Deskripsi
Proyek ini adalah sebuah kalkulator matriks dan aplikasinya yang ditulis dalam bahasa pemrograman Java. Program ini mampu menyelesaikan berbagai operasi pada matriks, seperti sistem persamaan linier (SPL), determinan, matriks balikan, interpolasi polinom, interpolasi bicubic spline, regresi linier dan kuadratik berganda, serta interpolasi gambar. Program mendukung input dari keyboard maupun file eksternal sesuai format yang telah ditentukan. Program dapat menampilkan output pada terminal serta menyimpannya ke dalam file teks di folder test sesuai keinginan pengguna.

## Fitur Utama
1. Sistem Persamaan Linier (SPL)
   <br>Program dapat menyelesaikan SPL menggunakan beberapa metode:
   - Metode eliminasi Gauss
   - Metode eliminasi Gauss-Jordan
   - Matriks balikan
   - Kaidah Cramer
     
2. Determinan
   <br>Program menghitung determinan dari sebuah matriks dengan dua metode, yaitu metode eliminasi Gauss-Jordan dan matriks balikan.

3. Matriks Balikan
   <br>Program menghitung balikan dari sebuah matriks dengan dua metode, yaitu operasi baris elementer (OBE) dan matriks adjoin.

4. Interpolasi Polinom
   <br>Program melakukan interpolasi polinom untuk data titik-titik yang diberikan menggunakan beberapa metode:
   - Metode eliminasi Gauss
   - Metode eliminasi Gauss-Jordan
   - Matriks balikan
   - Kaidah Cramer
     
6. Interpolasi Bicubic Spline
   <br>Program melakukan interpolasi bicubic spline.

7. Regresi Linier dan Kuadratik Berganda
   <br>Program menyelesaikan regresi linier dan kuadratik berganda untuk data yang diberikan.

8. Interpolasi Gambar
   <br>Program melakukan scaling (resizing) gambar berdasarkan ukuran matriks tertentu.

9. Keluar
   <br>Mengakhiri program.

## Cara Penggunaan
1. Clone repository atau unduh kode sumber program.
2. Buka proyek di IDE yang mendukung Java.
3. Jalankan program dari kelas Main.java.
   ```
   cd src
   javac -d ../bin *.java
   cd ../bin
   java Main.Main
