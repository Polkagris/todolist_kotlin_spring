package com.redeyemedia.todolist.entities

import javax.persistence.*

@Entity(name = "Users")
data class User (
    @Id
    // @OneToMany(mappedBy = "userid", cascade = [CascadeType.ALL])
     val userid: Int = 0,
     val email: String = "",
     val password: String = ""
    )