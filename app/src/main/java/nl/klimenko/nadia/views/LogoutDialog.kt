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
import nl.klimenko.nadia.configuration.SessionManager


class LogoutDialog {

    private lateinit var myDialog: Dialog


    @SuppressLint("SetTextI18n")
    fun showLogout(dialog: Dialog, sessionManager: SessionManager) {
        myDialog = dialog
        myDialog.setContentView(R.layout.logout)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.window?.setGravity(Gravity.BOTTOM)
        myDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        myDialog.window?.findViewById<TextView>(R.id.greeting)?.text =
            myDialog.context.getString(R.string.welcome) + sessionManager.fetchName() + "!"
        myDialog.window?.findViewById<Button>(R.id.logoutButton)?.setOnClickListener {
            //log out
            sessionManager.clearUserCredentials()
            Toast.makeText(
                myDialog.context,
                myDialog.context.getString(R.string.LogoutResult),
                Toast.LENGTH_SHORT
            ).show()
            myDialog.dismiss()

        }

        myDialog.show()
    }
}