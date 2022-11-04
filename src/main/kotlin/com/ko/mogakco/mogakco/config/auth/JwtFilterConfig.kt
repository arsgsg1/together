package com.ko.mogakco.mogakco.config.auth

import com.ko.mogakco.mogakco.domain.auth.TokenProvider
import com.ko.mogakco.mogakco.service.auth.AuthService
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtFilterConfig(
    private val tokenProvider: TokenProvider,
    private val authService: AuthService
) :
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        val jwtFilter = JwtFilter(tokenProvider, authService)
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}