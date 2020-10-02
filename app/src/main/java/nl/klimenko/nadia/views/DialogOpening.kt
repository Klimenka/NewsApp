package nl.klimenko.nadia.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import nl.klimenko.nadia.R


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
            LoginView().loginWindow(myDialog!!)
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