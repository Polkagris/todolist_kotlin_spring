package com.redeyemedia.todolist.controllers

import com.redeyemedia.todolist.entities.Todo
import com.redeyemedia.todolist.repository.TodoRepository
import com.redeyemedia.todolist.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class TodoController(@Autowired private val todoRepository : TodoRepository, @Autowired private val userRepository: UserRepository) {


    @GetMapping("/todos")
    fun getAllTodos() : List<Todo> = todoRepository.findAll()


    @GetMapping("/todos/{id}")
    fun getTodoById(@PathVariable id : Int) : ResponseEntity<Todo> =
        todoRepository.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())


    @PostMapping("/todos")
    fun createTodo(@Valid @RequestBody todo : Todo) : Todo = todoRepository.save(todo)


    @PutMapping("/todos/{id}")
    fun updateTodoById(@PathVariable id : Int, @Valid @RequestBody updatedTodo: Todo)
        : ResponseEntity<Todo> = todoRepository.findById(id).map {
            val newTodo = it.copy(name = updatedTodo.name, userid = updatedTodo.userid)
        ResponseEntity.ok().body(todoRepository.save(newTodo))
    }.orElse(ResponseEntity.notFound().build())


    @DeleteMapping("/todos/{id}")
    fun deleteTodoById(@PathVariable id : Int) : ResponseEntity<Void> =
        todoRepository.findById(id).map{
            todoRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    // Join tables to get all todos from specific user
/*    @GetMapping("/mytodos/{id}")
    fun getMyTodos(@PathVariable id : Int) : ResponseEntity<Todo> =
        todoRepository.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())*/

    @GetMapping("/mytodos/{userid}")
    fun getMyTodos(@PathVariable userid: Int): List<Todo> {
        // val user = userRepository.findById(userid)
        val todos = todoRepository.findAll()

        return todos.filter { todo -> todo.userid == userid }

    }

    @GetMapping("/user/{userid}")
    fun getUsersTodo(@PathVariable userid: Int): List<Todo> {
        val user = userRepository.findById(userid)
        // user.todos
        val todos = todoRepository.findAll()

        return todos.filter { todo -> todo.userid == userid }

    }
}

/*
@GetMapping("/students")
fun retrieveAllStudents(): String{
    // fetch all students from database
    val students = studentRepository.findAll()

    // some processing for better String format on browser showing
    var info : String = ""
    students.forEach{
        info += it.toString() + "
        "
    }

    return info
}

@GetMapping("/subjects")
fun retrieveAllSubjects(): String{
    // fetch all students from database
    val subjects = subjectRepository.findAll()

    // some processing for better String format on browser showing
    var info : String = ""
    subjects.forEach{
        info += it.toString() + "
        "
    }

    return info
}*/
