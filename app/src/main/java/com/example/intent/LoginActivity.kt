package com.example.intent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intent.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager // Tambahkan ini


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this) // Inisialisasi
        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return // Hentikan eksekusi onCreate lebih lanjut
        }
        setContentView(binding.root)

        // Listener untuk tombol Login
        binding.loginButton.setOnClickListener {
            // Di XML login, kamu pakai "name" bukan email, jadi kita pakai name
            val nameInput = binding.nameEditText.text.toString().trim()
            val passwordInput = binding.passwordEditText.text.toString().trim()

            // AMBIL DATA TERSIMPAN
            val storedUserData = sessionManager.getUserDetails()
            val storedName = storedUserData[SessionManager.KEY_NAME]
            val storedPassword = storedUserData[SessionManager.KEY_PASSWORD]

            // Validasi
            if (nameInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Nama dan Password harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // BANDINGKAN INPUT DENGAN DATA TERSIMPAN
            if (nameInput == storedName && passwordInput == storedPassword) {
                // Jika cocok, simpan status login dan pindah ke Main
                sessionManager.setLoginStatus(true)

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Nama atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener untuk teks "Sign Up Here" untuk pindah ke halaman register
        binding.signUpHereText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}