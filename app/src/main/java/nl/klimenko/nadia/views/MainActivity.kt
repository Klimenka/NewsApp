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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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


class MainActivity : AppCompatActivity(), Callback<ResultArticle>,
    SwipeRefreshLayout.OnRefreshListener {
    private lateinit var sessionManager: SessionManager
    lateinit var myDialog: Dialog
    lateinit var service: ArticleService
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview)
        //show progress bar
        this.recyclerview.visibility = View.GONE
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        intent = Intent(this, DetailedArticle::class.java)

        myDialog = Dialog(this)
        sessionManager = SessionManager(this)
        val retrofit = RetrofitFactory.getRetrofitObject()
        service = retrofit?.create(ArticleService::class.java)!!
        swipeRefreshLayout = findViewById<View>(R.id.swipe_container) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        loadData()
    }

    private fun loadData() {
        service.articles(sessionManager.fetchAuthToken()).enqueue(this)
    }

    override fun onFailure(call: Call<ResultArticle>, t: Throwable) {
        swipeRefreshLayout.isRefreshing = false
        progressBar.visibility = View.GONE
        Log.e("HTTP", "Could not fetch data", t)
        setContentView(R.layout.tryagain)
        findViewById<Button>(R.id.try_again_button).setOnClickListener {
            this.recreate()
        }
        Toast.makeText(this, this.getString(R.string.network), Toast.LENGTH_LONG).show()
    }

    override fun onRefresh() {
        this.recreate()
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
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
        if(item.title == "Home"){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun shortLoading() {
        Toast.makeText(this, this.getString(R.string.waiting), Toast.LENGTH_SHORT).show()
    }

    private fun loadMoreData(nextId: Int) {
        service.nextArticles(sessionManager.fetchAuthToken(), nextId, 20).enqueue(this)
    }

    override fun onResponse(call: Call<ResultArticle>, response: Response<ResultArticle>) {
        if (response.isSuccessful && response.body() != null) {
            this.recyclerview.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
            val articles = response.body()!!.Results as List<Article>
            val nextId = response.body()!!.NextId as Int

            this.recyclerview.layoutManager = LinearLayoutManager(this)
            this.recyclerview.adapter = MyAdapter(
                this,
                articles,
                object : ArticleListener {
                    override fun onArticleClicked(article: Article) {
                        shortLoading()
                        intent.putExtra("Article", article)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        if (sessionManager.fetchAuthToken() != null) {
                            intent.putExtra("UserName", sessionManager.fetchName())
                            intent.putExtra("Token", sessionManager.fetchAuthToken())
                        }
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())
                      //  startActivity(intent)
                    }
                })


            this.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) { //1 for down
                        loadMoreData(nextId)
                        recyclerview.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                }
            })
        }

    }
}



