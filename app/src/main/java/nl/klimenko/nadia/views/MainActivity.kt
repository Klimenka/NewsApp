package nl.klimenko.nadia.views

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview.*
import nl.klimenko.nadia.R
import nl.klimenko.nadia.configuration.RetrofitFactory
import nl.klimenko.nadia.controllers.ArticleListener
import nl.klimenko.nadia.controllers.MyAdapter
import nl.klimenko.nadia.models.Article
import nl.klimenko.nadia.models.ResultArticle
import nl.klimenko.nadia.models.User
import nl.klimenko.nadia.repository.ArticleService
import retrofit2.*

class MainActivity : AppCompatActivity(), Callback<ResultArticle> {
    var myDialog: Dialog? = null
    var service: ArticleService? = null
    private var articles : List<Article>? = emptyList()
    private var collectArticles : MutableList<Article>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        intent = Intent(this, DetailedArticle::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview)
        myDialog = Dialog(this)
        val retrofit = RetrofitFactory.getRetrofitObject()
        service = retrofit?.create(ArticleService::class.java)
        loadData()
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
        if (item.title == "Log in") {
            if (User.getUser().token == null) {
                myDialog?.let { DialogOpening().openDialogWindow(it) }
            } else {
                myDialog?.let { LogoutView().showLogout(it) }
            }
        }
    }

    private fun shortLoading() {
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
    }

    private fun longLoading() {
        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show()
    }

    private fun loadMoreData(nextId: Int) {
        service?.nextArticles(nextId, 20)?.enqueue(this)
    }

    private fun loadData() {
        service?.articles()?.enqueue(this)
    }

    override fun onResponse(call: Call<ResultArticle>, response: Response<ResultArticle>) {
        if (response.isSuccessful && response.body() != null) {

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


