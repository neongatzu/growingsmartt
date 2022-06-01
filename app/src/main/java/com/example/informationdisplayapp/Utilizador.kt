package com.example.informationdisplayapp

data class Utilizador
    (var firstName : String ?= null,
     var lastName : String ?= null,
     var username: String ?= null,
     val pontos: String?=null,
     var id: String?= null)
{
        constructor(): this("","","",""," ")
    }
