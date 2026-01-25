package com.upb.agripos.view;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.upb.agripos.dao.SalesDAO;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CashierView extends VBox { 
    private ProductService productService = new ProductService();
    private CartService cartService = new CartService();
    private SalesDAO salesDAO = new SalesDAO();

    private TableView<Product> tableProduct;
    private TableView<CartItem> tableCart;
    private TextField txtQty;
    private Label lblTotal;

    public CashierView() {
        setPadding(new Insets(20));
        setSpacing(20);
        setStyle("-fx-background-color: #f4f4f4;");

        // --- 1. HEADER (JUDUL + LOGOUT) ---
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label lblTitle = new Label("Kasir Agri-POS");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Dorong tombol logout ke kanan
        
        Button btnLogout = new Button("Keluar / Logout");
        btnLogout.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogout.setOnAction(e -> handleLogout());

        header.getChildren().addAll(lblTitle, spacer, btnLogout);

        // --- 2. KONTEN UTAMA (KIRI & KANAN) ---
        HBox content = new HBox(20);
        
        // --- BAGIAN KIRI: DAFTAR PRODUK ---
        VBox left = new VBox(10); 
        left.setPrefWidth(550);
        
        // Header Katalog (Judul + Tombol Refresh)
        Label lbl1 = new Label("Katalog Produk");
        lbl1.setFont(new Font("Arial", 16));
        
        // TOMBOL REFRESH BARU
        Button btnRefresh = new Button("🔄 Refresh Stok");
        btnRefresh.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnRefresh.setOnAction(e -> {
            loadData(); // Panggil ulang data dari database
            // Opsional: Kasih feedback suara atau animasi
        });

        HBox catalogHeader = new HBox(15, lbl1, btnRefresh);
        catalogHeader.setAlignment(Pos.CENTER_LEFT);

        // Tabel Produk
        tableProduct = new TableView<>();
        tableProduct.getColumns().add(col("Kode", "code"));
        tableProduct.getColumns().add(col("Nama", "name"));
        tableProduct.getColumns().add(col("Harga", "price"));
        tableProduct.getColumns().add(col("Stok", "stock"));
        
        // Input Jumlah Beli
        txtQty = new TextField(); 
        txtQty.setPromptText("Jml"); 
        txtQty.setMaxWidth(60);
        
        Button btnAdd = new Button("Masuk Keranjang");
        btnAdd.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        HBox actionBox = new HBox(10, txtQty, btnAdd);
        
        // Masukkan Header Katalog, Tabel, dan Input ke Layout Kiri
        left.getChildren().addAll(catalogHeader, tableProduct, actionBox);

        // --- BAGIAN KANAN: KERANJANG ---
        VBox right = new VBox(10); 
        right.setPrefWidth(400);
        right.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        
        Label lbl2 = new Label("Keranjang Belanja"); 
        lbl2.setFont(new Font("Arial Bold", 18));

        tableCart = new TableView<>();
        tableCart.setPrefHeight(300);
        tableCart.getColumns().add(col("Barang", "productName"));
        tableCart.getColumns().add(col("Qty", "quantity"));
        tableCart.getColumns().add(col("Subtotal", "subtotal"));

        lblTotal = new Label("Total: Rp 0");
        lblTotal.setFont(new Font("Arial Bold", 22));
        lblTotal.setTextFill(javafx.scene.paint.Color.web("#2e7d32"));

        Button btnPay = new Button("BAYAR & CETAK STRUK");
        btnPay.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        btnPay.setMaxWidth(Double.MAX_VALUE); 
        btnPay.setPrefHeight(50);
        
        right.getChildren().addAll(lbl2, tableCart, lblTotal, btnPay);
        content.getChildren().addAll(left, right);
        
        // Gabungkan Header dan Konten
        this.getChildren().addAll(header, content);

        loadData();

        // --- LOGIKA ---
        
        // Klik Tabel -> Otomatis set qty jadi 1 biar cepat
        tableProduct.setOnMouseClicked(e -> {
            if(tableProduct.getSelectionModel().getSelectedItem() != null) {
                txtQty.setText("1");
                txtQty.requestFocus();
            }
        });

        btnAdd.setOnAction(e -> {
            Product p = tableProduct.getSelectionModel().getSelectedItem();
            if(p != null && !txtQty.getText().isEmpty()) {
                try {
                    int qty = Integer.parseInt(txtQty.getText());
                    if (qty > p.getStock()) {
                        alert(Alert.AlertType.WARNING, "Stok Kurang", "Stok tersisa: " + p.getStock());
                        return;
                    }
                    cartService.addItem(p, qty);
                    refreshCart();
                    txtQty.clear(); // Bersihkan input setelah masuk keranjang
                } catch(NumberFormatException ex) {
                    alert(Alert.AlertType.ERROR, "Error", "Jumlah harus angka!");
                }
            } else {
                alert(Alert.AlertType.WARNING, "Pilih Produk", "Silakan klik produk di tabel dulu!");
            }
        });

        btnPay.setOnAction(e -> {
            if(!cartService.getCartItems().isEmpty()) {
                double total = cartService.calculateGrandTotal();
                
                // 1. Potong Stok
                for(CartItem item : cartService.getCartItems()) {
                    try { productService.reduceStock(item.getProduct().getCode(), ""+item.getQuantity()); } catch(Exception ex){}
                }
                
                // 2. Simpan Laporan
                salesDAO.saveSale(total); 
                
                // 3. CETAK STRUK
                printReceipt();

                // 4. Bersihkan
                cartService.clearCart(); 
                refreshCart(); 
                loadData(); // Auto refresh stok setelah bayar
            } else {
                alert(Alert.AlertType.WARNING, "Kosong", "Keranjang belanja kosong!");
            }
        });
    }

    // --- LOGIKA CETAK STRUK ---
    private void printReceipt() {
        StringBuilder struk = new StringBuilder();
        double grandTotal = cartService.calculateGrandTotal();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        
        struk.append("========== AGRI-POS ==========\n");
        struk.append("Tgl : ").append(dtf.format(LocalDateTime.now())).append("\n");
        struk.append("------------------------------\n");
        
        for (CartItem item : cartService.getCartItems()) {
            struk.append(String.format("%-15s x%d  Rp%d\n", 
                item.getProductName().length() > 15 ? item.getProductName().substring(0,15) : item.getProductName(),
                item.getQuantity(), (long)item.getSubtotal()));
        }
        
        struk.append("------------------------------\n");
        struk.append("TOTAL : Rp ").append((long)grandTotal).append("\n");
        struk.append("==============================\n");

        // Simpan File
        try (FileWriter writer = new FileWriter("struk_belanja.txt")) {
            writer.write(struk.toString());
        } catch (IOException e) { e.printStackTrace(); }

        // Tampilkan Pop-up
        TextArea area = new TextArea(struk.toString());
        area.setEditable(false);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Struk Belanja");
        a.setHeaderText("Transaksi Berhasil");
        a.getDialogPane().setContent(area);
        a.showAndWait();
    }

    private void handleLogout() {
        Stage stage = (Stage) this.getScene().getWindow();
        WelcomeView welcome = new WelcomeView(stage); // Kembali ke Layar Depan
        stage.setScene(new Scene(welcome, 600, 450));
        stage.centerOnScreen();
    }
    
    // Method untuk load data produk dari database
    private void loadData() { 
        tableProduct.getItems().setAll(productService.getAllProducts()); 
    }
    
    private void refreshCart() { 
        tableCart.getItems().setAll(cartService.getCartItems());
        lblTotal.setText("Total: Rp " + (long)cartService.calculateGrandTotal());
    }
    
    private <T> TableColumn<T, String> col(String t, String p) {
        TableColumn<T, String> c = new TableColumn<>(t); c.setCellValueFactory(new PropertyValueFactory<>(p)); return c;
    }
    
    private void alert(Alert.AlertType type, String title, String msg) {
        new Alert(type, msg).show();
    }
}