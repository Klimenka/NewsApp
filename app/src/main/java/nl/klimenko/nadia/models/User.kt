package nl.klimenko.nadia.models

object User{
    var name : String? = null
    var token : LoginToken? = null

   public fun getUser(): User {
        return this
    }
}