package com.ko.mogakco.mogakco.controller.auth

import com.ko.mogakco.mogakco.service.auth.AuthService
import com.ko.mogakco.mogakco.service.auth.dto.CreateAccountDto
import com.ko.mogakco.mogakco.service.auth.dto.LoginDto
import com.ko.mogakco.mogakco.service.auth.dto.TokenDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val authService: AuthService) {
    //todo: 공통 응답 포맷 고려해보기
    @PostMapping("/signup")
    fun signUp(@RequestBody dto: CreateAccountDto): ResponseEntity<Long> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(dto))
    }

    @PostMapping("/signin")
    fun sign(@RequestBody dto: LoginDto): ResponseEntity<TokenDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signIn(dto))
    }
}