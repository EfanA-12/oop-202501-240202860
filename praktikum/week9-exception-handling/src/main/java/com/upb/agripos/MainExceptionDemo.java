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