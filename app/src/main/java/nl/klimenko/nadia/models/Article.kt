package nl.klimenko.nadia.models

import java.io.Serializable

data class Article (
    val Id : Int?,
    //maybe here it is appropriate to use the object Feed
    val Feed : Int?,
    val Title : String?,
    val PublishDate : String?,
    val Image : String?,
    val Url : String?,
    val Related : Array<String>?,
    val Categories : Array<Category>?,
    val IsLiked : Boolean?
): Serializable