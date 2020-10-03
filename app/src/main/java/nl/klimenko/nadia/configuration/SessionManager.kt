package nl.klimenko.nadia.configuration

import android.content.Context
import android.content.SharedPreferences
import nl.klimenko.nadia.R
import java.io.Serializable


class SessionManager(context: Context) : Serializable {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
    }


    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }
    fun saveName(name: String) {
        val editor = prefs.edit()
        editor.putString(USER_NAME, name)
        editor.apply()
    }
    fun clearUserCredentials(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()

    }


    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchName(): String? {
        return prefs.getString(USER_NAME, null)
    }
}
