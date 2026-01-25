package com.upb.agripos;

// 1. UBAH IMPORT: Gunakan ProductTableView (File baru yang Anda buat tadi)
import com.upb.agripos.view.ProductTableView; 

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    @Override
    public void start(Stage stage) {
        // 2. GANTI ROOT: Panggil class TableView
        ProductTableView root = new ProductTableView();
        
        // 3. UBAH UKURAN: Buat lebih lebar (600) agar tabel terlihat jelas
        Scene scene = new Scene(root, 650, 600); 
        
        // 4. GANTI JUDUL: Sesuaikan dengan tugas sekarang
        stage.setTitle("Agri-POS Week 13 (TableView)"); 
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}