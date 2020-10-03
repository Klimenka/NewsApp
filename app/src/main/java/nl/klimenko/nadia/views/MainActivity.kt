package nl.klimenko.nadia.views

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.recyclerview.*
import nl.klimenko.nadia.R
import nl.klimenko.nadia.configuration.RetrofitFactory
import nl.klimenko.nadia.controllers.ArticleListener
import nl.klimenko.nadia.controllers.MyAdapter
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.models.ResultArticle
import nl.klimenko.nadia.models.User
import nl.klimenko.nadia.repository.ArticleService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), Callback<ResultArticle>, SwipeRefreshLayout.OnRefreshListener  {
    var myDialog: Dialog? = null
    var service: ArticleService? = null
    private var articles : List<Article>? = emptyList()
    private var collectArticles : MutableList<Article>? = null
    lateinit var swipeRefreshLayout : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        intent = Intent(this, DetailedArticle::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview)
        myDialog = Dialog(this)
        val retrofit = RetrofitFactory.getRetrofitObject()
        service = retrofit?.create(ArticleService::class.java)
        // SwipeRefreshLayout

        // SwipeRefreshLayout
        swipeRefreshLayout = findViewById<View>(R.id.swipe_container) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        loadData()
        longLoading()
    }

    override fun onFailure(call: Call<ResultArticle>, t: Throwable) {
        swipeRefreshLayout.isRefreshing = false
        Log.e("HTTP", "Could not fetch data", t)
        setContentView(R.layout.tryagain)
        findViewById<Button>(R.id.try_again_button).setOnClickListener {
            loadData()
            longLoading()
        }
    }
    override fun onRefresh(){
        this.recreate()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    fun onGroupItemClick(item: MenuItem) {
        if (item.title == "Log in") {
            if (User.getUser().token == null) {
                myDialog?.let { DialogOpening().openDialogWindow(it) }
            } else {
                myDialog?.let { LogoutDialog().showLogout(it) }
            }
        }
    }

    private fun shortLoading() {
        Toast.makeText(this, this.getString(R.string.waiting), Toast.LENGTH_SHORT).show()
    }

    private fun longLoading() {
        Toast.makeText(this, this.getString(R.string.waiting), Toast.LENGTH_LONG).show()
    }

    private fun loadMoreData(nextId: Int) {
        service?.nextArticles(nextId, 20)?.enqueue(this)
    }

    private fun loadData() {
        service?.articles()?.enqueue(this)
    }

    override fun onResponse(call: Call<ResultArticle>, response: Response<ResultArticle>) {
        if (response.isSuccessful && response.body() != null) {
            swipeRefreshLayout.isRefreshing = false
            articles = response.body()!!.Results as List<Article>
            val nextId = response.body()!!.NextId as Int

            this.recyclerview.layoutManager = LinearLayoutManager(this)
            this.recyclerview.adapter = MyAdapter(
                this,
                articles!!,
                object : ArticleListener {
                    override fun onArticleClicked(article: Article) {
                        shortLoading()
                        intent.putExtra("Article", article)
                        startActivity(intent)
                    }
                })
            this.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) { //1 for down
                        loadMoreData(nextId)
                        longLoading()
                    }
                }
            })



        }
    }
}


