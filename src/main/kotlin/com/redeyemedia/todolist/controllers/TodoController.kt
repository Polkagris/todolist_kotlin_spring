package com.redeyemedia.todolist.controllers

import com.redeyemedia.todolist.entities.Todo
import com.redeyemedia.todolist.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class TodoController(@Autowired private val todoRepository : TodoRepository) {

    @GetMapping("/todos")
    fun getAllTodos() : List<Todo> = todoRepository.findAll()

    @GetMapping("/todos/{id}")
    fun getTodoById(@PathVariable id : Int) : ResponseEntity<Todo> =
        todoRepository.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    @PostMapping("/todos")
    fun createTodo(@Valid @RequestBody todo : Todo) : Todo = todoRepository.save(todo)
}