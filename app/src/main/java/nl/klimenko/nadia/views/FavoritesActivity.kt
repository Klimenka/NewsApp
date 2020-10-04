package nl.klimenko.nadia.views

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview.*
import nl.klimenko.nadia.R
import nl.klimenko.nadia.configuration.RetrofitFactory
import nl.klimenko.nadia.configuration.SessionManager
import nl.klimenko.nadia.controllers.ArticleListener
import nl.klimenko.nadia.controllers.MyAdapter
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.models.ResultArticle
import nl.klimenko.nadia.repository.ArticleService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesActivity: AppCompatActivity(), Callback<ResultArticle> {
    private lateinit var myDialog : Dialog
    lateinit var sessionManager : SessionManager
    lateinit var service : ArticleService
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview)
        this.recyclerview.visibility = View.GONE
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        myDialog = Dialog(this)
        sessionManager = SessionManager(this)
        intent = Intent(this, DetailedArticle::class.java)
        val retrofit = RetrofitFactory.getRetrofitObject()
        service = retrofit?.create(ArticleService::class.java)!!
        service.likedArticles(sessionManager.fetchAuthToken()).enqueue(this)
    }
    override fun onFailure(call: Call<ResultArticle>, t: Throwable) {
        progressBar.visibility = View.GONE
        Log.e("HTTP", "Could not fetch data", t)
        setContentView(R.layout.tryagain)
        findViewById<Button>(R.id.try_again_button).setOnClickListener {
            this.recreate()
        }
        Toast.makeText(this, this.getString(R.string.network), Toast.LENGTH_LONG).show()
    }

    override fun onResponse(call: Call<ResultArticle>, response: Response<ResultArticle>) {
        if (response.isSuccessful && response.body() != null) {
            this.recyclerview.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            val articles = response.body()!!.Results as List<Article>
            this.recyclerview.layoutManager = LinearLayoutManager(this)
            this.recyclerview.adapter = MyAdapter(
                this,
                articles,
                object : ArticleListener {
                    override fun onArticleClicked(article: Article) {
                        intent.putExtra("Article", article)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        if (sessionManager.fetchAuthToken() != null) {
                            intent.putExtra("UserName", sessionManager.fetchName())
                            intent.putExtra("Token", sessionManager.fetchAuthToken())
                        }
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@FavoritesActivity).toBundle())
                    }
                })
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    fun onGroupItemClick(item: MenuItem) {
        if (item.title == "Log in") {
            if (sessionManager.fetchName() == null) {
                myDialog.let { DialogOpening().openDialogWindow(it, sessionManager) }
            } else {
                myDialog.let { LogoutDialog().showLogout(it, sessionManager) }
            }
        }
        if (item.title == "Favorites") {
            if (sessionManager.fetchName() == null) {
                myDialog.let { DialogOpening().openDialogWindow(it, sessionManager) }
            } else {
                service.likedArticles(sessionManager.fetchAuthToken()).enqueue(this)
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
            }
        }
        if(item.title == "Home"){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}