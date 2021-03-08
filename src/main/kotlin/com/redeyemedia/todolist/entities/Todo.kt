package com.redeyemedia.todolist.entities
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity(name = "Todos")
data class Todo (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int = 0,
        @get: NotBlank val name : String = "",
        // @ManyToOne
        val userid : Int? = 0
        )