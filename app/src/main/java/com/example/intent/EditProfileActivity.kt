package com.example.intent

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intent.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Setup Toolbar dengan tombol kembali
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Ambil data lama dan tampilkan di form
        populateProfileData()

        // Atur listener untuk tombol simpan
        binding.btnSaveChanges.setOnClickListener {
            saveChanges()
        }
    }

    private fun populateProfileData() {
        val userDetails = sessionManager.getUserDetails()
        binding.etEditName.setText(userDetails[SessionManager.KEY_NAME])
        binding.etEditEmail.setText(userDetails[SessionManager.KEY_EMAIL])
        // TAMPILKAN DATA BARU
        binding.etEditPhone.setText(userDetails[SessionManager.KEY_PHONE])
        binding.etEditNik.setText(userDetails[SessionManager.KEY_NIK])
        binding.etEditAddress.setText(userDetails[SessionManager.KEY_ADDRESS])
    }

    private fun saveChanges() {
        val newName = binding.etEditName.text.toString()
        val newEmail = binding.etEditEmail.text.toString()
        val newPhone = binding.etEditPhone.text.toString()
        val newNik = binding.etEditNik.text.toString()
        val newAddress = binding.etEditAddress.text.toString()


        // Validasi sederhana
        if (newName.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(this, "Nama dan Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        sessionManager.updateUserData(newName, newEmail, newPhone, newNik, newAddress)

        Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
        finish()
    }

    // Fungsi untuk handle klik tombol kembali di toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}