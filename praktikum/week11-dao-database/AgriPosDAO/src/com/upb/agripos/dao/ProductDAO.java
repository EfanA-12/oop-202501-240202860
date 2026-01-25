package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDAO {
    void save(Product product);           // Simpan barang baru
    void update(Product product);         // Update barang
    void delete(String code);             // Hapus barang
    Product getProductByCode(String code);// Cari 1 barang
    List<Product> getAllProducts();       // Ambil semua barang
}