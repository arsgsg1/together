package com.ko.mogakco.mogakco.controller

import com.ko.mogakco.mogakco.service.AuthService
import com.ko.mogakco.mogakco.service.dto.CreateAccountDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val authService: AuthService) {
    @PostMapping("/signup")
    fun createAccount(@RequestBody dto: CreateAccountDto): ResponseEntity<Long> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createAccount(dto))
    }
}