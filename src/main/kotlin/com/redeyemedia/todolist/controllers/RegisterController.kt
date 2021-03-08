package com.redeyemedia.todolist.controllers

import com.redeyemedia.todolist.Status
import com.redeyemedia.todolist.entities.Todo
import com.redeyemedia.todolist.entities.User
import com.redeyemedia.todolist.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class RegisterController(@Autowired private val userRepository: UserRepository) {

    @GetMapping("/users")
    fun getUsers() : List<User> = userRepository.findAll()



/*    @GetMapping("/id/{id}")
    fun getUserById(@PathVariable id : Int) : ResponseEntity<User> =
        userRepository.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())*/



    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody newUser: User): Status {

       val users: List<User> =  userRepository.findAll()
        println("Users: $users")
        users.map { user ->
            if(user == newUser) {
                println("User already exists!")
                return Status.USER_ALREADY_EXISTS
            }
        }
        userRepository.save(newUser)
        return Status.REGISTERSUCCESS
    }
}