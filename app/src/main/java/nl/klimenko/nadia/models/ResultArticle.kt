package nl.klimenko.nadia.models

import java.io.Serializable

data class ResultArticle (
    val Results: Array<Article>?,
    val NextId : Int?
): Serializable