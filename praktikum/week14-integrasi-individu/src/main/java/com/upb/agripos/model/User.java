package com.upb.agripos.model;

public class User {
    private String username;
    private String password;
    private String role; // <--- Tambahan Baru (Menyimpan 'admin' atau 'kasir')

    // Update Constructor agar menerima role
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    
    // Getter Baru untuk mengambil role
    public String getRole() { return role; } 
}