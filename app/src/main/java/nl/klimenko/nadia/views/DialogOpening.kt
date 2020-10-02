package nl.klimenko.nadia.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import nl.klimenko.nadia.R
import nl.klimenko.nadia.configuration.RetrofitFactory
import nl.klimenko.nadia.models.RegisterMessage
import nl.klimenko.nadia.repository.ArticleService
import nl.klimenko.nadia.repository.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogOpening  {
    var myDialog : Dialog? = null

    fun openDialogWindow(dialog : Dialog){
        if (myDialog==null){
            myDialog = dialog
        }
        myDialog?.setContentView(R.layout.log_in)
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.window?.setGravity(Gravity.BOTTOM)
        myDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog?.window?.findViewById<Button>(R.id.registerButton)?.setOnClickListener{
            myDialog!!.dismiss()
            RegisterView().registerWindow(myDialog!!)
        }
        myDialog?.window?.findViewById<Button>(R.id.loginButton)?.setOnClickListener{
            myDialog!!.dismiss()
            loginWindow()
        }
        myDialog?.show()
    }

    private fun loginWindow(){
        myDialog?.setContentView(R.layout.login)
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.window?.setGravity(Gravity.BOTTOM)
        myDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog?.window?.findViewById<Button>(R.id.loginButton)?.setOnClickListener{
            myDialog!!.dismiss()
            //log in check
        }

        myDialog?.show()
    }
    private fun showLogout(){
        myDialog?.setContentView(R.layout.login)
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.window?.setGravity(Gravity.BOTTOM)
        myDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog?.window?.findViewById<Button>(R.id.logoutButton)?.setOnClickListener{
            myDialog!!.dismiss()
            //log out
        }

        myDialog?.show()
    }


}