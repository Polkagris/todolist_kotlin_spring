package com.redeyemedia.todolist.controllers

import JWTAuthenticationFilter
import com.redeyemedia.todolist.Status
import com.redeyemedia.todolist.authentication.SecurityConstants
import com.redeyemedia.todolist.entities.JwtResponse
import com.redeyemedia.todolist.entities.User
import com.redeyemedia.todolist.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import org.springframework.http.ResponseEntity

import java.util.stream.Collectors

import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/user")
class LoginController(@Autowired private val userRepository: UserRepository) {

    fun create(user: User): String {

        val signingKey: ByteArray = SecurityConstants.JWT_SECRET.toByteArray()

        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, signingKey)
            .setSubject(user.email)
            .setExpiration(Date(System.currentTimeMillis() + 864000000))
            .compact()

    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody newUser: User): ResponseEntity<Any> {
        val users: List<User> =  userRepository.findAll()

        users.map { user ->
            if(user == newUser) {
                userRepository.save(newUser)
                println("User already exists - login successful")


                // UsernamePasswordAuthenticationToken(newUser.email, newUser.password)


                //val jwt: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(newUser.email, newUser.password)
                //return ResponseEntity.ok<Any>(JwtResponse(token, user.email, user.userid))
                return ResponseEntity.ok<Any>(create(user))

            }
        }
        println("Login error - email or password does not match.")
        return ResponseEntity("Login error - email or password does not match.", HttpStatus.CONFLICT)
    }


    // TEST
/*    @PostMapping("/signin")
    open fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*>? {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtUtils.generateJwtToken(authentication)
        val userDetails: UserDetailsImpl = authentication.getPrincipal() as UserDetailsImpl
        val roles: List<String> = userDetails.getAuthorities().stream()
            .map { item -> item.getAuthority() }
            .collect(Collectors.toList())
        return ResponseEntity.ok<Any>(
            JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
            )
        )
    }*/
}