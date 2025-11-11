# Laporan Praktikum Minggu 4
Topik: Polymorphism (Info Produk)

## Identitas
- Nama  : Efan Aryanto Adli
- NIM   : 240202860
- Kelas : 3IKRA

---

## Tujuan 
1. Mahasiswa mampu menjelaskan konsep polymorphism dalam OOP.
2. Mahasiswa mampu membedakan method overloading dan overriding.
3. Mahasiswa mampu mengimplementasikan polymorphism (overriding, overloading, dynamic binding) dalam program.
4. Mahasiswa mampu menganalisis contoh kasus polymorphism pada sistem nyata (Agri-POS).

---

## Dasar Teori   
1. Method Overloading : Terjadi ketika sebuah kelas memiliki dua atau lebih method dengan nama yang sama tetapi parameter yang berbeda (berbeda jumlah, tipe, atau urutan). Method mana yang akan dipanggil ditentukan pada saat compile-time berdasarkan argumen yang diberikan. 
2. Method Overriding : Terjadi ketika sebuah subclass menyediakan implementasi spesifik untuk sebuah method yang sudah didefinisikan di superclass-nya. Method di subclass harus memiliki nama, return type, dan parameter yang sama persis (atau kompatibel) dengan method di superclass.
3. Enkapsulasi digunakan untuk menyembunyikan data.

---

## Langkah Praktikum
1. Overloading: Menambahkan dua method `tambahStok` (dengan parameter `int` dan `double`) pada class `Produk`. 
2. Overriding: Membuat method `getInfo()` di superclass `Produk` dan meng-override-nya di setiap subclass (`Benih`, `Pupuk`, `AlatPertanian`) untuk menampilkan detail spesifik.
3. Dynamic Binding: Membuat class `MainPolymorphism.java`, lalu membuat array `Produk[]` yang diisi oleh objek-objek subclass (`Benih`, `Pupuk`, `AlatPertanian`).
4. Demonstrasi: Melakukan iterasi (loop) pada array tersebut dan memanggil method `getInfo()` untuk membuktikan dynamic binding.
5. Administrasi: Memanggil `CreditBy.print()` di akhir main class dan melakukan commit.

---

## Kode Program
### 1. Produk.Java
```java
package main.java.com.upb.agripos.model;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // ===== Method Overloading =====
    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void tambahStok(double jumlah) {
        this.stok += (int) jumlah;
    }

    // ===== Getter Info =====
    public String getInfo() {
        return "Produk: " + nama +
               " (Kode: " + kode + "), Harga: " + harga +
               ", Stok: " + stok;
    }

    // Getter dan Setter opsional
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }
}
```
### 2. AlatPertanian.java
```java
package main.java.com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String bahan;

    public AlatPertanian(String kode, String nama, double harga, int stok, String bahan) {
        super(kode, nama, harga, stok);
        this.bahan = bahan;
    }

    @Override
    public String getInfo() {
        return "Alat Pertanian - " + super.getInfo() + ", Bahan: " + bahan;
    }
}
```
### 3. Benih.java
```java
package main.java.com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    @Override
    public String getInfo() {
        return "Benih - " + super.getInfo() + ", Varietas: " + varietas;
    }
}
```
### 4. Pupuk.java
```java
package main.java.com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis;

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    @Override
    public String getInfo() {
        return "Pupuk - " + super.getInfo() + ", Jenis: " + jenis;
    }
}
```
### 5. ObatHama.java
```java
package main.java.com.upb.agripos.model;

public class ObatHama extends Produk {
    private String kandunganAktif;

    public ObatHama(String kode, String nama, double harga, int stok, String kandunganAktif) {
        super(kode, nama, harga, stok);
        this.kandunganAktif = kandunganAktif;
    }

    @Override
    public String getInfo() {
        return "Obat Hama - " + super.getInfo() + ", Kandungan Aktif: " + kandunganAktif;
    }
}
```
### 6. CreditBy.java
```java
package main.java.com.upb.agripos.util;

public class CreditBy {
    public static void print(String NAMA, String NIM) {
        System.out.println("\n=== Credit By ===");
        System.out.println("NAMA : " + NAMA);
        System.out.println("NIM  : " + NIM);
    }
}
```
### 7. MainPolymorphism.java
```java
package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.*;
import main.java.com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {

        // ===== Membuat array objek (Dynamic Binding) =====
        Produk[] daftarProduk = {
            new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64"),
            new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea"),
            new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja"),
            new ObatHama("OBT-888", "Rodentisida", 50000, 25, "Antikoagulan")
        };

        System.out.println("=== Informasi Produk ===");
        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo());
        }

        // ===== Demonstrasi Overloading =====
        System.out.println("\n=== Tambah Stok ===");
        daftarProduk[0].tambahStok(10);      // tambah stok dengan int
        daftarProduk[1].tambahStok(5.5);     // tambah stok dengan double

        System.out.println(daftarProduk[0].getInfo());
        System.out.println(daftarProduk[1].getInfo());

        // ===== Identitas =====
        CreditBy.print("Efan Aryanto Adli","240202860");
    }
}
```
---

## Hasil Eksekusi 
![Screenshot hasil](screenshots/ScreenshotHasilPraktikum)
---

## Analisis
Saat program berjalan, sebuah array daftarProduk yang bertipe Produk[] dibuat, namun array ini diisi dengan objek-objek dari subclass yang berbeda (Benih, Pupuk, AlatPertanian). Ketika program melakukan loop pada array tersebut, Dynamic Binding bekerja: saat p.getInfo() dipanggil di dalam loop, Java secara pintar melihat tipe objek aslinya (bukan tipe array-nya) dan memanggil method getInfo() yang ada di subclass (Benih, Pupuk, atau AlatPertanian). Hasilnya, output yang tercetak adalah info spesifik dari masing-masing subclass, bukan info umum dari Produk. 

---

## Kesimpulan
Praktikum ini berhasil mendemonstrasikan tiga pilar polymorphism. Overloading (static polymorphism) diimplementasikan pada method tambahStok dengan parameter berbeda. Overriding (dynamic polymorphism) diterapkan saat subclass seperti Benih dan Pupuk menyediakan implementasi getInfo() yang spesifik, menggantikan implementasi Produk. Terakhir, Dynamic Binding terbukti bekerja saat program memanggil method getInfo() yang benar pada runtime (versi Benih atau Pupuk) meskipun semua objek disimpan dalam satu array bertipe Produk.

---

## Quiz
1. Apa perbedaan overloading dan overriding?  
   **Jawaban:** Overloading adalah mendefinisikan method dengan nama yang sama tetapi parameter yang berbeda (berbeda tipe, jumlah, atau urutan) di dalam satu kelas. Overriding adalah ketika subclass mengganti implementasi method yang sudah ada di superclass-nya, dengan nama dan parameter yang sama persis.

2. Bagaimana Java menentukan method mana yang dipanggil dalam dynamic binding?  
   **Jawaban:**Java menentukannya pada saat runtime (bukan compile time). Java akan melihat jenis objek aktual (misalnya, apakah objek itu Benih atau Pupuk) yang sedang dirujuk oleh variabel, bukan hanya tipe dari variabel referensinya (misalnya Produk).

3. Berikan contoh kasus polymorphism dalam sistem POS selain produk pertanian. 
   **Jawaban:**POS Restoran: Superclass ItemMenu punya method getInfo(). Subclass Makanan meng-override-nya untuk info "level pedas", sementara Minuman meng-override untuk info "ukuran gelas".
