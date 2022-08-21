package com.jetpack_compose_practice.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.Intent
import com.jetpack_compose_practice.MainActivity


/**
 * Created by Kamesh singh.
 */


class AppPreferences(private val context: Context) {
    var mPrefs: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    //-----ClearPreference method-----//
    fun clearPreference() {
        val editor = mPrefs.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    fun getStringValue(Key: String): String? {
        return mPrefs.getString(Key, "")
    }

    fun getBooleanValue(Key: String): Boolean {
        return mPrefs.getBoolean(Key, false)
    }

    fun getIntegerValue(Key: String): Int {
        return mPrefs.getInt(Key, 0)
    }

    fun setStringValue(Key: String, value: String?) {
        val editor = mPrefs.edit()
        editor.putString(Key, value)
        editor.apply()
    }

    fun setBooleanValue(Key: String, value: Boolean) {
        val editor = mPrefs.edit()
        editor.putBoolean(Key, value)
        editor.apply()
    }

    fun setIntegerValue(Key: String, value: Int) {
        val editor = mPrefs.edit()
        editor.putInt(Key, value)
        editor.apply()
    }

    companion object {
        val SHARED_PREFERENCE_NAME = "Jetpack_compose_App"
        val IS_LOGIN = "isLoggedIn"
        val AUTH_TOKEN = "auth_token"
        val USER_ID = "user_id"
        val USERNAME = "username"
        val PHONE = "phone"
        val IMAGE = "image"
        val NAME = "first_name"

        fun getPrefs(context: Context): AppPreferences {
            return AppPreferences(context)

        }
    }
}
