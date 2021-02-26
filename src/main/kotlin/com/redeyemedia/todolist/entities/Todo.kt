package com.redeyemedia.todolist.entities
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank


@Entity(name = "Todos")
data class Todo (
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id : Int = 0,
        @get: NotBlank val name : String = "",
                        val userid : Int? = 0
        )