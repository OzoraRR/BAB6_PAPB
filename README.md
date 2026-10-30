# Praktikum 06 — Arsitektur Aplikasi Android

**Nama:** Ozora Nathalnael Purba  
**NIM:** 245150207111102  
**Kelas:** TIF D

## Deskripsi
Praktikum ini melanjutkan project NavigationLab dengan menerapkan separation of concerns, yaitu pemisahan tanggung jawab tampilan, pengelolaan state, dan akses data.

## Struktur Arsitektur
- **UI:** Menampilkan data dan meneruskan interaksi pengguna melalui callback.
- **ViewModel:** Mengelola HomeUiState dan DetailUiState.
- **Repository:** Menjadi penghubung antara ViewModel dan sumber data.
- **Data Source:** Menyediakan data mahasiswa secara in-memory.

Aliran data:
UI → ViewModel → StudentRepository → StudentLocalDataSource.

## Fitur
- Home menampilkan lima mahasiswa dengan ID dan nama.
- Pemilihan mahasiswa membuka Detail berdasarkan ID.
- Detail menampilkan nama, program studi, dan angkatan.
- Navigasi Profile, About, dan Kembali.
- Penanganan ID mahasiswa yang tidak ditemukan.

## Teknologi
Kotlin, Jetpack Compose, Navigation Compose, dan ViewModel. State menggunakan mutableStateOf tanpa penambahan Room, REST API, atau Flow.

## Cara Menjalankan
1. Buka folder NavLab di Android Studio.
2. Lakukan Gradle Sync.
3. Pilih emulator atau perangkat Android.
4. Klik Run app.

## Kesimpulan
Pemisahan layer membuat kode lebih terstruktur, mudah diuji, dan mudah dikembangkan tanpa mencampurkan logika data dengan tampilan.
