package com.redeyemedia.todolist.controllers

import com.redeyemedia.todolist.authentication.SecurityConstants
import com.redeyemedia.todolist.entities.User
import com.redeyemedia.todolist.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import org.springframework.http.ResponseEntity
import java.util.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@RestController
@RequestMapping("/user")
class LoginController(@Autowired private val userRepository: UserRepository) {

    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    fun create(user: User): String {

        val signingKey: ByteArray = SecurityConstants.JWT_SECRET.toByteArray()

        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, signingKey)
            .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
            .setIssuer(SecurityConstants.TOKEN_ISSUER)
            .setAudience(SecurityConstants.TOKEN_AUDIENCE)
            .setSubject(user.email)
            .claim("user id", user.userid.toString())
            .setExpiration(Date(System.currentTimeMillis() + 864000000))
            .compact()

    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody newUser: User): ResponseEntity<Any> {
        val users: List<User> =  userRepository.findAll()

        users.map { user ->
            if(user.email == newUser.email && passwordEncoder().matches(newUser.password, user.password)) {
                println("User already exists - login successful")

                return ResponseEntity.ok<Any>(create(user))
            }
        }
        println("Login error - email or password does not match.")

        return ResponseEntity("Login error - email or password does not match.", HttpStatus.CONFLICT)
    }
}