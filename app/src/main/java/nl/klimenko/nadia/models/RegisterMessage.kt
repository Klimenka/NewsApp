package nl.klimenko.nadia.models

import java.io.Serializable

data class RegisterMessage (val Success : Boolean, val Message : String) : Serializable {
    }