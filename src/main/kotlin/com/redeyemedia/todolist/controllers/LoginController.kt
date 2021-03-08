package com.redeyemedia.todolist.controllers

import com.redeyemedia.todolist.Status
import com.redeyemedia.todolist.entities.User
import com.redeyemedia.todolist.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class LoginController(@Autowired private val userRepository: UserRepository) {

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody newUser: User): Status {
        val users: List<User> =  userRepository.findAll()

        users.map { user ->
            if(user == newUser) {
                userRepository.save(newUser)
                println("User already exists - login successful")
                return Status.LOGINSUCCESS
            }
        }
        println("Login error - email or password does not match.")
        return Status.LOGINFAILURE;
    }
}