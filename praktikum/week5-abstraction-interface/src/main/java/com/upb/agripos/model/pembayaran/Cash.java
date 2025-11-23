package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class Cash extends Pembayaran implements Receiptable {

    private final double tunai;

    public Cash(String invoiceNo, double total, double tunai) {
        super(invoiceNo, total);
        this.tunai = tunai;
    }

    @Override
    public double biaya() {
        return 0.0;
    }

    @Override
    public boolean prosesPembayaran() {
        return tunai >= totalBayar();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "METODE:CASH %s | TOTAL: %.2f | BAYAR: %.2f | KEMBALI: %.2f",
            invoiceNo,
            totalBayar(),
            tunai,
            Math.max(0, tunai - totalBayar())
        );
    }
}