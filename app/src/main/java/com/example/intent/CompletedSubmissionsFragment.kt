package com.example.intent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intent.databinding.FragmentCompletedSubmissionsBinding

class CompletedSubmissionsFragment : Fragment() {

    private var _binding: FragmentCompletedSubmissionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager // <-- 1. TAMBAHKAN DEKLARASI INI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedSubmissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext()) // Inisialisasi
        binding.rvCompletedSubmissions.layoutManager = LinearLayoutManager(requireContext())

        // Ambil SEMUA pengajuan dari SharedPreferences
        val allSubmissions = sessionManager.getSubmissions()

        // Filter untuk yang statusnya SELESAI (Approved atau Rejected)
        val completedList = allSubmissions.filter {
            it.status == SubmissionStatus.APPROVED || it.status == SubmissionStatus.REJECTED
        }

        // Di dalam onViewCreated

        // ... setelah mengambil allSubmissions dan mem-filternya menjadi activeList ...

        if (completedList.isEmpty()) {
            // Jika daftar kosong, tampilkan layout empty state
            binding.rvCompletedSubmissions.visibility = View.GONE
            binding.emptyStateLayout.root.visibility = View.VISIBLE
            // (Opsional) Ubah teks sesuai konteks
            binding.emptyStateLayout.tvEmptyTitle.text = "Belum Ada Pengajuan Selesai"
            binding.emptyStateLayout.tvEmptySubtitle.text = "Pengajuan yang sudah diproses akan muncul di sini."
        } else {
            // Jika ada data, tampilkan RecyclerView
            binding.rvCompletedSubmissions.visibility = View.VISIBLE
            binding.emptyStateLayout.root.visibility = View.GONE
            binding.rvCompletedSubmissions.adapter = SubmissionAdapter(completedList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}