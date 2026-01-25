package com.upb.agripos.view;

import com.upb.agripos.exception.InvalidQuantityException;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button; // Import baru untuk tata letak tombol
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductFormView extends VBox {
    private TextField txtCode, txtName, txtPrice, txtStock;
    private Button btnSave;   // Tombol Tambah/Simpan
    private Button btnReduce; // Tombol Kurangi (BARU)
    private Button btnDelete; // Tombol Hapus
    private ListView<Product> listView;
    private ProductService service;

    public ProductFormView() {
        this.service = new ProductService();
        initUI();
        initEventHandler();
        refreshData();
    }

    private void initUI() {
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        Label lblTitle = new Label("Sistem Manajemen Stok (Week 12)");
        lblTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // Input Fields
        txtCode = new TextField(); txtCode.setPromptText("Kode Produk (Contoh: P001)");
        txtName = new TextField(); txtName.setPromptText("Nama Produk");
        txtPrice = new TextField(); txtPrice.setPromptText("Harga");
        txtStock = new TextField(); txtStock.setPromptText("Jumlah Stok (Input / Kurang)");

        // --- BUTTONS ---
        btnSave = new Button("Tambah Stok / Simpan");
        btnSave.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Hijau
        btnSave.setMaxWidth(Double.MAX_VALUE);

        btnReduce = new Button("Kurangi Stok"); // Tombol Baru
        btnReduce.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;"); // Oranye
        btnReduce.setMaxWidth(Double.MAX_VALUE);

        btnDelete = new Button("Hapus Produk");
        btnDelete.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;"); // Merah
        btnDelete.setMaxWidth(Double.MAX_VALUE);

        // HBox untuk menjejerkan tombol (Opsional, biar rapi)
        HBox buttonContainer = new HBox(10, btnSave, btnReduce, btnDelete);
        
        listView = new ListView<>();
        
        this.getChildren().addAll(
            lblTitle, 
            new Label("Input Data:"),
            txtCode, txtName, txtPrice, txtStock, 
            new Label("Aksi:"),
            btnSave, btnReduce, btnDelete, // Masukkan semua tombol
            new Label("Daftar Produk:"), 
            listView
        );
    }

    private void initEventHandler() {
        // 1. AKSI TAMBAH / SIMPAN
        btnSave.setOnAction(e -> {
            try {
                service.addProduct(
                    txtCode.getText(),
                    txtName.getText(),
                    txtPrice.getText(),
                    txtStock.getText()
                );
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Stok Bertambah / Produk Disimpan!");
                clearFields();
                refreshData();
            } catch (InvalidQuantityException ex) {
                showAlert(Alert.AlertType.WARNING, "Peringatan", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        // 2. AKSI KURANGI STOK (BARU)
        btnReduce.setOnAction(e -> {
            try {
                // Panggil method reduceStock yang baru kita buat
                service.reduceStock(txtCode.getText(), txtStock.getText());
                
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Stok Berhasil Dikurangi!");
                clearFields();
                refreshData();
            } catch (InvalidQuantityException ex) {
                showAlert(Alert.AlertType.WARNING, "Gagal Kurang", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        // 3. AKSI HAPUS
        btnDelete.setOnAction(e -> {
            Product selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                service.deleteProduct(selected.getCode());
                refreshData();
                clearFields(); // Bersihkan form setelah hapus
                showAlert(Alert.AlertType.INFORMATION, "Terhapus", "Produk berhasil dihapus.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Pilih Produk", "Klik salah satu produk di daftar dulu!");
            }
        });

        // Fitur Klik List -> Isi Form Otomatis
        listView.setOnMouseClicked(e -> {
            Product selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                txtCode.setText(selected.getCode());
                txtName.setText(selected.getName());
                txtPrice.setText(String.valueOf((long)selected.getPrice()));
                txtStock.setText("1"); // Default angka 1 agar siap ditambah/dikurang
            }
        });
    }

    private void refreshData() {
        try {
            listView.getItems().setAll(service.getAllProducts());
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void clearFields() {
        txtCode.clear(); txtName.clear(); txtPrice.clear(); txtStock.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}