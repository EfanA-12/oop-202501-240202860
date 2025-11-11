package main.java.com.upb.agripos.model;

public class ObatHama extends Produk {
    private String kandunganAktif;

    public ObatHama(String kode, String nama, double harga, int stok, String kandunganAktif) {
        super(kode, nama, harga, stok);
        this.kandunganAktif = kandunganAktif;
    }

    @Override
    public String getInfo() {
        return "Obat Hama - " + super.getInfo() + ", Kandungan Aktif: " + kandunganAktif;
    }
}