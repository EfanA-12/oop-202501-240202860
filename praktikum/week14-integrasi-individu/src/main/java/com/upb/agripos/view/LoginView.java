package com.upb.agripos.view;

import com.upb.agripos.model.User;
import com.upb.agripos.service.UserService; 

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView extends VBox {
    private Stage stage;
    private UserService userService; 
    
    // VARIABEL PENTING: Menyimpan kita sedang di pintu "admin" atau "kasir"
    private String currentMode; 

    // Komponen UI
    private TextField txtUser;
    private PasswordField txtPass, txtConfirmPass;
    private Button btnAction;
    private Label lblTitle, lblSwitch;
    private Text msgError;

    private boolean isRegisterMode = false;

    // UPDATE CONSTRUCTOR: Menerima parameter 'roleMode' dari WelcomeView
    public LoginView(Stage stage, String roleMode) {
        this.stage = stage;
        this.currentMode = roleMode; // Simpan mode (misal: "admin")
        this.userService = new UserService(); 
        initUI();
    }

    private void initUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(40));
        this.setAlignment(Pos.CENTER);
        
        // 1. BACKGROUND PUTIH
        this.setStyle("-fx-background-color: #ffffff;");

        // 2. JUDUL MENYESUAIKAN MODE
        String judul = currentMode.equalsIgnoreCase("admin") ? "Login Admin" : "Login Kasir";
        lblTitle = new Label(judul);
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblTitle.setTextFill(Color.web("#333"));

        // Style Input Rounded
        String styleInput = "-fx-background-radius: 20; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 20;";
        
        txtUser = new TextField();
        txtUser.setPromptText("Username");
        txtUser.setMaxWidth(300);
        txtUser.setStyle(styleInput);

        txtPass = new PasswordField();
        txtPass.setPromptText("Password");
        txtPass.setMaxWidth(300);
        txtPass.setStyle(styleInput);

        txtConfirmPass = new PasswordField();
        txtConfirmPass.setPromptText("Konfirmasi Password");
        txtConfirmPass.setMaxWidth(300);
        txtConfirmPass.setStyle(styleInput);
        txtConfirmPass.setVisible(false);
        txtConfirmPass.setManaged(false);

        msgError = new Text();
        msgError.setFill(Color.RED);
        msgError.setFont(Font.font("Arial", 12));

        // 3. TOMBOL AKSI (HIJAU)
        btnAction = new Button("MASUK");
        btnAction.setPrefWidth(300);
        btnAction.setPrefHeight(45);
        btnAction.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        String styleBtnNormal = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 30; -fx-cursor: hand;";
        String styleBtnHover = "-fx-background-color: #45a049; -fx-text-fill: white; -fx-background-radius: 30; -fx-cursor: hand;";
        
        btnAction.setStyle(styleBtnNormal);
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        shadow.setRadius(5);
        shadow.setOffsetY(3);
        btnAction.setEffect(shadow);

        btnAction.setOnMouseEntered(e -> btnAction.setStyle(styleBtnHover));
        btnAction.setOnMouseExited(e -> btnAction.setStyle(styleBtnNormal));

        // 4. LINK GANTI MODE
        lblSwitch = new Label("Belum punya akun? Daftar disini");
        lblSwitch.setTextFill(Color.web("#2e7d32"));
        lblSwitch.setUnderline(true);
        lblSwitch.setCursor(javafx.scene.Cursor.HAND);
        lblSwitch.setFont(Font.font("Arial", 12));
        
        // 5. TOMBOL KEMBALI
        Button btnBack = new Button("Kembali ke Menu Awal");
        btnBack.setStyle("-fx-background-color: transparent; -fx-text-fill: gray; -fx-cursor: hand;");
        btnBack.setOnAction(e -> {
            WelcomeView welcome = new WelcomeView(stage);
            stage.setScene(new Scene(welcome, 600, 450));
            stage.centerOnScreen();
        });

        // Setup Events
        btnAction.setOnAction(e -> handleAction());
        lblSwitch.setOnMouseClicked(e -> toggleMode());

        this.getChildren().addAll(lblTitle, txtUser, txtPass, txtConfirmPass, msgError, btnAction, lblSwitch, btnBack);
    }

    private void toggleMode() {
        isRegisterMode = !isRegisterMode;
        msgError.setText("");

        if (isRegisterMode) {
            lblTitle.setText("Buat Akun " + (currentMode.equalsIgnoreCase("admin") ? "Admin" : "Kasir"));
            btnAction.setText("DAFTAR SEKARANG");
            lblSwitch.setText("Sudah punya akun? Login disini");
            txtConfirmPass.setVisible(true);
            txtConfirmPass.setManaged(true);
        } else {
            lblTitle.setText("Login " + (currentMode.equalsIgnoreCase("admin") ? "Admin" : "Kasir"));
            btnAction.setText("MASUK");
            lblSwitch.setText("Belum punya akun? Daftar disini");
            txtConfirmPass.setVisible(false);
            txtConfirmPass.setManaged(false);
        }
    }

    private void handleAction() {
        String user = txtUser.getText().trim();
        String pass = txtPass.getText();
        String confirm = txtConfirmPass.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            msgError.setText("Username dan Password harus diisi!");
            return;
        }

        try {
            if (isRegisterMode) {
                // --- DAFTAR ---
                if (!pass.equals(confirm)) {
                    msgError.setText("Password konfirmasi tidak sama!");
                    return;
                }
                
                // Mendaftar otomatis sebagai role sesuai pintu (Admin/Kasir)
                userService.register(user, pass, currentMode.toLowerCase());
                
                showAlert("Sukses", "Akun " + currentMode + " berhasil dibuat! Silakan Login.");
                toggleMode();

            } else {
                // --- LOGIN ---
                User loggedInUser = userService.login(user, pass);
                
                if (loggedInUser != null) {
                    // CEK KEAMANAN: Apakah jabatan user SAMA dengan pintu yang dimasuki?
                    if (loggedInUser.getRole().equalsIgnoreCase(currentMode)) {
                        openMainApp(loggedInUser.getRole());
                    } else {
                        msgError.setText("Akun ini bukan " + currentMode + "! Silakan pindah menu.");
                    }
                } else {
                    msgError.setText("Username atau Password salah!");
                }
            }
        } catch (Exception e) {
            msgError.setText("Error: " + e.getMessage());
        }
    }

    private void openMainApp(String role) {
        if (role.equalsIgnoreCase("admin")) {
            AdminView adminRoot = new AdminView();
            stage.setScene(new Scene(adminRoot, 800, 600));
            stage.setTitle("Agri-POS [Mode Admin]");
        } else {
            CashierView cashierRoot = new CashierView();
            stage.setScene(new Scene(cashierRoot, 900, 600));
            stage.setTitle("Agri-POS [Mode Kasir]");
        }
        stage.centerOnScreen();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}