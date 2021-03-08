package com.redeyemedia.todolist.controllers

import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class PublicController {
    @GetMapping("/public")
    fun getMessage(): String {
        val message: String
       return "Hello from public API controller"
    }
}