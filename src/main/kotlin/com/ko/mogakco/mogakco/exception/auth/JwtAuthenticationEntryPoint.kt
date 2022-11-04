package com.ko.mogakco.mogakco.exception.auth

import com.ko.mogakco.mogakco.exception.ErrorCode
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorCode = request.getAttribute(AuthenticationExceptionUtils.EXCEPTION_ATTRIBUTE_KEY) as ErrorCode?
        AuthenticationExceptionUtils.setResponse(response, errorCode)
    }
}