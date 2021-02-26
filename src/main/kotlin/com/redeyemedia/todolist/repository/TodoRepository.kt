package com.redeyemedia.todolist.repository

import com.redeyemedia.todolist.entities.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Int> {
}