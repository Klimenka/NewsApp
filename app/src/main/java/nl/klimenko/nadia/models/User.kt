package nl.klimenko.nadia.models

import java.io.Serializable

object User : Serializable {
    var name : String? = null
    var token : LoginToken? = null

   fun getUser(): User {
        return this
   }
    fun clearCredentials(){
        name = null
        token = null
    }
}