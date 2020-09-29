package nl.klimenko.nadia.controllers

import nl.klimenko.nadia.models.Article

interface ArticleListener {
    fun onArticleClicked(article: Article)
}