package com.upb.agripos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test; 

import com.upb.agripos.model.Product;

public class ProductTest {
    
    @Test
    public void testProductName() {
        // Skenario: Membuat produk baru
        Product p = new Product("P01", "Benih Jagung");
        
        // Ekspektasi: Nama produk harus "Benih Jagung"
        assertEquals("Benih Jagung", p.getName());
    }
    
    @Test
    public void testProductCode() {
        // Skenario: Cek kode produk
        Product p = new Product("P02", "Pupuk Urea");
        
        // Ekspektasi: Kode harus "P02"
        assertEquals("P02", p.getCode());
    }
}