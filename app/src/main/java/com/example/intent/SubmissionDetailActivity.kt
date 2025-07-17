package com.example.intent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intent.databinding.ActivitySubmissionDetailBinding
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class SubmissionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubmissionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubmissionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ambil data yang dikirim dari SubmissionActivity
        val quantity = intent.getStringExtra("EXTRA_QUANTITY")
        val productName = intent.getStringExtra("EXTRA_PRODUCT_NAME")

        // Format tanggal hari ini
        val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        val currentDate = sdf.format(Date())

        // Tampilkan data ke UI
        binding.tvDetailJumlah.text = "$quantity Gram" // Asumsi satuan gram
        binding.tvDetailDate.text = currentDate
        binding.tvDetailProductType.text = productName

        // Atur listener untuk tombol kembali ke home
        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        // Panggil fungsi untuk memulai animasi konfeti
        showConfetti()
    }

    // Fungsi untuk handle klik tombol kembali di toolbar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun showConfetti() {
        val party = Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3)
        )
        binding.konfettiView.start(party)
    }
}