package com.upb.agripos.dao;

// PERBAIKAN: Import DatabaseConnection dari package config
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.config.DatabaseConnection;
import com.upb.agripos.model.SaleRecord;

public class SalesDAO {

    // Simpan Transaksi (Dipakai Kasir saat Bayar)
    public void saveSale(double total) {
        String sql = "INSERT INTO sales (total_amount) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setDouble(1, total);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ambil Data Laporan (Dipakai Admin)
    public List<SaleRecord> getAllSales() {
        List<SaleRecord> list = new ArrayList<>();
        String sql = "SELECT date, total_amount FROM sales ORDER BY date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while(rs.next()) {
                // Ambil data dari database dan masukkan ke object SaleRecord
                String tgl = rs.getString("date"); // PostgreSQL timestamp -> String
                double tot = rs.getDouble("total_amount");
                
                list.add(new SaleRecord(tgl, tot));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}