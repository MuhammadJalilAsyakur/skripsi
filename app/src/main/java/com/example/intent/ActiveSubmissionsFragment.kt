package com.example.intent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intent.databinding.FragmentActiveSubmissionsBinding

class ActiveSubmissionsFragment : Fragment() {

    private var _binding: FragmentActiveSubmissionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager // <-- 1. TAMBAHKAN DEKLARASI INI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveSubmissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext()) // Inisialisasi
        binding.rvActiveSubmissions.layoutManager = LinearLayoutManager(requireContext())

        // Buat data dummy lengkap dengan semua status
        // Ambil SEMUA pengajuan dari SharedPreferences
        val allSubmissions = sessionManager.getSubmissions()

        // Filter hanya untuk yang statusnya PENDING
        val activeList = allSubmissions.filter { it.status == SubmissionStatus.PENDING }


        if (activeList.isEmpty()) {
            // Jika daftar kosong, tampilkan layout empty state
            binding.rvActiveSubmissions.visibility = View.GONE
            binding.emptyStateLayout.root.visibility = View.VISIBLE
            // (Opsional) Ubah teks sesuai konteks
            binding.emptyStateLayout.tvEmptyTitle.text = "Belum Ada Pengajuan Aktif"
            binding.emptyStateLayout.tvEmptySubtitle.text = "Pengajuan yang sedang diproses akan muncul di sini."
        } else {
            // Jika ada data, tampilkan RecyclerView
            binding.rvActiveSubmissions.visibility = View.VISIBLE
            binding.emptyStateLayout.root.visibility = View.GONE
            binding.rvActiveSubmissions.adapter = SubmissionAdapter(activeList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}