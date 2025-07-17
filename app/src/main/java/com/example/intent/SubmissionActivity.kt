package com.example.intent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.intent.databinding.ActivitySubmissionBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SubmissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubmissionBinding
    private lateinit var sessionManager: SessionManager // Pastikan ini ada
    private var suratUri: Uri? = null
    private var ktpUri: Uri? = null
    private var currentProduct: Product? = null // Variabel untuk menyimpan produk

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

        sessionManager = SessionManager(this)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ambil data produk SEKALI SAJA di awal dan simpan
        currentProduct = intent.getParcelableExtra("EXTRA_PRODUCT")

        // Tampilkan info produk jika tidak null
        currentProduct?.let {
            binding.ivProductImageDetail.setImageResource(it.image)
            binding.tvProductNameDetail.text = it.name
        }

        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        binding.btnPilihSurat.setOnClickListener {
            selectSuratLauncher.launch("application/pdf")
        }

        binding.btnPilihKtp.setOnClickListener {
            selectKtpLauncher.launch("image/*")
        }

        binding.btnSubmitPengajuan.setOnClickListener {
            val namaPengaju = binding.etSubmissionName.text.toString()
            val quantity = binding.etSubmissionQuantity.text.toString()

            // Lakukan null check pada produk yang sudah disimpan
            currentProduct?.let { product ->
                // Validasi
                if (namaPengaju.isEmpty() || quantity.isEmpty() || suratUri == null || ktpUri == null) {
                    Toast.makeText(this, "Harap lengkapi semua form dan file", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Buat objek Submission baru
                val sdf = SimpleDateFormat("d MMM yyyy", Locale("id", "ID"))
                val newSubmission = Submission(
                    productImage = product.image,
                    productName = product.name,
                    submissionDate = sdf.format(Date()),
                    quantity = quantity.toInt(),
                    status = SubmissionStatus.PENDING,
                    statusMessage = "Menunggu diterima admin dinas dan UPT"
                )

                sessionManager.saveNewSubmission(newSubmission)

                // Pindah ke halaman detail
                val intent = Intent(this, SubmissionDetailActivity::class.java).apply {
                    putExtra("EXTRA_QUANTITY", quantity)
                    putExtra("EXTRA_PRODUCT_NAME", product.name)
                }
                startActivity(intent)
                finish()

            } ?: run {
                // Tampilkan error jika karena suatu alasan produknya null
                Toast.makeText(this, "Data produk tidak ditemukan, coba lagi.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
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