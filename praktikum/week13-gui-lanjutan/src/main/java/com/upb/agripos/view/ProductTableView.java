package com.upb.agripos.view;

import com.upb.agripos.exception.InvalidQuantityException;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductTableView extends VBox {
    // Komponen GUI
    private TableView<Product> table; 
    private TextField txtCode, txtName, txtPrice, txtStock;
    private Button btnSave, btnReduce, btnDelete; // <--- btnReduce ditambahkan lagi
    
    private ProductService service;

    public ProductTableView() {
        this.service = new ProductService();
        initUI();
        initEventHandler();
        loadData(); // Load data saat aplikasi dibuka
    }

    private void initUI() {
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        Label lblTitle = new Label("Manajemen Produk (Week 13 - TableView)");
        lblTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // --- 1. MEMBUAT TABEL DAN KOLOM ---
        table = new TableView<>();
        
        TableColumn<Product, String> colCode = new TableColumn<>("Kode Produk");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code")); 

        TableColumn<Product, String> colName = new TableColumn<>("Nama Produk");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setMinWidth(200);

        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(colCode, colName, colPrice, colStock);

        // --- 2. FORM INPUT ---
        txtCode = new TextField(); txtCode.setPromptText("Kode");
        txtName = new TextField(); txtName.setPromptText("Nama Produk");
        txtPrice = new TextField(); txtPrice.setPromptText("Harga");
        txtStock = new TextField(); txtStock.setPromptText("Stok (Input / Kurang)");

        // --- 3. TOMBOL (Sekarang ada 3) ---
        btnSave = new Button("Simpan / Tambah");
        btnSave.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Hijau
        btnSave.setPrefWidth(130);
        
        btnReduce = new Button("Kurangi Stok"); // <--- INI DIA TOMBOLNYA
        btnReduce.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;"); // Oranye
        btnReduce.setPrefWidth(130);

        btnDelete = new Button("Hapus");
        btnDelete.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;"); // Merah
        btnDelete.setPrefWidth(100);

        // Masukkan ketiga tombol ke dalam kotak baris (HBox)
        HBox buttonBox = new HBox(10, btnSave, btnReduce, btnDelete);

        // Masukkan semua ke layar
        this.getChildren().addAll(lblTitle, 
            new Label("Input Data:"),
            txtCode, txtName, txtPrice, txtStock, 
            new Label("Aksi:"),
            buttonBox,
            new Label("Daftar Produk (Tabel):"), 
            table);
    }

    private void initEventHandler() {
        // --- 1. Event Simpan / Tambah ---
        btnSave.setOnAction(e -> {
            try {
                service.addProduct(
                    txtCode.getText(),
                    txtName.getText(),
                    txtPrice.getText(),
                    txtStock.getText()
                );
                loadData(); // Refresh tabel
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil disimpan!");
            } catch (InvalidQuantityException ex) {
                showAlert(Alert.AlertType.WARNING, "Validasi Error", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "System Error", ex.getMessage());
            }
        });

        // --- 2. Event Kurangi Stok (Fitur Week 12 dikembalikan) ---
        btnReduce.setOnAction(e -> {
            try {
                service.reduceStock(txtCode.getText(), txtStock.getText());
                loadData(); // Refresh tabel
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Stok berhasil dikurangi!");
            } catch (InvalidQuantityException ex) {
                showAlert(Alert.AlertType.WARNING, "Gagal Kurang", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "System Error", ex.getMessage());
            }
        });

        // --- 3. Event Hapus ---
        btnDelete.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                service.deleteProduct(selected.getCode());
                loadData(); // Refresh tabel
                clearFields(); 
                showAlert(Alert.AlertType.INFORMATION, "Terhapus", "Produk berhasil dihapus.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Peringatan", "Klik salah satu baris di tabel dulu!");
            }
        });

        // Klik Tabel -> Isi Form Otomatis
        table.setOnMouseClicked(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                txtCode.setText(selected.getCode());
                txtName.setText(selected.getName());
                txtPrice.setText(String.valueOf((long)selected.getPrice()));
                txtStock.setText("1"); // Default angka 1 agar siap dikurangi
            }
        });
    }

    private void loadData() {
        try {
            table.getItems().setAll(service.getAllProducts());
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