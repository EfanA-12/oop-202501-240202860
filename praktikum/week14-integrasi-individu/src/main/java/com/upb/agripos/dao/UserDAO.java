package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.upb.agripos.config.DatabaseConnection;
import com.upb.agripos.model.User;

public class UserDAO {

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // PERUBAHAN 1: Ambil kolom 'role' dari database
                // Dan masukkan ke Constructor User yang baru (3 parameter)
                return new User(
                    rs.getString("username"), 
                    rs.getString("password"),
                    rs.getString("role") 
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveUser(User user) throws Exception {
        // PERUBAHAN 2: Tambahkan kolom 'role' di SQL Insert
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            
            // PERUBAHAN 3: Simpan role ke database
            stmt.setString(3, user.getRole()); 
            
            stmt.executeUpdate();
        }
    }
}