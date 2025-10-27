package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainInheritance {
    public static void main(String[] args) {
        // Membuat objek dari masing-masing subclass
        Benih benih = new Benih("BP-125", "Benih Padi ", 25000, 100, "IR64");
        Pupuk pupuk = new Pupuk("PU-135", "Pupuk Urea", 350000, 40, "Urea");
        AlatPertanian alat = new AlatPertanian("CB-150", "Cangkul Baja", 50000, 15, "Baja");

        System.out.println("=== DATA PRODUK PERTANIAN ===");
        benih.deskripsi();
        pupuk.deskripsi();
        alat.deskripsi();

        // Demonstrasi method inheritance
        System.out.println("\n=== DEMONSTRASI METHOD DARI SUPERCLASS ===");
        System.out.println("Menambah stok pupuk sebanyak 10...");
        pupuk.tambahStok(10);
        System.out.println("Stok pupuk sekarang: " + pupuk.getStok());

        System.out.println("\nMengurangi stok alat pertanian sebanyak 5...");
        alat.kurangiStok(5);
        System.out.println("Stok alat pertanian sekarang: " + alat.getStok());

        // Menampilkan identitas mahasiswa
        CreditBy.print("240202860", "Efan");
    }
}
