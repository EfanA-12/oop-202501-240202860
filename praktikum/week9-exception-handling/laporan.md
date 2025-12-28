# Laporan Praktikum Minggu 9
Topik: Exception Handling, Custom Exception, dan Penerapan Design Pattern

## Identitas
- Nama  : Efan Aryanto Adli
- NIM   : 240202860
- Kelas : 3IKRA

---

## Tujuan
1. Mahasiswa mampu menjelaskan perbedaan mendasar antara error dan exception.
2. Mahasiswa dapat mengimplementasikan blok kode try–catch–finally dengan benar untuk menangani kesalahan.
3. Mahasiswa mampu membuat custom exception sendiri sesuai dengan kebutuhan logika bisnis program.
4. Mahasiswa dapat mengintegrasikan mekanisme penanganan exception ke dalam aplikasi simulasi keranjang belanja (Agri-POS).
5. Mahasiswa mengenal penerapan design pattern dasar seperti Singleton dan konsep MVC dalam pengembangan aplikasi Java.

---

## Dasar Teori 
1. Error vs Exception: Error merujuk pada kondisi fatal yang tidak dapat ditangani oleh program seperti OutOfMemoryError, sedangkan Exception adalah kondisi tidak normal yang masih dapat diantisipasi dan ditangani agar program tidak berhenti tiba-tiba.  
2. Struktur Penanganan: Java menyediakan blok try untuk kode berisiko, catch untuk menangkap kesalahan, dan finally untuk menjalankan kode yang harus tetap dieksekusi (seperti menutup koneksi) apa pun kondisinya.  
3. Custom Exception: Class exception yang dibuat sendiri oleh programmer dengan mewarisi class Exception (untuk checked exception) untuk memberikan pesan kesalahan yang lebih spesifik bagi pengguna.
4. Design Pattern:
   - Singleton: Memastikan sebuah class hanya memiliki satu instance di seluruh aplikasi.
   - MVC (Model-View-Controller): Memisahkan logika data (Model), tampilan (View), dan pemroses alur (Controller) agar kode lebih terorganisir.

---

## Langkah Praktikum
1. Persiapan Struktur Proyek 
   - Membuat direktori kerja sesuai dengan standar struktur paket Java: `src/main/java/com/upb/agripos/`.
   - Menyiapkan file-file Java yang diperlukan sesuai dengan tanggung jawab masing-masing kelas.
2. Pembuatan Custom Exception 
   Tahap ini bertujuan untuk membuat kelas kesalahan spesifik yang mewarisi class  `Exception`.
   - `InvalidQuantityException`: Digunakan untuk menangani input jumlah barang yang tidak valid (nol atau negatif).
   - `ProductNotFoundException`: Digunakan ketika operasi penghapusan dilakukan pada produk yang tidak ada di dalam keranjang.
   - `InsufficientStockException`: Digunakan untuk memvalidasi ketersediaan stok barang saat proses checkout.
3. Implementasi Model Produk (`Product.java`)
   - Mendefinisikan atribut produk yang terdiri dari `code`, `name`, `price`, dan `stock`.
   - Membuat constructor untuk inisialisasi data produk.
   - Menambahkan metode `reduceStock(int qty)` untuk memperbarui jumlah stok setelah transaksi berhasil.
4. Pengembangan Logika Keranjang Belanja (`ShoppingCart.java`)
   - Menggunakan `HashMap` untuk menyimpan koleksi produk dan jumlah yang dibeli.
   - Metode `addProduct`: Menambahkan validasi jika `qty <= 0` maka akan melempar `InvalidQuantityException`.
   - Metode `removeProduct`: Menambahkan pengecekan keberadaan produk; jika tidak ditemukan, melempar `ProductNotFoundException`.
   - Metode `checkout`:
      1. Melakukan iterasi pada semua item di keranjang.
      2. Membandingkan kebutuhan jumlah dengan stok yang tersedia di kelas `Product`.
      3. Melempar `InsufficientStockException` jika stok tidak mencukupi.
      4. Mengurangi stok produk secara permanen jika seluruh validasi terpenuhi.
5. Pengujian Sistem (`MainExceptionDemo.java`)
   - Membuat objek `ShoppingCart` dan `Product` sebagai data uji.
   - Mengimplementasikan blok try-catch untuk mensimulasikan tiga skenario kegagalan:
      1. Memasukkan jumlah negatif.
      2. Menghapus produk yang belum ditambahkan ke keranjang.
      3. Melakukan checkout dengan jumlah yang melebihi stok yang tersedia.
   - Menampilkan pesan kesalahan dari `e.getMessage()` ke terminal untuk memastikan exception tertangkap dengan benar.

---

## Kode Program 

