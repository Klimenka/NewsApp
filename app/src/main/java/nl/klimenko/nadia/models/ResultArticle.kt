package nl.klimenko.nadia.models

import java.io.Serializable

data class ResultArticle (
    val Results: List<Article>?,
    val NextId : Int?
): Serializable