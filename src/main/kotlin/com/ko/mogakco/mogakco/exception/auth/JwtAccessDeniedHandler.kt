package com.ko.mogakco.mogakco.exception.auth

import com.ko.mogakco.mogakco.exception.ErrorCode
import com.ko.mogakco.mogakco.exception.auth.AuthenticationExceptionUtils.Companion.EXCEPTION_ATTRIBUTE_KEY
import com.ko.mogakco.mogakco.exception.auth.AuthenticationExceptionUtils.Companion.setResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val errorCode = request.getAttribute(EXCEPTION_ATTRIBUTE_KEY) as ErrorCode
        setResponse(response, errorCode)
    }
}