package com.example.intent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.intent.databinding.ItemSubmissionBinding

class SubmissionAdapter(private val submissionList: List<Submission>) :
    RecyclerView.Adapter<SubmissionAdapter.SubmissionViewHolder>() {

    class SubmissionViewHolder(val binding: ItemSubmissionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        val binding = ItemSubmissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubmissionViewHolder(binding)
    }

    override fun getItemCount() = submissionList.size

    override fun onBindViewHolder(holder: SubmissionViewHolder, position: Int) {
        val submission = submissionList[position]
        val context = holder.itemView.context

        with(holder.binding) {
            ivSubmissionImage.setImageResource(submission.productImage)
            tvSubmissionName.text = submission.productName
            tvSubmissionDate.text = "Diajukan: ${submission.submissionDate}"
            tvSubmissionQuantity.text = "Jumlah: ${submission.quantity}"
            tvStatusMessage.text = submission.statusMessage

            // Di dalam onBindViewHolder di SubmissionAdapter.kt

// ...
            with(holder.binding) {
                // ... kode lain untuk set gambar, nama, dll ...

                when (submission.status) {
                    SubmissionStatus.PENDING -> {
                        // Gunakan setCardBackgroundColor untuk CardView
                        cardStatusBanner.setCardBackgroundColor(ContextCompat.getColor(context, R.color.status_pending_bg))
                        ivStatusIcon.setImageResource(R.drawable.ic_info)
                    }
                    SubmissionStatus.REJECTED -> {
                        cardStatusBanner.setCardBackgroundColor(ContextCompat.getColor(context, R.color.status_rejected_bg))
                        ivStatusIcon.setImageResource(R.drawable.ic_cancel)
                    }
                    SubmissionStatus.APPROVED -> {
                        cardStatusBanner.setCardBackgroundColor(ContextCompat.getColor(context, R.color.status_approved_bg))
                        ivStatusIcon.setImageResource(R.drawable.ic_check_circle)
                    }
                }
            }
        }
    }
}