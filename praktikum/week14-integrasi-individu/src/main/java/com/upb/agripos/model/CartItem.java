package com.upb.agripos.model;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // --- GETTER & SETTER ---

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // --- METHOD KHUSUS UNTUK TABLEVIEW (PENTING!) ---
    // TableView JavaFX akan mencari method getProductName() untuk kolom "productName"
    public String getProductName() {
        return product.getName();
    }

    // TableView akan mencari getPrice() untuk harga satuan
    public double getPrice() {
        return product.getPrice();
    }

    // TableView akan mencari getSubtotal() untuk kolom "subtotal"
    // Logika: Harga x Jumlah
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}