package com.example.intent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Enum untuk merepresentasikan status yang berbeda
enum class SubmissionStatus {
    PENDING, REJECTED, APPROVED
}

@Parcelize
data class Submission(
    val productImage: Int,
    val productName: String,
    val submissionDate: String,
    val quantity: Int,
    val status: SubmissionStatus,
    val statusMessage: String
) : Parcelable