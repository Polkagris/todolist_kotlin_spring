package com.redeyemedia.todolist.controllers

import com.redeyemedia.todolist.entities.User
import com.redeyemedia.todolist.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(@Autowired private val userRepository: UserRepository) {

/*
    @GetMapping("/users")
    fun getAllUsers() : List<User> = userRepository.findAll()
*/

}