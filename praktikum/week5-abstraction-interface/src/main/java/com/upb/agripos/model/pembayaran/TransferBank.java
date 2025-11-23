package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Validatable;
import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class TransferBank extends Pembayaran implements Validatable, Receiptable {

    private String norek;
    private String kodeVerifikasi;

    public TransferBank(String invoiceNo, double total, String norek, String kodeVerifikasi) {
        super(invoiceNo, total);
        this.norek = norek;
        this.kodeVerifikasi = kodeVerifikasi;
    }

    @Override
    public double biaya() {
        return 4000; 
    }

    @Override
    public boolean validasi() {
        return kodeVerifikasi != null && kodeVerifikasi.length() == 6;
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "METODE:TRASFER BANK %s | TOTAL: %.2f | TRANSFER KE: %s | STATUS: %s",
            invoiceNo,
            totalBayar(),
            norek,
            prosesPembayaran() ? "BERHASIL" : "GAGAL"
        );
    }
}