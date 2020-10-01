package nl.klimenko.nadia.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.klimenko.nadia.R

class LogInView : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)
        this.setFinishOnTouchOutside(false);
    }
}