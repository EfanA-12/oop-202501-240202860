package com.upb.agripos;

// --- BAGIAN INI SANGAT PENTING (JANGAN DIHAPUS) ---
import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;
// --------------------------------------------------

public class MainDAOTest {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAOImpl();

        System.out.println("=== PERSIAPAN: Membersihkan Data Lama ===");
        // Menghapus data lama agar tidak error Duplicate saat di-Run berkali-kali
        try {
             dao.delete("P001");
             dao.delete("P002");
        } catch (Exception e) {
            // Abaikan error jika data memang belum ada
        }
        System.out.println("Data lama sudah dibersihkan (jika ada).\n");

        System.out.println("=== TEST 1: Simpan Barang ===");
        Product p1 = new Product("P001", "Semen Tiga Roda", 65000, 100);
        Product p2 = new Product("P002", "Cat Tembok Putih", 120000, 50);
        dao.save(p1);
        dao.save(p2);

        System.out.println("\n=== TEST 2: Tampilkan Semua Barang ===");
        List<Product> list = dao.getAllProducts();
        for (Product p : list) {
            System.out.println(p);
        }

        System.out.println("\n=== TEST 3: Update Barang (Stok Berkurang) ===");
        p1.setStock(95); 
        dao.update(p1);
        
        Product updatedP1 = dao.getProductByCode("P001");
        System.out.println("Data setelah update: " + updatedP1);
    }
}