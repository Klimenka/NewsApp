package nl.klimenko.nadia.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import nl.klimenko.nadia.R
import nl.klimenko.nadia.configuration.RetrofitFactory
import nl.klimenko.nadia.configuration.SessionManager
import nl.klimenko.nadia.models.LoginToken
import nl.klimenko.nadia.repository.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginDialog : Callback<LoginToken> {
    private lateinit var myDialog: Dialog
    lateinit var sessionManager: SessionManager

    fun loginWindow(dialog: Dialog, manager: SessionManager) {
        sessionManager = manager
        myDialog = dialog
        myDialog.setContentView(R.layout.login)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.window?.setGravity(Gravity.BOTTOM)
        myDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        myDialog.window?.findViewById<Button>(R.id.loginButton)?.setOnClickListener {
            //log in check
            val userName =
                myDialog.window?.findViewById<EditText>(R.id.user_name)?.text.toString().trim()
            val password =
                myDialog.window?.findViewById<EditText>(R.id.password)?.text.toString().trim()
            if (userName.isEmpty()) {
                myDialog.window?.findViewById<EditText>(R.id.user_name)?.error =
                    myDialog.context.getString(R.string.userNameRequired)
                myDialog.window?.findViewById<EditText>(R.id.user_name)?.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                myDialog.window?.findViewById<EditText>(R.id.password)?.error =
                    myDialog.context.getString(R.string.passwordRequired)
                myDialog.window?.findViewById<EditText>(R.id.password)?.requestFocus()
                return@setOnClickListener
            }
            val service = RetrofitFactory.getRetrofitObject()?.create(LoginService::class.java)
            service?.login(UserName = userName, Password = password)?.enqueue(this)
            sessionManager.saveName(userName)
        }

        myDialog.show()
    }

    override fun onFailure(call: Call<LoginToken>, t: Throwable) {
        Log.e("HTTP", "Could not login a user", t)
        sessionManager.clearUserCredentials()
        Toast.makeText(
            myDialog.context,
            myDialog.context.getString(R.string.wrong),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onResponse(call: Call<LoginToken>, response: Response<LoginToken>) {
        if (response.isSuccessful && response.body() != null) {
            val token = response.body()!!
            sessionManager.saveAuthToken(token.AuthToken)
            Toast.makeText(
                myDialog.context,
                myDialog.context.getString(R.string.loggedIn),
                Toast.LENGTH_SHORT
            ).show()
            myDialog.dismiss()
        }
    }
}