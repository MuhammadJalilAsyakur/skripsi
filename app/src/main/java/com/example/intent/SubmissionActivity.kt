package com.example.intent

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.intent.databinding.ActivitySubmissionBinding

class SubmissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubmissionBinding
    private var suratUri: Uri? = null
    private var ktpUri: Uri? = null

    // Launcher untuk memilih file PDF (Surat)
    private val selectSuratLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            suratUri = it
            binding.tvNamaFileSurat.text = getFileName(it)
        }
    }

    // Launcher untuk memilih file Gambar (KTP)
    private val selectKtpLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            ktpUri = it
            binding.tvNamaFileKtp.text = getFileName(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Ambil data produk yang dikirim dari HomeFragment
        val product = intent.getParcelableExtra<Product>("EXTRA_PRODUCT")
        if (product != null) {
            binding.ivProductImageDetail.setImageResource(product.image)
            binding.tvProductNameDetail.text = product.name
        }

        // Atur listener untuk tombol-tombol
        binding.btnPilihSurat.setOnClickListener {
            selectSuratLauncher.launch("application/pdf") // Hanya bisa pilih PDF
        }

        binding.btnPilihKtp.setOnClickListener {
            selectKtpLauncher.launch("image/*") // Hanya bisa pilih Gambar
        }

        binding.btnSubmitPengajuan.setOnClickListener {
            // Logika untuk mengirim data (nanti ke API)
            Toast.makeText(this, "Pengajuan Terkirim!", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk kembali saat panah di toolbar diklik
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    // Fungsi helper untuk mendapatkan nama file dari Uri
    private fun getFileName(uri: Uri): String? {
        var fileName: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }
}