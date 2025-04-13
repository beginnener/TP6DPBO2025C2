_Saya Natasha Adinda Cantika dengan NIM 2312120 mengerjakan TP6 dalam mata kuliah DPBO. Untuk keberkahan-Nya, saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin._

# Desain Program

Program ini merupakan implementasi ulang dari permainan terkenal **Flappy Bird** yang dibuat menggunakan **Java Swing GUI**. Dalam permainan ini, pemain diminta untuk mengendalikan seekor burung agar dapat melewati pipa sebanyak mungkin tanpa menabraknya.

Program ini terdiri dari **4 kelas utama**, yaitu:
- `App`
- `FlappyBirb`
- `Pipe`
- `Player`

## Kelas App

Kelas `App` merupakan **kelas utama (main)** dari program ini. Fungsinya adalah:
- Mengatur visibilitas frame menggunakan **`JFrame`**
- Menampilkan komponen-komponen GUI seperti panel permainan

## Kelas FlappyBirb

Kelas `FlappyBirb` merupakan **inti dari logika permainan**, berperan sebagai controller sekaligus view utama game. Fungsinya meliputi:

### Pengaturan Logika Permainan
- **Posisi awal pemain dan pipa**
- **Collision Detection** antara burung dan pipa menggunakan kelas `Rectangle`
- **Perhitungan skor** berdasarkan jumlah pipa yang berhasil dilewati

### Pengaturan Tampilan
- Metode `draw()` menangani semua proses rendering
- Dipanggil dalam `actionPerformed()` yang bekerja berdasarkan timer (`1000/60 ms`)

### Pengaturan Input Pemain
Menggunakan kelas `KeyEvent`, input yang diterima antara lain:
- `Space`: Burung melompat
- `R`: Restart permainan
- `P`: Pause permainan

### Pengaturan Pipa
- Metode `placePipe()` bertugas untuk membuat dan menempatkan pipa baru dengan atribut:
  - `posX`, `posYm`, `velocityX`, `width`, `height`, dan gambar pipa
- Disimpan dalam array `pipes`

### Perhitungan Skor
- Menggunakan `JLabel` untuk menampilkan skor
- Skor bertambah setiap kali burung berhasil melewati pipa:
  ```java
  if (!pipe.isPassed() && pipe.getPosX + pipe.getWidth < player.posX) {
      score++;
      pipe.isPassed = true;
  }
  ```

## Kelas Pipe
Merupakan kelas untuk objek pipe. Berisi,
- `posX` : posisi X pipa.
- `posY` : posisi Y pipa.
- `width` : lebar pipa.
- `height` : tinggi pipa.
- `velocityX` : kecepatan pipa terhadap sumbu X, nilainya negatif karena pipa bergerak kekiri.
- `image` : gambar pipa.


## Kelas Pipe
Merupakan kelas untuk objek player. Berisi,
- `posX` : posisi X player.
- `posY` : posisi Y player.
- `width` : lebar pipa.
- `height` : tinggi pipa.
- `velocityY` : kecepatan pipa terhadap sumbu Y, nilainya nol karena player bergerak keatas dan kebawah.
- `image` : gambar burung.

# Screenshot Program
![Screenshot 2025-04-13 200531](https://github.com/user-attachments/assets/7fa9183a-a0f9-4c48-936e-2bbdb748853f)
![Screenshot 2025-04-13 200544](https://github.com/user-attachments/assets/d760c384-1e46-4c4e-b55e-aa256bd111c4)
![Screenshot 2025-04-13 201509](https://github.com/user-attachments/assets/f18069ee-0b37-4076-bd7f-e729c8fce020)
![Screenshot 2025-04-13 201601](https://github.com/user-attachments/assets/d44c6cf3-79e8-4b70-bf3d-88184478df00)

