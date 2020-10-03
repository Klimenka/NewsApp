package nl.klimenko.nadia.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import nl.klimenko.nadia.R


class DialogOpening  {
    private lateinit var myDialog : Dialog

    fun openDialogWindow(dialog: Dialog){
        myDialog = dialog
        myDialog.setContentView(R.layout.log_in)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.window?.setGravity(Gravity.BOTTOM)
        myDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog.window?.findViewById<Button>(R.id.registerButton)?.setOnClickListener{
            myDialog.dismiss()
            RegisterDialog().registerWindow(myDialog)
        }
        myDialog.window?.findViewById<Button>(R.id.loginButton)?.setOnClickListener{
            myDialog.dismiss()
            LoginDialog().loginWindow(myDialog)
        }
        myDialog.show()
    }
}