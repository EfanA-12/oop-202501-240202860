package com.upb.agripos.service;

import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

public class CartService {
    // List untuk menampung barang belanjaan
    private List<CartItem> cartItems = new ArrayList<>();

    // LOGIKA 1: Tambah Barang
    // Jika barang sudah ada -> Update jumlahnya
    // Jika belum ada -> Masukkan baru
    public void addItem(Product product, int qty) {
        CartItem existingItem = null;
        
        // Cek apakah barang ini sudah ada di keranjang?
        for (CartItem item : cartItems) {
            if (item.getProduct().getCode().equals(product.getCode())) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            // Update qty lama + qty baru
            int newQty = existingItem.getQuantity() + qty;
            existingItem.setQuantity(newQty);
        } else {
            // Buat item baru
            cartItems.add(new CartItem(product, qty));
        }
    }

    // LOGIKA 2: Hapus Barang
    public void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    // LOGIKA 3: Kosongkan Keranjang (Fitur Bayar)
    public void clearCart() {
        cartItems.clear();
    }

    // LOGIKA 4: Hitung Total Bayar (Grand Total)
    public double calculateGrandTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Getter List (Agar bisa ditampilkan di Tabel GUI)
    public List<CartItem> getCartItems() {
        return cartItems;
    }
}