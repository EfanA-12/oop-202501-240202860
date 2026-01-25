package com.upb.agripos;

// 1. Ubah Import dari LoginView ke WelcomeView
import com.upb.agripos.view.WelcomeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        // 2. Panggil WelcomeView sebagai tampilan awal
        WelcomeView root = new WelcomeView(stage);
        
        // 3. Ubah Ukuran Layar agar pas dengan desain WelcomeView
        // (Agak lebih lebar dari Login biasa)
        Scene scene = new Scene(root, 600, 450);
        
        // 4. Ubah Judul Aplikasi
        stage.setTitle("Agri-POS Sistem Toko Bangunan");
        stage.setScene(scene);
        
        // Agar posisi window ada di tengah layar monitor
        stage.centerOnScreen();
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}