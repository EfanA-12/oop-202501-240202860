package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.*;
import main.java.com.upb.agripos.util.CreditBy;

public class MainProduk {
    public static void main(String[] args) {
        Produk p1 = new Produk("BRS-115", "Beras 1kg", 15000, 75);
        Produk p2 = new Produk("TRA-131", "Telur Ayam 1kg", 31000, 50);
        Produk p3 = new Produk("TGT-117", "Tepung Terigu 1kg", 17000, 25);
        Produk p4 = new Produk("MYG-120", "Minyak Goreng 1L", 20000, 35);

        System.out.println("Kode: " + p1.getKode() + ", Nama: " + p1.getNama() + ", Harga: " + p1.getHarga() + ", Stok: " + p1.getStok() + "kg");
        System.out.println("Kode: " + p2.getKode() + ", Nama: " + p2.getNama() + ", Harga: " + p2.getHarga() + ", Stok: " + p2.getStok() + "kg");
        System.out.println("Kode: " + p3.getKode() + ", Nama: " + p3.getNama() + ", Harga: " + p3.getHarga() + ", Stok: " + p3.getStok() + "kg");
        System.out.println("Kode: " + p4.getKode() + ", Nama: " + p3.getNama() + ", Harga: " + p3.getHarga() + ", Stok: " + p3.getStok() + "L");

        // Tampilkan identitas mahasiswa
        CreditBy.print("240202860","Efan Aryanto Adli");

        
        p1.tambahStok(30);
        p2.tambahStok(45);
        p3.tambahStok(50);
        p4.tambahStok(15);

        p1.kurangiStok(51);
        p2.kurangiStok(100);
        p3.kurangiStok(20);
        p4.kurangiStok(5);

        System.out.println(p1.getStok() + "kg");
        System.out.println(p2.getStok() + "kg");
        System.out.println(p3.getStok() + "kg");
        System.out.println(p4.getStok() + "L");

        
    }

}