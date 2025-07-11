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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedSubmissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asumsi layoutnya juga berisi RecyclerView dengan ID rv_completed_submissions
        binding.rvCompletedSubmissions.layoutManager = LinearLayoutManager(requireContext())

        // Buat data dummy lengkap dengan semua status
        val allSubmissions = listOf(
            Submission(R.drawable.tomat, "Tomat", "3 Mei 2025", 124, SubmissionStatus.PENDING, "Menunggu diterima admin dinas dan UPT"),
            Submission(R.drawable.tomat, "Tomat", "3 Mei 2025", 122, SubmissionStatus.REJECTED, "Di Tolak UPT karena stok abis"),
            Submission(R.drawable.tomat, "Jahe", "3 Mei 2025", 112, SubmissionStatus.APPROVED, "Disetujui - Silakan ambil produk di UPT KOTA TANGERANG")
        )

        // Filter daftar untuk menampilkan yang statusnya APPROVED atau REJECTED
        val completedList = allSubmissions.filter {
            it.status == SubmissionStatus.APPROVED || it.status == SubmissionStatus.REJECTED
        }

        // Tampilkan daftar yang sudah difilter ke adapter
        binding.rvCompletedSubmissions.adapter = SubmissionAdapter(completedList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}