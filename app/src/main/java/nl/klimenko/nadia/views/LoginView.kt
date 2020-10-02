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
import nl.klimenko.nadia.models.LoginToken
import nl.klimenko.nadia.models.RegisterMessage
import nl.klimenko.nadia.repository.LoginService
import nl.klimenko.nadia.repository.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginView : Callback<LoginToken> {
    private lateinit var myDialog : Dialog

    fun loginWindow(dialog :Dialog){
        myDialog = dialog
        myDialog.setContentView(R.layout.login)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.window?.setGravity(Gravity.BOTTOM)
        myDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog.window?.findViewById<Button>(R.id.loginButton)?.setOnClickListener{
            //log in check
            val user_name = myDialog.window?.findViewById<EditText>(R.id.user_name)?.text.toString().trim()
            val password = myDialog.window?.findViewById<EditText>(R.id.password)?.text.toString().trim()
            val service = RetrofitFactory.getRetrofitObject()?.create(LoginService::class.java)
            service?.login(UserName = user_name, Password =  password)?.enqueue(this)
            Toast.makeText(myDialog.context, "Waiting...", Toast.LENGTH_SHORT).show()
        }

        myDialog.show()
    }
    override fun onFailure(call: Call<LoginToken>, t: Throwable) {
        Log.e("HTTP", "Could not login a user", t)
    }

    override fun onResponse(call: Call<LoginToken>, response: Response<LoginToken>) {
        if (response.isSuccessful && response.body() != null) {
            val token = response.body()!!
            Toast.makeText(myDialog.context, "You are logged in", Toast.LENGTH_SHORT).show()
            print(token.AuthToken)
            myDialog.dismiss()
        }
    }
}