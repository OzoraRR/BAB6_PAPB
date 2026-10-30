# NavigationLab Praktikum 06

Ozora Nathalnael Purba - 245150207111102

## Jalankan di Android Studio

1. Ekstrak ZIP ini.
2. Pilih Open di Android Studio, lalu pilih folder NavLab yang berisi settings.gradle.kts.
3. Pilih lokasi Android SDK pada komputer Anda jika diminta.
4. Lakukan Gradle Sync. Sinkronisasi pertama memerlukan internet untuk dependency yang belum ada di cache.
5. Pilih emulator atau perangkat Android (minimal API 24), lalu klik Run app.

Project memakai Gradle Wrapper 9.6.0, AGP 9.4.1, compileSdk/targetSdk 37, dan toolchain JDK 25 sesuai konfigurasi project yang telah diuji. Gunakan versi Android Studio yang mendukung AGP tersebut. File local.properties dibuat lokal oleh Android Studio; file lokasi SDK komputer pembuat tidak disertakan.

## Build melalui terminal

Linux/macOS:
    ./gradlew :app:assembleDebug

Windows:
    gradlew.bat :app:assembleDebug

APK hasil build:
    app/build/outputs/apk/debug/app-debug.apk

Home memuat lima mahasiswa. Pilih salah satu untuk membuka Detail; Profile, About, dan tombol Kembali tersedia.

Source aplikasi ini telah berhasil di-build, dengan 9 unit test dan 7 tes emulator lulus pada pengujian 29 Oktober 2026.
