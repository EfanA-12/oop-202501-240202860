package com.upb.agripos.model;

public class SaleRecord {
    private String date;
    private double total;

    public SaleRecord(String date, double total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() { return date; }
    public double getTotal() { return total; }
    
    // Override toString agar saat diprint/debug hasilnya terbaca
    @Override
    public String toString() {
        return date + " - Rp " + (long)total;
    }
}