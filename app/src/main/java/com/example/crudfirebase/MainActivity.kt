package com.example.crudfirebase

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudfirebase.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inisialisasi ID (Button)
        binding.logout.setOnClickListener(this)
        binding.save.setOnClickListener(this)
        binding.showdata.setOnClickListener(this)
        //Mendapatkan Instance Firebase Autentifikasi
        auth = FirebaseAuth.getInstance()
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick (p0: View?) {
        when (p0?.getId()) {
            R.id.save -> {
                // Statement program untuk simpan data
                // Mendapatkan UserID dari pengguna yang Terautentifikasi
                val getUserID = auth!!.currentUser!!.uid

                // Mendapatkan Instance dari Database
                val database = FirebaseDatabase.getInstance()

                // Menyimpan data yang diinputkan user kedalam variable
                val getNIM: String = nim.getText().toString()
                val getNama: String = nama.getText().toString()
                val getJurusan: String = jurusan.getText().toString()

                // Mendapatkan referensi dari Database
                val getReference: DatabaseReference
                getReference = database.reference

                // Mengecek apakah ada data yang kosong
                if (isEmpty(getNIM) || isEmpty(getNama) || isEmpty(getJurusan)) {
                    //Jika ada, maka akan menampilkan pesan singkat seperti berikut ini.
                    Toast.makeText(this@MainActivity, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
                } else {
                    /* Jika tidak, maka data dapat diproses dan menyimpannya pada Database
                    menyimpan data referensi pada database berdasarkan user ID dari masing - masing
                    akun
                    */
                    getReference.child("Admin").child(getUserID).child("Mahasiswa").push()
                        .setValue(data_mahasiswa(getNIM, getNama, getJurusan))
                        .addOnCompleteListener(this) {
                            // Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam database
                            nim.setText("")
                            nama.setText("")
                            jurusan.setText("")
                            Toast.makeText(this@MainActivity, "Data Tersimpan", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.logout -> {
                // Statement program untuk logout/keluar
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(object : OnCompleteListener<Void> {
                        override fun onComplete(p0: Task<Void>) {
                            Toast.makeText(this@MainActivity, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                            intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
            }
            R.id.showdata -> {
            }
        }
    }
}