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