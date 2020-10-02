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
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.models.RegisterMessage
import nl.klimenko.nadia.models.ResultArticle
import nl.klimenko.nadia.repository.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterView : Callback<RegisterMessage> {

    private lateinit var myDialog : Dialog

    fun registerWindow(dialog :Dialog){
        myDialog = dialog
        myDialog.setContentView(R.layout.register)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.window?.setGravity(Gravity.BOTTOM)
        myDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog.window?.findViewById<Button>(R.id.registerButton)?.setOnClickListener{
            //register check
            val user_name = myDialog.window?.findViewById<EditText>(R.id.user_name)?.text.toString().trim()
            val password = myDialog.window?.findViewById<EditText>(R.id.password)?.text.toString().trim()
/*
            if(user_name?.isEmpty()!!){
                myDialog?.window?.findViewById<EditText>(R.id.user_name)?.error = "User Name is required"
                myDialog?.window?.findViewById<EditText>(R.id.user_name)?.requestFocus()
                return@setOnClickListener
            }
            if(password?.isEmpty()!!){
                myDialog?.window?.findViewById<EditText>(R.id.password)?.error = "Password is required"
                myDialog?.window?.findViewById<EditText>(R.id.password)?.requestFocus()
                return@setOnClickListener
            }

 */
            val service = RetrofitFactory.getRetrofitObject()?.create(RegisterService::class.java)
            service?.register(UserName = user_name, Password =  password)?.enqueue(this)

        }
        myDialog.show()
    }

    override fun onFailure(call: Call<RegisterMessage>, t: Throwable) {
        Log.e("HTTP", "Could not register a user", t)
    }

    override fun onResponse(call: Call<RegisterMessage>, response: Response<RegisterMessage>) {
        if (response.isSuccessful && response.body() != null) {
            val responseMessage= response.body()!!
            if(responseMessage.Success){
                Toast.makeText(myDialog.context, "You have been successfully registered", Toast.LENGTH_SHORT).show()
                myDialog.dismiss()
            }
           else{
                Toast.makeText(myDialog.context, "This user nem is already in use, please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}