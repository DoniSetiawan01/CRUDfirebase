package com.example.crudfirebase

class data_mahasiswa {
    // Deklarasi Variable
    var nim: String? = null
    var nama: String? = null
    var jurusan: String? = null
    var key: String? = null

    // Membuat konstruktor kosong untuk membaca data snapshot
    constructor() {}

    // Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari user
    constructor(nim: String?, nama: String?, jurusan: String?) {
        this.nim = nim
        this.nama = nama
        this.jurusan = jurusan
    }
}