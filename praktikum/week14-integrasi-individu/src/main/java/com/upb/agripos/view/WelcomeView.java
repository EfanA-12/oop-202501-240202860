package com.upb.agripos.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class WelcomeView extends VBox {
    
    public WelcomeView(Stage stage) {
        // 1. Setup Layout Utama
        this.setSpacing(25); // Spasi antar elemen
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #ffffff;"); 

        // 2. Judul (Hanya "Sistem POS")
        Label lblTitle = new Label("Sistem POS");
        // Saya perbesar sedikit font-nya (48) agar tetap gagah tanpa logo
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 48)); 
        lblTitle.setTextFill(Color.web("#333")); 

        // (Bagian Logo & Tulisan "Toko Bangunan" SUDAH DIHAPUS)

        // 3. Teks "Masuk sebagai"
        Label lblAsk = new Label("Masuk sebagai");
        lblAsk.setFont(new Font("Arial", 14));
        lblAsk.setTextFill(Color.GRAY);
        lblAsk.setPadding(new javafx.geometry.Insets(10, 0, 5, 0));

        // 4. Dua Tombol Berdampingan
        Button btnKasir = createButton("Kasir");
        Button btnOwner = createButton("Admin");

        // Aksi Tombol
        btnKasir.setOnAction(e -> openLogin(stage, "Kasir"));
        btnOwner.setOnAction(e -> openLogin(stage, "Admin"));

        HBox buttonBox = new HBox(20, btnKasir, btnOwner);
        buttonBox.setAlignment(Pos.CENTER);

        // 5. Gabungkan Semua
        this.getChildren().addAll(lblTitle, lblAsk, buttonBox);
    }

    // --- Method Helper untuk Desain Tombol HIJAU ---
    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(180);
        btn.setPrefHeight(45);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // CSS Style: WARNA HIJAU (#4CAF50)
        String styleNormal = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 30; -fx-cursor: hand;";
        String styleHover = "-fx-background-color: #45a049; -fx-text-fill: white; -fx-background-radius: 30; -fx-cursor: hand;";
        
        btn.setStyle(styleNormal);
        
        // Efek Bayangan
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        shadow.setRadius(5);
        shadow.setOffsetY(3);
        btn.setEffect(shadow);
        
        // Efek Hover
        btn.setOnMouseEntered(e -> btn.setStyle(styleHover));
        btn.setOnMouseExited(e -> btn.setStyle(styleNormal));
        
        return btn;
    }

    private void openLogin(Stage stage, String role) {
        LoginView login = new LoginView(stage, role); 
        Scene scene = new Scene(login, 400, 400); 
        stage.setScene(scene);
        stage.setTitle("Login - " + role);
    }
}