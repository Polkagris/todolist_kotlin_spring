package com.redeyemedia.todolist.entities

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class JwtResponse (
    val jwt: String,
    val username: String,
    val userId: Int
    )