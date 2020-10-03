package nl.klimenko.nadia.models

import java.io.Serializable

data class Article (
    val Id : Int,
    //maybe here it is appropriate to use the object Feed
    val Feed : Int?,
    val Title : String?,
    val Summary : String?,
    val PublishDate : String?,
    val Image : String?,
    val Url : String?,
    val Related : List<String>?,
    val Categories : List<Category>?,
    val IsLiked : Boolean?
): Serializable