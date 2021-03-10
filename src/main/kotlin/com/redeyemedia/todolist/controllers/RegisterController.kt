package com.redeyemedia.todolist.controllers

import JWTAuthenticationFilter
import com.redeyemedia.todolist.Status
import com.redeyemedia.todolist.entities.Todo
import com.redeyemedia.todolist.entities.User
import com.redeyemedia.todolist.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class RegisterController(@Autowired private val userRepository: UserRepository) {

    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @GetMapping("/users")
    fun getUsers() : List<User> = userRepository.findAll()


    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody newUser: User): ResponseEntity<Any> {

       val users: List<User> =  userRepository.findAll()
        println("Users: $users")
        users.map { user ->
            if(user == newUser) {
                println("User already exists!")
                return ResponseEntity("User already exists", HttpStatus.CONFLICT)
            }
        }
        // userRepository.save(newUser)
        //return ResponseEntity.ok(UsernamePasswordAuthenticationFilter(newUser.email, newUser.password))
        val userToBeSaved = User(newUser.userid, newUser.email, passwordEncoder().encode(newUser.password))

        return ResponseEntity.ok(userRepository.save(userToBeSaved))

    }
}