### 1. InsufficientStockException.java
```java
package main.java.com.upb.agripos;

public class InsufficientStockException extends Exception {
    public InsufficientStockException(String msg) { 
        super(msg); 
    }
}
```
### 2. InvalidQuantityException.java
```java
package main.java.com.upb.agripos;

public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String msg) { 
        super(msg); 
    }
}
```
### 3. MainExceptionDemo.java
```java
package main.java.com.upb.agripos;

public class MainExceptionDemo {
    public static void main(String[] args) {
        // Ganti [Nama]-[NIM] dengan identitas Anda
        System.out.println("Hello, I am Efan Aryanto Adli-240202860 (Week9)");

        ShoppingCart cart = new ShoppingCart();
        Product p1 = new Product("P01", "Pupuk Organik", 25000, 3);

        // Uji validasi Quantity
        try {
            cart.addProduct(p1, -1);
        } catch (InvalidQuantityException e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }

        // Uji validasi Produk di Keranjang
        try {
            cart.removeProduct(p1);
        } catch (ProductNotFoundException e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }

        // Uji validasi Stok
        try {
            cart.addProduct(p1, 5); // Stok hanya ada 3
            cart.checkout();
        } catch (Exception e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }
    }
}
```
### 4. Product.java
```java
package main.java.com.upb.agripos;

public class Product {
    private final String code;
    private final String name;
    private final double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public void reduceStock(int qty) { this.stock -= qty; }
}
```
### 5. ProductNotFoundException.java
```java
package main.java.com.upb.agripos;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String msg) { 
        super(msg); 
    }
}
```
### 6. ShoppingCarte.java
```java
package main.java.com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p, int qty) throws InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity harus lebih dari 0.");
        }
        items.put(p, items.getOrDefault(p, 0) + qty);
    }

    public void removeProduct(Product p) throws ProductNotFoundException {
        if (!items.containsKey(p)) {
            throw new ProductNotFoundException("Produk tidak ada dalam keranjang.");
        }
        items.remove(p);
    }

    public void checkout() throws InsufficientStockException {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            if (product.getStock() < qty) {
                throw new InsufficientStockException(
                    "Stok tidak cukup untuk: " + product.getName()
                );
            }
        }
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().reduceStock(entry.getValue());
        }
    }
}
```
---

## Hasil Eksekusi  
![Screenshot hasil](screenshots/hasil.png)

---

## Analisis
- Tiga buah Custom Exception dibuat: InvalidQuantityException, ProductNotFoundException, dan InsufficientStockException untuk menangani batasan bisnis yang spesifik. 
- Pada class ShoppingCart, metode addProduct menggunakan kata kunci throws untuk mewajibkan pemanggil menangani kesalahan jika input jumlah kurang dari atau sama dengan nol.
- Metode checkout melakukan validasi stok sebelum transaksi diproses; jika stok produk kurang dari jumlah yang dipesan, maka InsufficientStockException akan dilemparkan, mencegah terjadinya data yang tidak konsisten.
- Penggunaan blok try-catch di class MainExceptionDemo membuktikan bahwa program tidak langsung "crash" saat terjadi kesalahan, melainkan memberikan output pesan edukatif kepada pengguna.

---

## Kesimpulan
1. Mekanisme Exception Handling sangat krusial dalam menjaga stabilitas aplikasi dari masukan data yang tidak valid atau kondisi sistem yang tidak terduga.
2. Dengan menggunakan Custom Exception, pesan kesalahan dalam aplikasi menjadi lebih informatif dan relevan dengan kasus bisnis yang ditangani (seperti masalah stok atau kuantitas).
3. Penerapan struktur kode yang baik (seperti pemisahan model dan penggunaan exception) mempermudah proses debugging dan pengembangan aplikasi di masa depan.

---

## Quiz
1. Jelaskan perbedaan error dan exception. 
   **Jawaban:** …  
   1. Error: Merujuk pada kondisi fatal yang biasanya terjadi pada tingkat sistem atau JVM (Java Virtual Machine) dan tidak dapat ditangani oleh program (contoh: `OutOfMemoryError`).
   2. Exception: Merujuk pada kondisi tidak normal yang muncul saat program berjalan namun masih dapat diantisipasi dan ditangani menggunakan kode program agar aplikasi tidak berhenti secara tiba-tiba.

2. Apa fungsi finally dalam blok try–catch–finally? 
   **Jawaban:** …  
   1. Blok `finally` berfungsi sebagai blok kode yang akan selalu dijalankan, baik ketika pengecualian (exception) terjadi maupun tidak.
   2. Biasanya digunakan untuk proses pembersihan (cleanup), seperti menutup koneksi database atau menutup file yang dibuka di blok `try`.

3. Mengapa custom exception diperlukan?
   **Jawaban:** … 
   1.  Spesifik: Agar program dapat memberikan pesan kesalahan yang lebih mendetail dan relevan dengan logika bisnis tertentu.
   2. Keterbacaan: Memudahkan pengembang lain dalam memahami penyebab error karena nama class exception sudah mencerminkan masalahnya (misal: `InsufficientStockException` lebih jelas daripada sekadar `Exception`).
   3. Kontrol: Memungkinkan penanganan yang berbeda untuk setiap jenis kesalahan bisnis yang unik.

4. Berikan contoh kasus bisnis dalam POS yang membutuhkan custom exception.
   **Jawaban:** … 
   1. Validasi Stok: Melempar ``InsufficientStockException ketika jumlah barang yang dibeli melebihi stok yang tersedia di gudang.
   2. Validasi Input: Melempar `InvalidQuantityException` jika kasir memasukkan jumlah barang nol atau negatif.
   3. Pencarian Produk: Melempar `ProductNotFoundException` jika barcode yang dipindai tidak terdaftar dalam sistem keranjang. 
