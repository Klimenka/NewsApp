package nl.klimenko.nadia.controllers

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import nl.klimenko.nadia.R
import nl.klimenko.nadia.repository.ArticleService

class DialogOpening {
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
            registerWindow()
        }
        myDialog?.window?.findViewById<Button>(R.id.loginButton)?.setOnClickListener{
            myDialog!!.dismiss()
            loginWindow()
        }
        myDialog?.show()
    }
    private fun registerWindow(){
        myDialog?.setContentView(R.layout.register)
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.window?.setGravity(Gravity.BOTTOM)
        myDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog?.window?.findViewById<Button>(R.id.registerButton)?.setOnClickListener{
            //register check
            val user_name = myDialog?.window?.findViewById<EditText>(R.id.user_name)?.text
            val password = myDialog?.window?.findViewById<EditText>(R.id.password)?.text

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
            myDialog!!.dismiss()

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