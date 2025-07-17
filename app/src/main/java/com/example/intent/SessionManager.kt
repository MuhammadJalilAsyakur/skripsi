package com.example.intent // Sesuaikan dengan package kamu

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences("MyAppSession", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = prefs.edit()

    companion object {
        const val IS_LOGGED_IN = "isLoggedIn"
        const val KEY_NAME = "userName"
        const val KEY_EMAIL = "userEmail"
        const val KEY_PASSWORD = "userPassword"
        // TAMBAHKAN KUNCI BARU
        const val KEY_PHONE = "userPhone"
        const val KEY_NIK = "userNik"
        const val KEY_ADDRESS = "userAddress"
        const val KEY_SUBMISSIONS = "userSubmissions" // Key baru untuk daftar pengajuan

    }
    /**
     * Menyimpan data user saat registrasi
     */
    fun saveRegistrationData(name: String, email: String, password: String) {
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.apply() // Jangan lupa .apply() untuk menyimpan
    }

    /**
     * Mengambil data user yang tersimpan
     */
    // Perbarui fungsi untuk mengambil semua data
    fun getUserDetails(): HashMap<String, String?> {
        val user = HashMap<String, String?>()
        user[KEY_NAME] = prefs.getString(KEY_NAME, null)
        user[KEY_EMAIL] = prefs.getString(KEY_EMAIL, null)
        user[KEY_PASSWORD] = prefs.getString(KEY_PASSWORD, null)
        // AMBIL DATA BARU
        user[KEY_PHONE] = prefs.getString(KEY_PHONE, null)
        user[KEY_NIK] = prefs.getString(KEY_NIK, null)
        user[KEY_ADDRESS] = prefs.getString(KEY_ADDRESS, null)
        return user
    }
    /**
     * Menyimpan status login
     */
    fun setLoginStatus(isLoggedIn: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    /**
     * Mengecek apakah user sudah login atau belum
     */
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    /**
     * Menghapus semua data sesi (untuk logout)
     */
    fun logoutUser() {
        // HANYA ubah status login menjadi false
        editor.putBoolean(IS_LOGGED_IN, false)
        editor.apply()
    }

    // Di dalam kelas SessionManager
    fun updateUserData(name: String, email: String, phone: String, nik: String, address: String) {
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PHONE, phone)
        editor.putString(KEY_NIK, nik)
        editor.putString(KEY_ADDRESS, address)
        editor.apply()
    }


    fun saveNewSubmission(newSubmission: Submission) {
        // 1. Ambil daftar pengajuan yang sudah ada (jika ada)
        val existingSubmissions = getSubmissions().toMutableList()

        // 2. Tambahkan pengajuan baru ke dalam daftar
        existingSubmissions.add(newSubmission)

        // 3. Ubah daftar yang sudah diupdate menjadi string JSON
        val jsonString = Gson().toJson(existingSubmissions)

        // 4. Simpan kembali ke SharedPreferences
        editor.putString(KEY_SUBMISSIONS, jsonString)
        editor.apply()
    }
    fun getSubmissions(): List<Submission> {
        // Ambil string JSON dari SharedPreferences
        val jsonString = prefs.getString(KEY_SUBMISSIONS, null)

        // Jika tidak ada data, kembalikan daftar kosong
        if (jsonString == null) {
            return emptyList()
        }

        // Ubah string JSON kembali menjadi List<Submission>
        val listType = object : TypeToken<List<Submission>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}