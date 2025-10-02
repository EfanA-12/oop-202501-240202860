class Mahasiswa {
    String nama;
    int umur;
    int NIM;

    Mahasiswa(String n, int u, int y){
        nama = n;
        umur = u;
        NIM = y;
    }

    void sapa() {
        System.out.println("Halo, saya " + nama + ". Umur saya " + umur + " tahun. NIM saya " + NIM);
    }
}

public class Helloop {
    public static void main(String[] args) {
        Mahasiswa m1 = new Mahasiswa("Efan Aryanto Adli", 19, 240202860);
        m1.sapa();
    }
}