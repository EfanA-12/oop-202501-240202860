# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik:Pengenalan ke 3 Paradigma Pemrograman yaitu (prosedural, OOP, dan fungsional)

## Identitas
- Nama  : Efan Aryanto Adli
- NIM   : 240202860
- Kelas : 3IKRA

---

## Tujuan
- Mahasiswa dapat memahami perbedaan konsep dan penerapan dari ke tiga paradigma (prosedural, OOP, dan fungsional)
- Mahasiswa dapata membuat contoh program sederhana dengan pendekatan prosedural, OOP, dan fungsional
- Mahasiswa mampu mengidentifikasi kelebihan dan kekurangan masing-masing paradigma (prosedural, OOP, dan fungsional)

---

## Dasar Teori 
1. paradigma prosedural: pemrograman yang berfokus pada urutan langkah-langkah atau instruksi (step by step) untuk menyelesaikan suatu masalah.
2. Paradigma OOP: paradigma pemrograman yang berfokus pada objek sebagai elemen utama dalam membangun program.  
3. Paradigma fungsional: pemrograman yang berfokus pada fungsi murni (pure function) sebagai blok utama program.

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Langkah-langkah yang dilakukan (setup, coding, run).  
2. File/kode yang dibuat.  
3. Commit message yang digunakan.)

---

## Kode Program
(Tuliskan kode utama yang dibuat, contoh:  

### 1. Prosedural
```java
public class HelloProcedural {
   public static void main(String[] args) {
      String nim = "240202860";
      String nama = "Efan Aryanto Adli";
      
      System.out.println("Hello Word");
      System.out.println("I am: " + nama + ", NIM: " + nim);
   }
}
```
### 2. OOP
```java
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
```
### 3. Functional
```java
public class HelloFunctional {
   public static void main(String[] args) {
      String nim = "240202860";
      String nama = "Efan Aryanto Adli";
      System.out.println("Hello World");
      System.out.println("I am: " + nama + ", NIM: " + nim);
   }
}
```
---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/hasil.png)
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan.  
- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.  
- Kendala yang dihadapi dan cara mengatasinya.  
)
---

## Kesimpulan
(Tuliskan kesimpulan dari praktikum minggu ini.  
Contoh: *Dengan menggunakan class dan object, program menjadi lebih terstruktur dan mudah dikembangkan.*)

---

## Quiz
1. Apakah OOP selalu lebih baik dari prosedural?  
   **Jawaban:** OOP tidak selalu lebih baik, tetapi lebih cocok untuk program besar dan kompleks. Sementara paradigma prosedural tetap relevan untuk program kecil, script, dan algoritma sederhana..  

2. Kapan functional programming lebih cocok digunakan dibanding OOP atau prosedural?  
   **Jawaban:**
   Functional programming (FP) lebih cocok digunakan dibanding OOP atau prosedural ketika program:
   - Berfokus pada pengolahan data,
   - Membutuhkan komputasi paralel atau concurrent,
   - Harus minim bug dan efek samping, serta
   - Mengutamakan stabilitas dan prediktabilitas hasil.

3. Bagaimana paradigma (prosedural, OOP, fungsional) memengaruhi maintainability dan scalability aplikasi?  
   **Jawaban:**  
   - Prosedural: Paradigma prosedural cocok untuk program kecil dan sederhana, tetapi kurang cocok untuk aplikasi yang harus berkembang dan dipelihara jangka panjang.  
   - OOP: Paradigma OOP memiliki maintainability dan scalability terbaik di antara ketiganya untuk aplikasi besar, kompleks, dan jangka panjang. 
   - Functional: Paradigma fungsional memiliki maintainability tinggi dan scalability baik untuk sistem komputasi data, tetapi tidak selalu ideal untuk aplikasi interaktif. 

4. Mengapa OOP lebih cocok untuk mengembangkan aplikasi POS dibanding prosedural?  
   **Jawaban:** OOP menyediakan struktur yang lebih kuat dan fleksibel yang cocok untuk sistem yang kompleks dan dinamis seperti aplikasi POS, sedangkan prosedural lebih cocok untuk program kecil dan sederhana dengan alur linear yang jelas. 

5. Bagaimana paradigma fungsional dapat membantu mengurangi kode berulang (boilerplate code)?  
   **Jawaban:** karena memfokuskan pada abstraksi komputasi melalui Fungsi Tingkat Tinggi (Higher-Order Functions - HOF) dan Fungsi sebagai Warga Negara Kelas Satu (First-Class Functions). 

