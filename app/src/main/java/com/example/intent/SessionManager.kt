package com.example.intent // Sesuaikan dengan package kamu

import android.content.Context
import android.content.SharedPreferences

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
}