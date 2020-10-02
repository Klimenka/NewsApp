package nl.klimenko.nadia.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import nl.klimenko.nadia.R
import nl.klimenko.nadia.models.User
import org.w3c.dom.Text


class LogoutView {

    private var myDialog : Dialog? = null


    @SuppressLint("SetTextI18n")
    public fun showLogout(dialog : Dialog){
        myDialog = dialog
        myDialog?.setContentView(R.layout.logout)
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.window?.setGravity(Gravity.BOTTOM)
        myDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog?.window?.findViewById<TextView>(R.id.greeting)?.text =
            myDialog!!.context.getString(R.string.welcome) + User.getUser().name + "!"
        myDialog?.window?.findViewById<Button>(R.id.logoutButton)?.setOnClickListener{
            //log out
            User.clearCredentials()
            Toast.makeText(myDialog!!.context, "You have been logged out", Toast.LENGTH_SHORT).show()
            myDialog!!.dismiss()

        }

        myDialog?.show()
    }
}