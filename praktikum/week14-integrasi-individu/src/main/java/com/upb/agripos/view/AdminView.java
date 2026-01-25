package com.upb.agripos.view;

import java.io.FileWriter;

import com.upb.agripos.dao.SalesDAO;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminView extends VBox { 
    private ProductService productService = new ProductService();
    private SalesDAO salesDAO = new SalesDAO();

    public AdminView() {
        setPadding(new Insets(10));
        setSpacing(10);

        // --- 1. HEADER ---
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #ddd; -fx-background-radius: 5;");
        
        Label lblTitle = new Label("Admin Dashboard");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button btnLogout = new Button("Keluar / Logout");
        btnLogout.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogout.setOnAction(e -> handleLogout());

        header.getChildren().addAll(lblTitle, spacer, btnLogout);

        // --- 2. TAB PANE ---
        TabPane tabPane = new TabPane();
        Tab tabStock = new Tab("Manajemen Produk", createStockView());
        tabStock.setClosable(false);
        Tab tabReport = new Tab("Laporan Keuangan", createReportView());
        tabReport.setClosable(false);
        tabPane.getTabs().addAll(tabStock, tabReport);

        this.getChildren().addAll(header, tabPane);
    }

    // --- UI STOK (UPDATE: Ada Tombol Kurangi Stok) ---
    private VBox createStockView() {
        VBox root = new VBox(10); 
        root.setPadding(new Insets(20));
        
        Label lblHeader = new Label("Input / Edit Produk");
        lblHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField txtCode = new TextField(); txtCode.setPromptText("Kode (P001)");
        TextField txtName = new TextField(); txtName.setPromptText("Nama Produk");
        TextField txtPrice = new TextField(); txtPrice.setPromptText("Harga");
        
        // Ubah prompt text agar user paham fungsinya ganda
        TextField txtStock = new TextField(); txtStock.setPromptText("Stok Awal / Jumlah Pengurangan");
        
        // 1. Tombol Simpan / Tambah (Hijau)
        Button btnSave = new Button("Simpan / Tambah");
        btnSave.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSave.setPrefHeight(35);

        // 2. Tombol Kurangi Stok (Oranye) - FITUR BARU
        Button btnReduce = new Button("Kurangi Stok (-)");
        btnReduce.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        btnReduce.setPrefHeight(35);

        // 3. Tombol Refresh (Biru)
        Button btnRefresh = new Button("Refresh");
        btnRefresh.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRefresh.setPrefHeight(35);

        // 4. Tombol Hapus (Merah)
        Button btnDelete = new Button("Hapus");
        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btnDelete.setPrefHeight(35);

        // Container Tombol
        HBox buttonBox = new HBox(10, btnSave, btnReduce, btnRefresh, btnDelete);

        TableView<Product> table = new TableView<>();
        table.getColumns().add(col("Kode","code")); 
        table.getColumns().add(col("Nama","name")); 
        table.getColumns().add(col("Harga","price"));
        table.getColumns().add(col("Stok","stock"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        // Load Data Awal
        table.getItems().setAll(productService.getAllProducts());

        // --- LOGIKA TOMBOL ---

        // Aksi Simpan / Tambah
        btnSave.setOnAction(e -> {
            try { 
                productService.addProduct(txtCode.getText(), txtName.getText(), txtPrice.getText(), txtStock.getText());
                table.getItems().setAll(productService.getAllProducts()); 
                clearFields(txtCode, txtName, txtPrice, txtStock);
                new Alert(Alert.AlertType.INFORMATION, "Data Tersimpan / Stok Bertambah").show();
            } catch(Exception ex){
                new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show();
            }
        });

        // Aksi Kurangi Stok (BARU)
        btnReduce.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            String qtyStr = txtStock.getText();

            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Pilih produk di tabel dulu!").show();
                return;
            }
            if (qtyStr.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Isi jumlah yang ingin dikurangi di kolom Stok!").show();
                return;
            }

            try {
                // Konfirmasi Pengurangan
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Kurangi Stok");
                confirm.setHeaderText("Konfirmasi Pengurangan");
                confirm.setContentText("Kurangi stok " + selected.getName() + " sebanyak " + qtyStr + "?");

                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            // Menggunakan fungsi reduceStock yang sudah ada (sama kayak kasir)
                            productService.reduceStock(selected.getCode(), qtyStr);
                            
                            // Refresh
                            table.getItems().setAll(productService.getAllProducts());
                            clearFields(txtCode, txtName, txtPrice, txtStock);
                            new Alert(Alert.AlertType.INFORMATION, "Stok Berhasil Dikurangi").show();
                        } catch (Exception ex) {
                            new Alert(Alert.AlertType.ERROR, "Gagal Mengurangi: " + ex.getMessage()).show();
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Aksi Refresh
        btnRefresh.setOnAction(e -> table.getItems().setAll(productService.getAllProducts()));

        // Aksi Hapus
        btnDelete.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Hapus Produk");
                confirm.setHeaderText("Konfirmasi Hapus");
                confirm.setContentText("Yakin ingin menghapus produk: " + selected.getName() + "?");
                
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            productService.deleteProduct(selected.getCode());
                            table.getItems().setAll(productService.getAllProducts());
                            clearFields(txtCode, txtName, txtPrice, txtStock);
                            new Alert(Alert.AlertType.INFORMATION, "Produk Berhasil Dihapus").show();
                        } catch (Exception ex) {
                            new Alert(Alert.AlertType.ERROR, "Gagal Hapus: " + ex.getMessage()).show();
                        }
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING, "Pilih produk di tabel yang ingin dihapus!").show();
            }
        });

        // Klik Tabel -> Isi Form
        table.setOnMouseClicked(e -> {
            Product p = table.getSelectionModel().getSelectedItem();
            if (p != null) {
                txtCode.setText(p.getCode());
                txtName.setText(p.getName());
                txtPrice.setText(String.valueOf((long)p.getPrice()));
                // Kosongkan kolom stok agar admin bisa input jumlah penambahan/pengurangan
                txtStock.setText(""); 
                txtStock.setPromptText("Isi jumlah (+/-)");
            }
        });
        
        root.getChildren().addAll(lblHeader, txtCode, txtName, txtPrice, txtStock, buttonBox, table);
        return root;
    }

    // --- UI LAPORAN ---
    private VBox createReportView() {
        VBox root = new VBox(10); 
        root.setPadding(new Insets(20));
        
        Label lblHeader = new Label("Riwayat Transaksi");
        lblHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TableView tableReport = new TableView();
        tableReport.getColumns().add(col("Tanggal Transaksi", "date"));
        tableReport.getColumns().add(col("Total Pendapatan", "total"));
        tableReport.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        Button btnRefresh = new Button("Refresh Data");
        Button btnPrint = new Button("Cetak Laporan ke File (.txt)");
        btnPrint.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        
        btnRefresh.setOnAction(e -> tableReport.getItems().setAll(salesDAO.getAllSales()));
        
        btnPrint.setOnAction(e -> {
            try (FileWriter writer = new FileWriter("laporan_keuangan.txt")) {
                writer.write("=== LAPORAN KEUANGAN AGRIPOS ===\n\n");
                for(Object item : tableReport.getItems()) {
                    writer.write(item.toString() + "\n");
                }
                new Alert(Alert.AlertType.INFORMATION, "Laporan berhasil dicetak ke laporan_keuangan.txt").show();
            } catch (Exception ex) { ex.printStackTrace(); }
        });
        
        tableReport.getItems().setAll(salesDAO.getAllSales());
        root.getChildren().addAll(lblHeader, new HBox(10, btnRefresh, btnPrint), tableReport);
        return root;
    }

    private void handleLogout() {
        Stage stage = (Stage) this.getScene().getWindow();
        WelcomeView welcome = new WelcomeView(stage);
        stage.setScene(new Scene(welcome, 600, 450));
        stage.centerOnScreen();
    }

    private void clearFields(TextField... fields) {
        for(TextField f : fields) f.clear();
    }

    private <T> TableColumn<T, String> col(String t, String p) {
        TableColumn<T, String> c = new TableColumn<>(t); c.setCellValueFactory(new PropertyValueFactory<>(p)); return c;
    }
}