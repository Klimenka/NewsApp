package nl.klimenko.nadia.views

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.articles_view.*
import kotlinx.android.synthetic.main.recyclerview.*
import nl.klimenko.nadia.R
import nl.klimenko.nadia.controllers.ArticleListener
import nl.klimenko.nadia.controllers.MyAdapter
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.models.ResultArticle
import nl.klimenko.nadia.repository.ArticleService
import retrofit2.*

import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity(), Callback<ResultArticle> {
    var myDialog : Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview)
        myDialog = Dialog(this)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://inhollandbackend.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(ArticleService::class.java)
        service.articles().enqueue(this)
        longLoading()
    }

    override fun onFailure(call: Call<ResultArticle>, t: Throwable) {
        Log.e("HTTP", "Could not fetch data", t)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    fun onGroupItemClick(item: MenuItem) {
         if (item.title == "Log in"){
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
    }
    private fun registerWindow(){
        myDialog?.setContentView(R.layout.register)
        myDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog?.window?.setGravity(Gravity.BOTTOM)
        myDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        myDialog?.window?.findViewById<Button>(R.id.registerButton)?.setOnClickListener{
            myDialog!!.dismiss()
            //register check
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
    private fun shortLoading(){
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
    }
    private fun longLoading(){
        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show()
    }
    override fun onResponse(call: Call<ResultArticle>, response: Response<ResultArticle>) {
        if (response.isSuccessful && response.body() != null) {
            val intent = Intent(this, DetailedArticle::class.java)
            val articles= response.body()!!.Results as List<Article>
            this.recyclerview.layoutManager = LinearLayoutManager(this)
            this.recyclerview.adapter = MyAdapter(
                this,
                articles,
                object : ArticleListener {
                    override fun onArticleClicked(article: Article) {
                        shortLoading()
                        intent.putExtra("Article", article)
                        startActivity(intent)
                        }
                })
        }
    }
}

