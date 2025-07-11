package com.example.intent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.intent.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
        setContentView(binding.root)

        // Listener untuk tombol "Sign In" (yang seharusnya untuk register)
        binding.signInButton.setOnClickListener {
            // Ambil data dari EditText dan hapus spasi di awal/akhir
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            // Validasi input agar tidak kosong
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                // SIMPAN DATA KE SharedPreferences
                sessionManager.saveRegistrationData(name, email, password)

                // Jika berhasil, tampilkan pesan dan pindah ke LoginActivity
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Tutup RegisterActivity agar tidak bisa kembali dengan tombol back
            }
        }

        // Listener untuk teks "Login Here" (ini sudah kamu buat sebelumnya)
        binding.loginHereText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}